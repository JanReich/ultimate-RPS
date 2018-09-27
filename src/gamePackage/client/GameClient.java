package gamePackage.client;

import abitur.netz.Client;
import gamePackage.server.ClientData;

import java.util.HashMap;

    public class GameClient extends Client {

                //Attribute


                //Referenzen
            private ClientData data;
            private HashMap<Integer, ClientData> connectedClients;

        public GameClient(String pServerIP, int pServerPort) {

            super(pServerIP, pServerPort);

            connectedClients = new HashMap<>();
        }

        @Override
        public void processMessage(String pMessage) {

                //Format: clientData: username: <name>: clientID: <ID>: host: <boolean>
                //Falls schon Clients auf dem Server sind werden ihre Daten in dieser Methode geschickt.
            if(pMessage.startsWith("clientData: ")) {

                String[] messages = pMessage.split(": ");

                //Player
                if(pMessage.length() == 7)
                    connectedClients.put(Integer.parseInt(messages[4]), new ClientData(messages[2], Integer.parseInt(messages[4]), Boolean.parseBoolean(messages[6])));
                }

            else if(pMessage.startsWith("joined: ")) {

                String[] messages = pMessage.split(": ");

                    //Player
                if(pMessage.length() == 7)
                    connectedClients.put(Integer.parseInt(messages[4]), new ClientData(messages[2], Integer.parseInt(messages[4]), Boolean.parseBoolean(messages[6])));
            }

            else if(pMessage.startsWith("clientDisconnect: ")) {

                String[] messages = pMessage.split(": ");

                if(connectedClients.get(Integer.parseInt(messages[4])).getUsername().equalsIgnoreCase(messages[2]));
                    connectedClients.remove(Integer.parseInt(messages[4]));
            }

                //Falls die Verbindung vom Server wieder getrennt werden muss:
            else if(pMessage.startsWith("disconnect: ")) {

                    //Wenn der Client gekickt wird.
                if(pMessage.contains("kick")) {


                } else {

                    System.err.println(pMessage.replace("disconnect: ", ""));
                }
            }


            if(pMessage.startsWith("RegisterSuccessful: ")) {

                String[] messages = pMessage.split(": ");

                    //Player
                if(messages.length == 2) {

                    //data = new ClientData()
                }

                    //Spectator
                else {


                }
            }
        }
    }
