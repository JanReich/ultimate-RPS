package gamePackage.server;

import abitur.netz.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    public class GameServer extends Server {

                //Attribute
            private boolean started;

            private int playerCount;
            private int spectatorCount;
            private final int minPlayers;
            private final int maxPlayers;
            private final int maxSpectators;
            private final boolean spectatingAllowed;

                //Referenzen
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
                                            int spectatorID = generateSpectatorID();
                                            clients.put(pClientIP, new ClientData(pClientIP, pClientPort, username, spectator, host));
                                            System.out.println("[Server] Client \"" + username + "\" hat sich mit dem Server als Spectator verbunden!");
                                            send(pClientIP, pClientPort, "RegisterSuccessful: ");
                                            sendToAll("joinedSpectator: username: " + username + ": clientID: " + spectatorID + ": host: " + host);
                                        } else send(pClientIP, pClientPort, "Disconnect: Server full");
                                    }
                                        //when a Player join's
                                    else {

                                        if(playerCount < maxPlayers) {

                                            playerCount++;
                                            names.add(username);
                                            int userClientID = generateClientID();
                                            clients.put(pClientIP, new ClientData(pClientIP, pClientPort, host, username, userClientID));
                                            System.out.println("[Server] Client \"" + username + "\" hat sich mit dem Server als Spieler verbunden!");
                                            send(pClientIP, pClientPort, "RegisterSuccessful: " + userClientID);
                                            sendToAll("joinedPlayer: username: " + username + ": clientID: " + userClientID + ": host: " + host);
                                        }
                                    }
                                } else send(pClientIP, pClientPort, "Disconnect: Game Already started");
                            } else send(pClientIP, pClientPort, "Disconnect: Username Already in use");
                        } else send(pClientIP, pClientPort, "Disconnect: No Spectators allowed");
                    } else send(pClientIP, pClientPort, "Disconnect: This Server is running on another gamemode");
                } else send(pClientIP, pClientPort, "Disconnect: A Client of this Device is already connected to the Server");
            }

            else if(pMessage.startsWith("")) {


            }
        }

        public int generateClientID() {

            for (int i = 0; i < clientIDs.length; i++) {

                if(clientIDs[i] == null) {

                    return i;
                } else continue;
            }
            return -1;
        }

        public int generateSpectatorID() {

            for (int i = 0; i < spectatorIDs.length; i++) {

                if(spectatorIDs[i] == null) {

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

                                if(clientIDs[i].equals(pClientIP)) {

                                    clientIDs[i] = null;
                                    sendToAll("clientDisconnect: " + i);
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
