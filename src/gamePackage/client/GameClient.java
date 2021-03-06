package gamePackage.client;

import abitur.netz.Client;
import cryptography.RSA;
import gamePackage.client.menu.CannotConnect;
import gamePackage.client.menu.KickedMenu;
import gamePackage.client.menu.Lobby;
import gamePackage.client.menu.MainMenu;
import gamePackage.client.menu.MenuController;
import gamePackage.server.ClientData;
import graphics.Display;

import java.util.HashMap;
import java.util.Map;

    public class GameClient extends Client {

                //Attribute

                //Referenzen
            private RSA rsa;
            private Chat chat;
            private Lobby lobby;
            private ClientData data;
            private Userinterface userInterface;
            private HashMap<Integer, ClientData> connectedPlayers;
            private HashMap<Integer, ClientData> connectedSpectators;

            private Display display;
            private String publicServerKey;

        public GameClient(Display display, String pServerIP, int pServerPort, String gameType, String username, boolean spectator, boolean host) {

            super(pServerIP, pServerPort);

            rsa = new RSA();
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
                    display.getActivePanel().drawObjectOnPanel(lobby, 1);
                } else {

                    userInterface = new Userinterface(display, this);
                    display.getActivePanel().drawObjectOnPanel(userInterface);
                }

                chat = new Chat(display, this);
                display.getActivePanel().drawObjectOnPanel(chat, 3);

                send(rsa.getPublicKey());
                send("GetPublicKey: ");
                send("GetAvailableSpectatorID: ");
            }

                //Format - Chat: <Name>: <Message>
            if(pMessage.startsWith("Chat: ")) {

                String[] messages = pMessage.split(": ");
                chat.processNewMessage(messages[1], rsa.decryptMessage(messages[2], rsa.getPrivateKey()));
            }

                //Format - GetPublicKey:
            if(pMessage.startsWith("GetPublicKey:")) {

                send(rsa.getPublicKey());
            }

            if(pMessage.startsWith("PublicKey: ")) {

                publicServerKey = pMessage;
            }

                //Format - AvailableSpectatorID: <specID>
            if(pMessage.startsWith("AvailableSpectatorID: ")) {

                String[] messages = pMessage.split(": ");
                int specID = Integer.parseInt(messages[1]);

                if(lobby != null) lobby.createToSpectator(specID);
            }

                //Format - JoinedSpectator <username> <spectatorID> <host>
            if(pMessage.startsWith("JoinedSpectator: ")) {

                String[] messages = pMessage.split(": ");

                String username = messages[1];
                int spectatorID = Integer.parseInt(messages[2]);
                boolean host = Boolean.parseBoolean(messages[3]);

                if(lobby != null) lobby.removeSpectatorSlot(spectatorID);

                ClientData data = new ClientData(username, true, host, spectatorID);
                connectedSpectators.put(spectatorID, data);
                if(lobby != null) lobby.createSpectatorSlot(spectatorID, username);

                send("GetAvailableSpectatorID: ");
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

                        if(lobby != null )lobby.remove();
                        if(userInterface != null) userInterface.remove();
                        close();
                        KickedMenu menu = new KickedMenu(display);
                        display.getActivePanel().drawObjectOnPanel(menu);
                    }
                } else if(pMessage.startsWith("KickSpectator: ")) {

                    lobby.removeSpectatorSlot(clientID);
                    if(clientID == data.getSpectatorID()) {

                        if(lobby != null )lobby.remove();
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

                backToLobby();
            }

            if(pMessage.startsWith("SpectatorDisconnect: ")) {

                String[] messages = pMessage.split(": ");
                int spectatorID = Integer.parseInt(messages[1]);

                lobby.removeSpectatorSlot(spectatorID);
                lobby.setMaxSpec(false);
                connectedSpectators.remove(spectatorID);

                send("GetAvailableSpectatorID: ");
            }

            if(pMessage.startsWith("StartGame: ")) {

                lobby.remove();
                data.setReady(false);

                for(Map.Entry<Integer, ClientData> entry : connectedPlayers.entrySet()) {

                    if(entry.getValue() != null) {

                        entry.getValue().setReady(false);
                    }
                }

                for(Map.Entry<Integer, ClientData> entry : connectedSpectators.entrySet()) {

                    if(entry.getValue() != null) {

                        lobby.removeSpectatorSlot(entry.getValue().getSpectatorID());
                    }
                }

                userInterface = new Userinterface(display, this);
                display.getActivePanel().drawObjectOnPanel(userInterface);
            }

                //Format - isIdAvailable: <type> <boolean> <ID> <currentID>
            if(pMessage.startsWith("isIdAvailable: ")) {

                String[] messages = pMessage.split(": ");
                String type = messages[1];
                boolean available = Boolean.parseBoolean(messages[2]);
                int id = Integer.parseInt(messages[3]);
                int currentID = Integer.parseInt(messages[4]);

                if(available) {

                    if(type.equalsIgnoreCase("spec")) {

                        send("ToSpectator: " + currentID + ": " + id);
                    } else if(type.equalsIgnoreCase("player")) {

                        send("ToPlayer: " + currentID + ": " + id);
                    }
                }
            }

                //Format - ToPlayer: <SpecID> <ClientID>
            if(pMessage.startsWith("ToPlayer: ")) {

                String[] messages = pMessage.split(": ");
                int specID = Integer.parseInt(messages[1]);
                int clientID = Integer.parseInt(messages[2]);
                lobby.removeSpectatorSlot(specID);

                for(Map.Entry<Integer, ClientData> entry : connectedSpectators.entrySet()) {

                    if(entry.getValue() != null) {

                        if(entry.getValue().getSpectatorID() == specID) {

                            ClientData cData = new ClientData(entry.getValue().getUsername(), false, entry.getValue().isHost(), clientID, false);
                            connectedPlayers.put(clientID, cData);
                            connectedSpectators.remove(specID);

                            if (specID == data.getSpectatorID()) {

                                data.setReady(false);
                                data.setSpectatorID(-1);
                                data.setSpectator(false);
                                data.setClientID(clientID);
                            }
                        }
                    }
                }
            }

                //Format - ToSpectator: <ClientID> <SpecID>
            if(pMessage.startsWith("ToSpectator: ")) {

                String[] messages = pMessage.split(": ");
                int specID = Integer.parseInt(messages[2]);
                int clientID = Integer.parseInt(messages[1]);

                lobby.removeToSpectator(specID);

                for(Map.Entry<Integer, ClientData> entry : connectedPlayers.entrySet()) {

                    if(entry.getValue() != null) {

                        if(entry.getValue().getClientID() == clientID) {

                            lobby.setCountdown(-1);
                            entry.getValue().setReady(false);
                            lobby.createSpectatorSlot(specID, entry.getValue().getUsername());
                            ClientData cData = new ClientData(entry.getValue().getUsername(), true, entry.getValue().isHost(), specID);
                            connectedPlayers.remove(clientID);
                            connectedSpectators.put(specID, cData);

                            if(clientID == data.getClientID()) {

                                data.setReady(false);
                                data.setClientID(-1);
                                data.setSpectator(true);
                                data.setSpectatorID(specID);
                            }
                        }
                    }
                }
                send("GetAvailableSpectatorID: ");
            }

            if(pMessage.startsWith("SpecBackToLobby: ")) {

                if(data.isSpectator()) {

                    backToLobby();
                }
            }

            if(pMessage.startsWith("Choose: ")) {

                String[] messages = pMessage.split(": ");
                int clientID = Integer.parseInt(messages[1]);
                int choose = Integer.parseInt(messages[2]);

                userInterface.setChoose(clientID, choose);
            }

            if(pMessage.startsWith("Disconnect: ")) {

                CannotConnect cannotConnect = new CannotConnect(display);
                display.getActivePanel().drawObjectOnPanel(cannotConnect);
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

        public void kickPlayer(int clientID) {

            send("KickPlayer: " + clientID);
        }

        public void kickSpectator(int spectatorID) {

            send("KickSpectator: " + spectatorID);
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

        public void playerToSpectator(int clientID, int specID) {

            send("isIdAvailable: spec: " + specID + ": " + clientID);
        }

        public void spectatorToPlayer(int specID, int clientID) {

            send("isIdAvailable: player: " + clientID + ": " + specID);
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
            MenuController menu = new MenuController(display);
            menu.createMainMenu();
        }

        public void backToLobby() {

            if(userInterface != null) {

                userInterface.remove();
                display.getActivePanel().removeObjectFromPanel(userInterface);
                userInterface = null;

                lobby = new Lobby(display, this);
                display.getActivePanel().drawObjectOnPanel(lobby);

                for (Map.Entry<Integer, ClientData> entry : connectedSpectators.entrySet()) {

                    lobby.createSpectatorSlot(entry.getValue().getSpectatorID(), entry.getValue().getUsername());
                }

                send("GetAvailableSpectatorID: ");
            }
        }

        public void sendChatMessage(String message) {

            send("Chat: " + data.getUsername() + ": " + rsa.encryptMessage(message, publicServerKey));
        }

        public void closeServer() {

            send("CloseServer: ");
        }

            //GETTER
        public ClientData getData() {

            return data;
        }

        public HashMap<Integer, ClientData> getConnectedPlayers() {

            return connectedPlayers;
        }

        public HashMap<Integer, ClientData> getConnectedSpectators() {

            return connectedSpectators;
        }
    }
