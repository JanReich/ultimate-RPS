package gamePackage.client;

import abitur.netz.Client;
import gamePackage.client.menu.KickedMenu;
import gamePackage.client.menu.Lobby;
import gamePackage.server.ClientData;
import graphics.Display;

import java.util.HashMap;

    public class GameClient extends Client {

                //Attribute

                //Referenzen
            private Lobby lobby;
            private ClientData data;
            private HashMap<Integer, ClientData> connectedPlayers;
            private HashMap<Integer, ClientData> connectedSpectators;

            private Display display;

        public GameClient(Display display, String pServerIP, int pServerPort, String gameType, String username, boolean spectator, boolean host) {

            super(pServerIP, pServerPort);

            this.display = display;
            connectedPlayers = new HashMap<>();
            connectedSpectators = new HashMap<>();

            data = new ClientData(username, spectator, host);
            registerClient(gameType);

        }

        @Override
        public void processMessage(String pMessage) {

                //Format - RegisterSuccessful: <ClientID> oder <SpectatorID>
            if(pMessage.startsWith("RegisterSuccessful: ")) {

                String[] messages = pMessage.split(": ");

                if(data.isSpectator()) {

                    data.setClientID(-1);
                    data.setSpectatorID(Integer.parseInt(messages[1]));
                } else {

                    data.setSpectatorID(-1);
                    data.setClientID(Integer.parseInt(messages[1]));
                }

                lobby = new Lobby(display, this);
                display.getActivePanel().drawObjectOnPanel(lobby);
            }

                //Format - JoinedSpectator <username> <spectatorID> <host>
            if(pMessage.startsWith("JoinedSpectator: ")) {

                String[] messages = pMessage.split(": ");

                String username = messages[1];
                int spectatorID = Integer.parseInt(messages[2]);
                boolean host = Boolean.parseBoolean(messages[3]);

                ClientData data = new ClientData(username, true, host, spectatorID);
                connectedSpectators.put(spectatorID, data);
                lobby.createSpectatorSlot(spectatorID, username);
            }

                //Format - JoinedPlayer <username> <clientID> <host>
            if(pMessage.startsWith("JoinedPlayer: ")) {

                String[] messages = pMessage.split(": ");

                String username = messages[1];
                int clientID = Integer.parseInt(messages[2]);
                boolean host = Boolean.parseBoolean(messages[3]);

                ClientData data = new ClientData(username, host, false, clientID, false);
                connectedPlayers.put(clientID, data);
            }

                //Format - PlayerReady: <clientID>
            if(pMessage.startsWith("PlayerReady: ")) {

                String[] messages = pMessage.split(": ");
                int clientID = Integer.parseInt(messages[1]);

                if(connectedPlayers.containsKey(clientID)) {

                }
            }

                //Format - PlayerUnReady: <clientID>
            if(pMessage.startsWith("PlayerUnReady: ")) {

                String[] messages = pMessage.split(": ");
                int clientID = Integer.parseInt(messages[1]);

                if(connectedPlayers.containsKey(clientID)) {

                }
            }

            if(pMessage.startsWith("KickPlayer: ") || pMessage.startsWith("KickSpectator: ")) {

                String[] messages = pMessage.split(": ");
                int clientID = Integer.parseInt(messages[1]);

                if(pMessage.startsWith("KickPlayer: ")) {

                    if (clientID == data.getClientID()) {

                        lobby.remove();
                        close();
                        KickedMenu menu = new KickedMenu(display);
                        display.getActivePanel().drawObjectOnPanel(menu);
                    }
                } else if(pMessage.startsWith("KickSpectator: ")) {

                    if(clientID == data.getSpectatorID()) {

                        lobby.remove();
                        close();
                        KickedMenu menu = new KickedMenu(display);
                        display.getActivePanel().drawObjectOnPanel(menu);
                    }
                }
            }

            if(pMessage.startsWith("PlayerDisconnect: ")) {

                String[] messages = pMessage.split(": ");
                int clientID = Integer.parseInt(messages[1]);

                connectedPlayers.remove(clientID);
            }


            if(pMessage.startsWith("SpectatorDisconnect: ")) {

                String[] messages = pMessage.split(": ");
                int spectatorID = Integer.parseInt(messages[1]);

                lobby.removeSpectatorSlot(spectatorID);
                connectedSpectators.remove(spectatorID);
            }

            if(pMessage.startsWith("Disconnect: ")) {

                System.err.println(pMessage);
            }
        }

        /**
         * In dieser Methode registriert sich der Client beim Server
         * @param gameType
         */
        public void registerClient(String gameType) {

                //Format - RegisterClient: <gameType>: <username>: <spectator>: <host>
            send("RegisterClient: " + gameType + ": " + data.getUsername() + ": " + data.isSpectator() + ": " + data.isHost());
        }

        public void setReady(boolean ready, int clientID) {

            if(ready) {
                    //Format - Ready: <clientID>
                send("Ready: " + clientID);
            } else {
                    //Format - Unready: <clientID>
                send("Unready: " + clientID);
            }
        }

        public ClientData getData() {

            return data;
        }

        public HashMap<Integer, ClientData> getConnectedPlayers() {

            return connectedPlayers;
        }

        public HashMap<Integer, ClientData> getConnectedSpectators() {

            return connectedSpectators;
        }

        public void kickPlayer(int clientID) {

            send("KickPlayer: " + clientID);
        }

        public void kickSpectator(int spectatorID) {

            send("KickSpectator: " + spectatorID);
        }
    }
