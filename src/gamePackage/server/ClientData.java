package gamePackage.server;

    public class ClientData {


            private int port;
            private boolean host;
            private int clientID;
            private int spectatorID;
            private String username;
            private String clientIP;
            private boolean spectator;

            private boolean ready;

        public ClientData(String username, boolean spectator, boolean host) {

            clientID = -1;
            this.host = host;
            spectatorID = - 1;
            this.username = username;
            this.spectator = spectator;
        }

            //Register Player
        public ClientData(String username, boolean spectator, boolean host, int clientID, boolean ready) {

            this.host = host;
            spectatorID = - 1;
            this.clientID = clientID;
            this.username = username;
            this.spectator = spectator;

            this.ready = ready;
        }


            //Register Spectator
        public ClientData(String username, boolean spectator, boolean host, int spectatorID) {

            clientID = -1;
            this.host = host;
            this.username = username;
            this.spectator = spectator;
            this.spectatorID = spectatorID;
        }

        public ClientData(String clientIP, int port, String username, boolean spectator, boolean host) {

            clientID = -1;
            this.port = port;
            this.host = host;
            spectatorID = - 1;
            this.clientIP = clientIP;

            this.username = username;
            this.spectator = spectator;
        }

        public ClientData(String clientIP, int port, boolean host, String username, int clientID) {

            this.port = port;
            this.host = host;
            spectatorID = - 1;
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

        public boolean isSpectator() {

            return spectator;
        }

        public void setClientID(int clientID) {

            this.clientID = clientID;
        }

        public void setSpectator(boolean spectator) {

            this.spectator = spectator;
        }

        public int getSpectatorID() {

            return spectatorID;
        }

        public void setSpectatorID(int spectatorID) {

            this.spectatorID = spectatorID;
        }

        public boolean isReady() {

            return ready;
        }

        public void setReady(boolean ready) {

            this.ready = ready;
        }
    }
