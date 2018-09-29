package gamePackage.server;

import abitur.netz.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    public class GameServer extends Server {

                //Attribute
            private boolean started;
            private boolean countdown;

            private int playerCount;
            private int spectatorCount;
            private final int minPlayers;
            private final int maxPlayers;
            private final int maxSpectators;
            private final boolean spectatingAllowed;

                //Referenzen
            private int[] active;
            private String gameType;
            private List<String> names;
            private String[] clientIDs;
            private String[] spectatorIDs;
            private Map<String, ClientData> clients;

        public GameServer(int pPort, int minPlayers, int maxPlayers, int maxSpectators, boolean spectatingAllowed, String gameType) {

            super(pPort);

            this.gameType = gameType;
            this.minPlayers = minPlayers;
            this.maxPlayers = maxPlayers;
            this.maxSpectators = maxSpectators;
            this.spectatingAllowed = spectatingAllowed;

            this.names = new ArrayList<>();
            this.clients = new HashMap<>();
            this.active = new int[maxPlayers];
            this.clientIDs = new String[maxPlayers];
            this.spectatorIDs = new String[maxSpectators];
        }

        @Override
        public void processNewConnection(String pClientIP, int pClientPort) {

            if(!clients.containsKey(pClientIP)) clients.put(pClientIP, null);
        }

        @Override
        public void processMessage(String pClientIP, int pClientPort, String pMessage) {

                //Format - RegisterClient: <gameType>: <username>: <spectator>: <host>
            if(pMessage.startsWith("RegisterClient: ")) {

                if(clients.get(pClientIP) == null) {

                        //Die Nachricht in Attributen speichern
                    String messages[] = pMessage.split(": ");

                    String username = messages[2];
                    String userGameType = messages[1];
                    boolean host = Boolean.getBoolean(messages[4]);
                    boolean spectator = Boolean.parseBoolean(messages[3]);

                    if(userGameType.equalsIgnoreCase(gameType)) {

                        if(!(!spectatingAllowed && spectator)) {

                            if(!names.contains(userGameType)) {

                                if(!started || spectator) {

                                    if(spectator) {

                                        if(spectatorCount < maxSpectators) {

                                            names.add(username);
                                            spectatorCount++;
                                            int spectatorID = generateSpectatorID(pClientIP);
                                            clients.put(pClientIP, new ClientData(pClientIP, pClientPort, username, spectator, host));
                                            clients.get(pClientIP).setSpectatorID(spectatorID);
                                            System.out.println("[Server] Client \"" + username + "\" hat sich mit dem Server als Spectator verbunden!");
                                            send(pClientIP, pClientPort, "RegisterSuccessful: " + spectatorID + ": " + started);
                                            sendToAll("JoinedSpectator: " + username + ": " + spectatorID + ": " + host);
                                        } else send(pClientIP, pClientPort, "Disconnect: Server full");
                                    }
                                        //when a Player join's
                                    else {

                                        if(playerCount < maxPlayers) {

                                            playerCount++;
                                            names.add(username);
                                            int userClientID = generateClientID(pClientIP);
                                            clients.put(pClientIP, new ClientData(pClientIP, pClientPort, host, username, userClientID));
                                            System.out.println("[Server] Client \"" + username + "\" hat sich mit dem Server als Spieler verbunden!");
                                            send(pClientIP, pClientPort, "RegisterSuccessful: " + userClientID + ": " + started);
                                            sendToAll("JoinedPlayer: " + username + ": " + userClientID + ": " + host + ": " + false);
                                        }
                                    }

                                        //Den Clients übermitteln, welche Spieler und Spectaors bereits gejoint sind
                                    for(Map.Entry<String, ClientData> entry : clients.entrySet()) {

                                        if(!entry.getKey().equalsIgnoreCase(pClientIP)) {

                                            if(entry.getValue() != null) {

                                                if (entry.getValue().isSpectator()) send(pClientIP, pClientPort, "JoinedSpectator: " + entry.getValue().getUsername() + ": " + entry.getValue().getSpectatorID() + ": " + entry.getValue().isHost());
                                                else send(pClientIP, pClientPort, "JoinedPlayer: " + entry.getValue().getUsername() + ": " + entry.getValue().getClientID() + ": " + entry.getValue().isHost() + ": " + entry.getValue().isReady());
                                            }
                                        }
                                    }
                                } else send(pClientIP, pClientPort, "Disconnect: Game Already started");
                            } else send(pClientIP, pClientPort, "Disconnect: Username Already in use");
                        } else send(pClientIP, pClientPort, "Disconnect: No Spectators allowed");
                    } else send(pClientIP, pClientPort, "Disconnect: This Server is running on another gamemode");
                } else send(pClientIP, pClientPort, "Disconnect: A Client of this Device is already connected to the Server");
            }

            else if(pMessage.startsWith("Ready: ")) {

                String[] messages = pMessage.split(": ");
                int clientID = Integer.parseInt(messages[1]);
                clients.get(pClientIP).setReady(true);

                sendToAll("PlayerReady: " + clientID);

                int players = 0;
                int readyPlayers = 0;

                for(Map.Entry<String, ClientData> entry : clients.entrySet()) {

                    if(entry.getValue() != null) {

                        if(!entry.getValue().isSpectator()) {

                            players++;
                            if (entry.getValue().isReady()) {

                                readyPlayers++;
                            }
                        }
                    }
                }

                if(players >= minPlayers) {

                    if(players == readyPlayers) {

                        countdown = true;
                        sendToAll("StartCountdown: ");
                    }
                }
            }

            if(pMessage.startsWith("CountdownOver: ")) {

                if(countdown && !started)  {

                    started = true;
                    countdown = false;
                    sendToAll("StartGame: ");
                }
            }

            else if(pMessage.startsWith("Unready: ")) {

                countdown = false;
                String[] messages = pMessage.split(": ");
                int clientID = Integer.parseInt(messages[1]);
                clients.get(pClientIP).setReady(false);

                sendToAll("PlayerUnReady: "  + clientID);
            }

            else if(pMessage.startsWith("KickPlayer: ") || pMessage.startsWith("KickSpectator: ")) {

                sendToAll(pMessage);
            }

            else if(pMessage.startsWith("GetAvailableSpectatorID: ")) {

                send(pClientIP, pClientPort, "AvailableSpectatorID: " + getAvailableSpectatorID());
            }

                //Format - ToPlayer: <SpecID> <ClientID>
            else if(pMessage.startsWith("ToPlayer: ")) {

                String[] messages = pMessage.split(": ");
                int clientID = Integer.parseInt(messages[2]);
                int specID = Integer.parseInt(messages[1]);

                if(specID != -1) spectatorIDs[specID] = null;

                playerCount++;
                spectatorCount--;
                clientIDs[clientID] = pClientIP;
                clients.get(pClientIP).setSpectator(false);
                clients.get(pClientIP).setSpectatorID(-1);
                clients.get(pClientIP).setClientID(clientID);

                sendToAll(pMessage);
            }

                //Format - ToSpecTator: <ClientID> <SpecID>
            else if(pMessage.startsWith("ToSpectator: ")) {

                String[] messages = pMessage.split(": ");
                int clientID = Integer.parseInt(messages[1]);
                int specID = Integer.parseInt(messages[2]);

                playerCount--;
                spectatorCount++;
                clientIDs[clientID] = null;

                clients.get(pClientIP).setReady(false);
                clients.get(pClientIP).setSpectator(true);
                clients.get(pClientIP).setSpectatorID(specID);
                clients.get(pClientIP).setClientID(-1);

                sendToAll(pMessage);
            }

            else if(pMessage.startsWith("Choose: ") || pMessage.startsWith("CloseServer: ")) {

                sendToAll(pMessage);
            }
        }

        private int generateClientID(String clientIP) {

            for (int i = 0; i < clientIDs.length; i++) {

                if(clientIDs[i] == null) {

                    clientIDs[i] = clientIP;
                    return i;
                } else continue;
            }
            return -1;
        }

        private int getAvailableSpectatorID() {

            for (int i = 0; i < spectatorIDs.length; i++) {

                if(spectatorIDs[i] == null) {

                    return i;
                } else continue;
            }
            return -1;
        }

        private int generateSpectatorID(String spectatorID) {

            for (int i = 0; i < spectatorIDs.length; i++) {

                if(spectatorIDs[i] == null) {

                    spectatorIDs[i] = spectatorID;
                    return i;
                } else continue;
            }
            return -1;
        }


        @Override
        public void processClosingConnection(String pClientIP, int pClientPort) {

            if(clients != null) {

                if(clients.containsKey(pClientIP) && clients.get(pClientIP) != null) {

                    if(clients.get(pClientIP).getPort() == pClientPort) {

                        if(!clients.get(pClientIP).isSpectator()) {

                            playerCount--;

                            for (int i = 0; i < clientIDs.length; i++) {

                                if(clientIDs[i] != null) {

                                    if (clientIDs[i].equals(pClientIP)) {

                                        clientIDs[i] = null;

                                        sendToAll("PlayerDisconnect: " + i);
                                        if(started) {

                                            started = false;
                                            sendToAll("BackToMenu: ");
                                        }
                                    }
                                }
                            }

                            names.remove(clients.get(pClientIP).getUsername());
                            System.out.println("[Server] Client \"" + clients.get(pClientIP).getUsername() + "\" hat die Verbindung zum Server getrennt!");
                            clients.remove(pClientIP);
                        } else {

                            spectatorCount--;
                            for (int i = 0; i < spectatorIDs.length; i++) {

                                if(spectatorIDs[i] != null) {

                                    if (spectatorIDs[i].equals(pClientIP)) {

                                        spectatorIDs[i] = null;
                                        sendToAll("SpectatorDisconnect: " + i);
                                    }
                                }
                            }

                            names.remove(clients.get(pClientIP).getUsername());
                            System.out.println("[Server] Client \"" + clients.get(pClientIP).getUsername() + "\" hat die Verbindung zum Server getrennt!");
                            clients.remove(pClientIP);
                        }
                    }
                }
            }
        }
    }
