package config;

import toolBox.FileHelper;

import java.io.File;
import java.util.HashMap;

    public class ServerListConfig extends Config {

                //Attribute
            private boolean slot1;
            private boolean slot2;
            private boolean slot3;
            private boolean slot4;

                //Server1
            private String name1;
            private int serverPort1;
            private String serverIP1;

                //Server2
            private String name2;
            private int serverPort2;
            private String serverIP2;

                //Server3
            private String name3;
            private int serverPort3;
            private String serverIP3;

                //Server4
            private String name4;
            private int serverPort4;
            private String serverIP4;

        public ServerListConfig() {

            super(new File("res/configs/ServerList.properties"));
        }

        @Override
        public void save() {

            HashMap<String, String> config = new HashMap<>();

            if(slot1) config.put("slot1", "Full");
            else config.put("slot1", "Empty");

            if(slot2) config.put("slot2", "Full");
            else config.put("slot2", "Empty");

            if(slot3) config.put("slot3", "Full");
            else config.put("slot3", "Empty");

            if(slot4) config.put("slot4", "Full");
            else config.put("slot4", "Empty");

            config.put("name1", name1 + "");
            config.put("serverIP1", serverIP1 + "");
            config.put("serverPort1", serverPort1 + "");

            config.put("name2", name2 + "");
            config.put("serverIP2", serverIP2 + "");
            config.put("serverPort2", serverPort2 + "");

            config.put("name3", name3 + "");
            config.put("serverIP3", serverIP3 + "");
            config.put("serverPort3", serverPort3 + "");

            config.put("name4", name4 + "");
            config.put("serverIP4", serverIP4 + "");
            config.put("serverPort4", serverPort4 + "");

            FileHelper.setProperty(file, config);
        }

        @Override
        public void readConfig() {

            if(FileHelper.getProperty(file, "slot1").equalsIgnoreCase("Empty")) slot1 = false;
            else slot1 = true;

            if(FileHelper.getProperty(file, "slot2").equalsIgnoreCase("Empty")) slot2 = false;
            else slot2 = true;

            if(FileHelper.getProperty(file, "slot3").equalsIgnoreCase("Empty")) slot3 = false;
            else slot3 = true;

            if(FileHelper.getProperty(file, "slot4").equalsIgnoreCase("Empty")) slot4 = false;
            else slot4 = true;

            if(slot1) {

                name1 = FileHelper.getProperty(file, "name1");
                serverIP1 = FileHelper.getProperty(file, "serverIP1");
                serverPort1 = Integer.parseInt(FileHelper.getProperty(file, "serverPort1"));
            }

            if(slot2) {

                name2 = FileHelper.getProperty(file, "name2");
                serverIP2 = FileHelper.getProperty(file, "serverIP2");
                serverPort2 = Integer.parseInt(FileHelper.getProperty(file, "serverPort2"));
            }

            if(slot3) {

                name3 = FileHelper.getProperty(file, "name3");
                serverIP3 = FileHelper.getProperty(file, "serverIP3");
                serverPort3 = Integer.parseInt(FileHelper.getProperty(file, "serverPort3"));
            }

            if(slot4) {

                name4 = FileHelper.getProperty(file, "name4");
                serverIP4 = FileHelper.getProperty(file, "serverIP4");
                serverPort4 = Integer.parseInt(FileHelper.getProperty(file, "serverPort4"));
            }
        }

        @Override
        public void setStandards() {

            if(!FileHelper.isFileExisting(file)) {

                FileHelper.createFile(file);

                HashMap<String, String> config = new HashMap<>();
                config.put("slot1", "Empty");
                config.put("slot2", "Empty");
                config.put("slot3", "Empty");
                config.put("slot4", "Empty");

                config.put("name1", "Empty");
                config.put("serverIP1", "Empty");
                config.put("serverPort1", "Empty");

                config.put("name2", "Empty");
                config.put("serverIP2", "Empty");
                config.put("serverPort2", "Empty");

                config.put("name3", "Empty");
                config.put("serverIP3", "Empty");
                config.put("serverPort3", "Empty");

                config.put("name4", "Empty");
                config.put("serverIP4", "Empty");
                config.put("serverPort4", "Empty");

                FileHelper.setProperty(file, config);
            }
        }

        public boolean isSlot1() {

            return slot1;
        }

        public void setSlot1(boolean slot1) {

            this.slot1 = slot1;
        }

        public boolean isSlot2() {

            return slot2;
        }

        public void setSlot2(boolean slot2) {

            this.slot2 = slot2;
        }

        public boolean isSlot3() {

            return slot3;
        }

        public void setSlot3(boolean slot3) {

            this.slot3 = slot3;
        }

        public boolean isSlot4() {

            return slot4;
        }

        public void setSlot4(boolean slot4) {

            this.slot4 = slot4;
        }

        public String getName1() {

            return name1;
        }

        public void setName1(String name1) {

            this.name1 = name1;
        }

        public int getServerPort1() {

            return serverPort1;
        }

        public void setServerPort1(int serverPort1) {

            this.serverPort1 = serverPort1;
        }

        public String getServerIP1() {

            return serverIP1;
        }

        public void setServerIP1(String serverIP1) {

            this.serverIP1 = serverIP1;
        }

        public String getName2() {

            return name2;
        }

        public void setName2(String name2) {

            this.name2 = name2;
        }

        public int getServerPort2() {

            return serverPort2;
        }

        public void setServerPort2(int serverPort2) {

            this.serverPort2 = serverPort2;
        }

        public String getServerIP2() {

            return serverIP2;
        }

        public void setServerIP2(String serverIP2) {

            this.serverIP2 = serverIP2;
        }

        public String getName3() {

            return name3;
        }

        public void setName3(String name3) {

            this.name3 = name3;
        }

        public int getServerPort3() {

            return serverPort3;
        }

        public void setServerPort3(int serverPort3) {

            this.serverPort3 = serverPort3;
        }

        public String getServerIP3() {

            return serverIP3;
        }

        public void setServerIP3(String serverIP3) {

            this.serverIP3 = serverIP3;
        }

        public String getName4() {

            return name4;
        }

        public void setName4(String name4) {

            this.name4 = name4;
        }

        public int getServerPort4() {

            return serverPort4;
        }

        public void setServerPort4(int serverPort4) {

            this.serverPort4 = serverPort4;
        }

        public String getServerIP4() {

            return serverIP4;
        }

        public void setServerIP4(String serverIP4) {

            this.serverIP4 = serverIP4;
        }
    }
