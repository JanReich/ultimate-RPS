package gamePackage.server;

import abitur.netz.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

    public class GameServer extends Server {

                //Attribute
            private int playerCount;

            private final int minPlayers;
            private final int maxPlayers;
            private final int maxSpectators;
            private final boolean spectatingAllowed;

            private boolean started;

                //Referenzen
            private List<String> names;
            private String[] clientIDs;
            private HashMap<String, ClientData> clients;

        public GameServer(int pPort, int minPlayers, int maxPlayers, int maxSpectators, boolean spectatingAllowed) {

            super(pPort);

            this.minPlayers = minPlayers;
            this.maxPlayers = maxPlayers;
            this.maxSpectators = maxSpectators;
            this.spectatingAllowed = spectatingAllowed;

            this.names = new ArrayList<>();
            this.clients = new HashMap<>();
            this.clientIDs = new String[maxPlayers];
        }

        @Override
        public void processNewConnection(String pClientIP, int pClientPort) {

            if(!clients.containsKey(pClientIP)) clients.put(pClientIP, null);
        }

        @Override
        public void processMessage(String pClientIP, int pClientPort, String pMessage) {

            if(pMessage.startsWith("RegisterClient: ")) {

                if(clients.get(pClientIP) == null) {

                    String messages[] = pMessage.split(": ");

                    boolean host = false;
                    boolean spectator = false;
                    String username = messages[2];

                    if(messages.length == 5)
                        host = Boolean.parseBoolean(messages[4]);
                     else
                        spectator = true;

                     if(!names.contains(username)) {

                        if(!(!spectatingAllowed && spectator)) {

                            if(!started) {

                                if(spectator) {

                                    names.add(username);
                                    send(pClientIP, pClientPort, "RegisterSuccessful: ");
                                    clients.put(pClientIP, new ClientData(pClientIP, pClientPort, username, spectator));
                                    System.out.println("[Server] Client \"" + username + "\" hat sich mit dem Server als Spectator verbunden!");
                                } else {

                                    if(playerCount < maxPlayers) {

                                        playerCount++;
                                        int clientID = -1;

                                        for (int i = 0; i < clientIDs.length; i++) {

                                            if(clientIDs[i] == null) {

                                                clientID = i;
                                                clientIDs[i] = pClientIP;
                                            }
                                        }

                                        names.add(username);
                                        clients.put(pClientIP, new ClientData(pClientIP, pClientPort, host, username, clientID));
                                        System.out.println("[Server] Client \"" + username + "\" hat sich mit dem Server als Spieler verbunden!");

                                        send(pClientIP, pClientPort, "RegisterSuccessful: " + clientID);
                                        sendToAll("joined: username: " + username + ": clientID: " + clientID + ": host: " + host);
                                    } else send(pClientIP, pClientPort,"Disconnect: Server full");
                                }
                            } else send(pClientIP, pClientPort, "Disconnect: Game Already started");
                        } else send(pClientIP, pClientPort, "Disconnect: No Spectators allowed");
                     } else send(pClientIP, pClientPort, "Disconnect: Username Already in use");
                } else send(pClientIP, pClientPort, "Disconnect: A Client of this Device is already connected to the Server");
            }

            else if(pMessage.startsWith("")) {


            }
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
