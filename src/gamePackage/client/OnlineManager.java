package gamePackage.client;

import config.ServerConfig;
import gamePackage.client.menu.Lobby;
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
            private String gameType;

            private GameServer server;
            private GameClient client;

        public OnlineManager(Display display, String gameType, String username) {

            this.host = true;
            this.display = display;
            this.username = username;
            this.gameType = gameType;

            createServer();
        }

        public OnlineManager(Display display, String gameType, String username, String serverIP, int port) {

            this.port = port;
            this.display = display;
            this.username = username;
            this.serverIP = serverIP;
            this.gameType = gameType;
            this.host = false;

            joinServer();
        }

        private void createServer() {

            ServerConfig config = new ServerConfig();

            server = new GameServer(config.getServerPort(), config.getMinPlayers(), config.getMaxPlayers(), config.getMaxSpectator(), config.isSpectatorJoin(), gameType);
            serverIP = "localhost";
            port = config.getServerPort();

            joinServer();
        }


        private void joinServer() {

            client = new GameClient(display, serverIP, port, gameType, username, false, host);
        }
    }
