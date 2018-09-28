package gamePackage.client;

import config.ServerConfig;
import gamePackage.server.GameServer;
import graphics.Display;

    public class OnlineManager {

                //Attribute
            private int port;
            private boolean host;
            private boolean spectator;

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
            this.spectator = false;

            createServer();
        }

        public OnlineManager(Display display, String gameType, String username, String serverIP, int port, boolean spectator) {

            this.port = port;
            this.display = display;
            this.username = username;
            this.serverIP = serverIP;
            this.gameType = gameType;
            this.spectator = spectator;
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

            client = new GameClient(display, serverIP, port, gameType, username, spectator, host);
        }
    }
