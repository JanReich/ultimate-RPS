package gamePackage.server;

    public class ClientData {

                //Attribute
            private int port;
            private int clientID;
            private int spectatorID;

            private boolean host;

                //Referent
            private String username;
            private String clientIP;


        public ClientData(String username, int spectatorID) {

            this.username = username;
            this.spectatorID = spectatorID;
        }

        public ClientData(String username, int clientID, boolean host) {

            this.host = host;
            this.clientID = clientID;
            this.username = username;
        }

        public ClientData(String clientIP, int port, String username, int spectatorID) {

            this.port = port;
            this.clientIP = clientIP;

            this.username = username;
            this.spectatorID = spectatorID;
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

        public int getSpectatorID() {

            return spectatorID;
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
    }
