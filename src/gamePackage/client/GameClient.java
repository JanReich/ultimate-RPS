package gamePackage.client;

import abitur.netz.Client;
import gamePackage.client.menu.KickedMenu;
import gamePackage.client.menu.Lobby;
import gamePackage.server.ClientData;
import graphics.Display;

import java.util.HashMap;
import java.util.Map;

    public class GameClient extends Client {

                //Attribute

                //Referenzen
            private Lobby lobby;
            private ClientData data;
            private Userinterface userInterface;
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

                //Format - RegisterSuccessful: <ClientID> oder <SpectatorID> <started>
            if(pMessage.startsWith("RegisterSuccessful: ")) {

                String[] messages = pMessage.split(": ");
                boolean started = Boolean.parseBoolean(messages[2]);

                if(data.isSpectator()) {

                    data.setClientID(-1);
                    data.setSpectatorID(Integer.parseInt(messages[1]));
                } else {

                    data.setSpectatorID(-1);
                    data.setClientID(Integer.parseInt(messages[1]));
                }

                if(!started) {

                    lobby = new Lobby(display, this);
                    display.getActivePanel().drawObjectOnPanel(lobby);
                } else {

                    userInterface = new Userinterface(display, this);
                    display.getActivePanel().drawObjectOnPanel(userInterface);
                }
            }

                //Format - JoinedSpectator <username> <spectatorID> <host>
            if(pMessage.startsWith("JoinedSpectator: ")) {

                String[] messages = pMessage.split(": ");

                String username = messages[1];
                int spectatorID = Integer.parseInt(messages[2]);
                boolean host = Boolean.parseBoolean(messages[3]);

                ClientData data = new ClientData(username, true, host, spectatorID);
                connectedSpectators.put(spectatorID, data);
                if(lobby != null)
                    lobby.createSpectatorSlot(spectatorID, username);
            }

                //Format - JoinedPlayer <username> <clientID> <host> <ready>
            if(pMessage.startsWith("JoinedPlayer: ")) {

                String[] messages = pMessage.split(": ");

                String username = messages[1];
                int clientID = Integer.parseInt(messages[2]);
                boolean host = Boolean.parseBoolean(messages[3]);
                boolean ready = Boolean.parseBoolean(messages[4]);

                ClientData data = new ClientData(username, host, false, clientID, ready);
                connectedPlayers.put(clientID, data);
            }

                //Format - PlayerReady: <clientID>
            if(pMessage.startsWith("PlayerReady: ")) {

                String[] messages = pMessage.split(": ");
                int clientID = Integer.parseInt(messages[1]);

                if(connectedPlayers.containsKey(clientID)) {

                    connectedPlayers.get(clientID).setReady(true);
                }
            }

                //Format - PlayerUnReady: <clientID>
            if(pMessage.startsWith("PlayerUnReady: ")) {

                String[] messages = pMessage.split(": ");
                int clientID = Integer.parseInt(messages[1]);

                if(connectedPlayers.containsKey(clientID)) {

                    lobby.setCountdown(-1);
                    connectedPlayers.get(clientID).setReady(false);
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

            if(pMessage.startsWith("StartCountdown: ")) {

                lobby.setCountdown(5);
            }

            if(pMessage.startsWith("CloseServer: ")) {

                disconnect();
            }

            if(pMessage.startsWith("PlayerDisconnect: ")) {

                String[] messages = pMessage.split(": ");
                int clientID = Integer.parseInt(messages[1]);

                connectedPlayers.remove(clientID);
            }

            if(pMessage.startsWith("BackToMenu: ")) {

                if(userInterface != null) {

                    userInterface.remove();
                    display.getActivePanel().removeObjectFromPanel(userInterface);
                    userInterface = null;

                    lobby = new Lobby(display, this);
                    display.getActivePanel().drawObjectOnPanel(lobby);

                    for (Map.Entry<Integer, ClientData> entry : connectedSpectators.entrySet()) {

                        lobby.createSpectatorSlot(entry.getValue().getSpectatorID(), entry.getValue().getUsername());
                    }
                }
            }

            if(pMessage.startsWith("SpectatorDisconnect: ")) {

                String[] messages = pMessage.split(": ");
                int spectatorID = Integer.parseInt(messages[1]);

                lobby.removeSpectatorSlot(spectatorID);
                connectedSpectators.remove(spectatorID);
            }

            if(pMessage.startsWith("StartGame: ")) {

                lobby.remove();
                data.setReady(false);
                for (int i = 0; i < connectedSpectators.size(); i++) {

                    lobby.removeSpectatorSlot(connectedPlayers.get(i).getSpectatorID());
                }

                userInterface = new Userinterface(display, this);
                display.getActivePanel().drawObjectOnPanel(userInterface);
            }

            if(pMessage.startsWith("ToPlayer: ")) {

                String[] messages = pMessage.split(": ");
                int specID = Integer.parseInt(messages[1]);
                int clientID = Integer.parseInt(messages[2]);
                lobby.removeSpectatorSlot(specID);

                if(data.getSpectatorID() == specID) {

                    data.setClientID(clientID);
                    data.setSpectatorID(-1);
                    data.setSpectator(false);
                }

                for (int i = 0; i < connectedSpectators.size(); i++) {

                    if(connectedSpectators.get(i).getSpectatorID() == specID) {

                        ClientData cData = new ClientData(connectedSpectators.get(i).getUsername(), false, connectedSpectators.get(i).isHost(), clientID, false);
                        connectedSpectators.remove(specID);
                        connectedPlayers.put(clientID, cData);
                    }
                }
            }

            if(pMessage.startsWith("Choose: ")) {

                String[] messages = pMessage.split(": ");
                int clientID = Integer.parseInt(messages[1]);
                int choose = Integer.parseInt(messages[2]);

                userInterface.setChoose(clientID, choose);
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

        public void choose(int clientID, int choose) {

            send("Choose: " + clientID + ": " + choose);
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

        public void spectatorToPlayer(int spectatorID, int playerID) {

            send("ToPlayer: " + spectatorID + ": " + playerID);
        }

        public void countdownOver() {

            send("CountdownOver: ");

            data.setReady(false);
            for(Map.Entry<Integer, ClientData> entry : connectedPlayers.entrySet()) {

                if(entry.getValue() != null) {

                    entry.getValue().setReady(false);
                }
            }
        }

        public void disconnect() {

            if(lobby != null) {

                lobby.remove();
                display.getActivePanel().removeObjectFromPanel(lobby);
            } else {

                userInterface.remove();
                display.getActivePanel().removeObjectFromPanel(userInterface);
            }
            close();

                //TODO: Connection Lost Screen
        }

        public void closeServer() {

            send("CloseServer: ");
        }
    }
