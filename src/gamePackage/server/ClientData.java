package gamePackage.server;

    public class ClientData {


            private int port;
            private String clientIP;
            private int clientID;

            private boolean host;
            private String username;
            private boolean spectator;

        public ClientData(String username, int clientID, boolean host) {

            this.host = host;
            this.clientID = clientID;
            this.username = username;
        }

        public ClientData(String clientIP, int port, String username, boolean spectator) {

            this.port = port;
            this.clientIP = clientIP;

            this.username = username;
            this.spectator = spectator;
        }

        public ClientData(String clientIP, int port, boolean host, String username, int clientID) {

            this.port = port;
            this.host = host;
            this.clientIP = clientIP;

            this.clientID = clientID;
            this.username = username;
        }

        public int getPort() {

            return port;
        }

        public int getClientID() {

            return clientID;
        }

        public boolean isHost() {

            return host;
        }

        public String getUsername() {

            return username;
        }

        public String getClientIP() {

            return clientIP;
        }

        public boolean isSpectator() {

            return spectator;
        }

        public void setClientID(int clientID) {

            this.clientID = clientID;
        }

        public void setSpectator(boolean spectator) {

            this.spectator = spectator;
        }
    }
