package gamePackage.server;

import abitur.netz.Server;

import java.util.HashMap;

    public class GameServer extends Server {

                //Attribute

                //Referenzen
            private HashMap<String, ClientData> clients;

        public GameServer(int pPort) {

            super(pPort);
        }

        @Override
        public void processNewConnection(String pClientIP, int pClientPort) {

            send(pClientIP, pClientPort, "clientData: username: test: clientID: 1: host: true");
        }

        @Override
        public void processMessage(String pClientIP, int pClientPort, String pMessage) {

        }

        @Override
        public void processClosingConnection(String pClientIP, int pClientPort) {

        }
    }
