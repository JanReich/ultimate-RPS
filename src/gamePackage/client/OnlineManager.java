package gamePackage.client;

import config.ServerConfig;
import gamePackage.server.GameServer;
import graphics.Display;

    public class OnlineManager {

                //Attribute
            private int port;
            private boolean host;

                //Referenzen
            private Display display;
            private String username;
            private String serverIP;

            private GameServer server;
            private GameClient client;

        public OnlineManager(Display display, String username, boolean host) {

            this.host = host;
            this.display = display;
            this.username = username;

            createServer();
        }

        public OnlineManager(Display display, String username, String serverIP, int port) {

            this.port = port;
            this.display = display;
            this.username = username;
            this.serverIP = serverIP;

            joinServer();
        }

        private void createServer() {

            ServerConfig config = new ServerConfig();

            server = new GameServer(config.getServerPort(), config.getMinPlayers(), config.getMaxPlayers(), config.getMaxSpectator(), config.isSpectatorJoin());
            serverIP = "localhost";
            port = config.getServerPort();

            joinServer();
        }


        private void joinServer() {

            client = new GameClient(serverIP, port);
        }
    }
