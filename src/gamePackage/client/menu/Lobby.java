package gamePackage.client.menu;

import gamePackage.client.GameClient;
import gamePackage.server.ClientData;
import graphics.Display;
import toolBox.Button;
import toolBox.DrawHelper;
import toolBox.ImageHelper;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

public class Lobby extends Menu {

                //Attribute
            private boolean maxSpec;
            private double countdown;
            private double errorTime;
            private boolean readyPlayer1;
            private boolean readyPlayer2;

                //Referenzen
            private GameClient gameClient;
            private ToSpecSlot toSpecSlot;
            private BufferedImage playerLaser1;
            private BufferedImage playerLaser2;

            private ArrayList<SpectatorSlot> slots;

            private Button playerClickToJoin1;
            private Button playerClickToJoin2;

                //Button's
            private Button ready1;
            private Button ready2;

            private Button unReady1;
            private Button unReady2;

            private Button kick1;
            private Button kick2;

            private Button disconnect;

        public Lobby(Display display, GameClient gameClient) {

            super(display);
            this.gameClient = gameClient;

            //DisconnectButton
            if(gameClient.getData().isHost())
                disconnect = new Button(683, 810, 240, 60, "res/images/menu/buttons/disconect", true);
            else
                disconnect = new Button(683, 810, 240, 60, "res/images/menu/buttons/disconect", true);
            display.getActivePanel().drawObjectOnPanel(disconnect);
        }

        @Override
        public void init() {

            slots = new ArrayList<>();
            countdown = -1;

                //background
            background = ImageHelper.getImage("res/images/lobby/lobby-menu.png");
                //PlayerLaser
            playerLaser1 = ImageHelper.getImage("res/images/lobby/player-laser1.png");
            playerLaser2 = ImageHelper.getImage("res/images/lobby/player-laser2.png");
                //ReadyButton's
            ready1 = new Button(329 , 289 ,79, 26, "res/images/lobby/ready-button", true);
            ready2 = new Button(267 , 625 ,79, 26, "res/images/lobby/ready-button", true);
                //UnReadyButton
            unReady1 = new Button(328 , 289 ,79, 26, "res/images/lobby/unReady-button", true);
            unReady2 = new Button(266 , 625 ,79, 26, "res/images/lobby/unReady-button", true);
                //KickButton
            kick1 = new Button(333 , 317 ,70, 26, "res/images/lobby/kick-button", true);
            kick2 = new Button(270 , 653 ,70, 26, "res/images/lobby/kick-button", true);

                //Add to Display
            display.getActivePanel().drawObjectOnPanel(kick1);
            display.getActivePanel().drawObjectOnPanel(kick2);

            playerClickToJoin1 = new Button(162, 285, 161, 61, "res/images/lobby/click-play-button", true);
            playerClickToJoin2 = new Button(348, 622, 161, 61, "res/images/lobby/click-play-button", true);
        }

        @Override
        public void remove() {

            display.getActivePanel().removeObjectFromPanel(kick1);
            display.getActivePanel().removeObjectFromPanel(kick2);
            display.getActivePanel().removeObjectFromPanel(unReady1);
            display.getActivePanel().removeObjectFromPanel(unReady2);
            display.getActivePanel().removeObjectFromPanel(disconnect);

            display.getActivePanel().removeObjectFromPanel(this);
        }

        @Override
        public void draw(DrawHelper draw) {

                //draw background
            draw.drawImage(background, 0, 0, display.getWidth(), display.getHeight());
                //draw KickButton's
            draw.drawButton(kick1);
            draw.drawButton(kick2);
            draw.drawButton(disconnect);
            if(toSpecSlot != null) draw.drawButton(toSpecSlot.getClickToSpectate());

            if(readyPlayer1) {
                display.getActivePanel().removeObjectFromPanel(ready1);
                if(!display.getActivePanel().contains(unReady1)) display.getActivePanel().drawObjectOnPanel(unReady1);
                draw.drawButton(unReady1);
            } else {
                draw.drawButton(ready1);
                display.getActivePanel().removeObjectFromPanel(unReady1);
                if(!display.getActivePanel().contains(ready1)) display.getActivePanel().drawObjectOnPanel(ready1);
            }

            if(readyPlayer2) {
                display.getActivePanel().removeObjectFromPanel(ready2);
                if(!display.getActivePanel().contains(unReady2)) display.getActivePanel().drawObjectOnPanel(unReady2);
                draw.drawButton(unReady2);
            } else {
                display.getActivePanel().removeObjectFromPanel(unReady2);
                if(!display.getActivePanel().contains(ready2)) display.getActivePanel().drawObjectOnPanel(ready2);
                draw.drawButton(ready2);
            }

            if(gameClient.getData().isSpectator()) {

                if(gameClient.getConnectedPlayers().get(0) == null) {

                    if(!display.getActivePanel().contains(playerClickToJoin1)) {

                        display.getActivePanel().drawObjectOnPanel(playerClickToJoin1);
                    }
                    draw.drawButton(playerClickToJoin1);
                }
                else draw.drawImage(playerLaser1, 49, 265, 278, 109);
                if(gameClient.getConnectedPlayers().get(1) == null) {

                    if(!display.getActivePanel().contains(playerClickToJoin2)) {

                        display.getActivePanel().drawObjectOnPanel(playerClickToJoin2);
                    }
                    draw.drawButton(playerClickToJoin2);
                }
                else draw.drawImage(playerLaser2, 348, 600, 278, 109);
            } else {

                    //draw PlayerLaser
                draw.drawImage(playerLaser1, 49, 265, 278, 109);
                draw.drawImage(playerLaser2, 348, 600, 278, 109);
            }

            if(gameClient != null) {

                draw.setColour(Color.BLACK);
                draw.setFont(new Font("Impact", Font.PLAIN, 30));
                if(gameClient.getConnectedPlayers().get(0) != null) draw.drawString(gameClient.getConnectedPlayers().get(0).getUsername(), 90, 330);
                if(gameClient.getConnectedPlayers().get(1) != null) draw.drawString(gameClient.getConnectedPlayers().get(1).getUsername(), 365, 665);
            }

            if(errorTime > 0) {

                draw.setColour(Color.RED);
                draw.setFont(new Font("Impact", Font.PLAIN, 28));
                draw.drawString("Du hast keine Berechtigung um diesen Client zu kicken!", 10, 100);
            }

            for (int i = 0; i < slots.size(); i++) {

                draw.setColour(Color.BLACK);
                draw.setFont(new Font("Impact", Font.PLAIN, 18));
                draw.drawImage(slots.get(i).getSlot(), slots.get(i).getX(), slots.get(i).getY(), slots.get(i).getWidth(), slots.get(i).getHeight());
                draw.drawString(slots.get(i).getName(), slots.get(i).getX() + 22, slots.get(i).getY() + 30);
                draw.drawButton(slots.get(i).getKick());
            }

            if(countdown >= 0) {

                draw.setColour(Color.RED);
                draw.setFont(new Font("Verdana", Font.BOLD, 400));
                if(countdown > 1) {
                    draw.drawString((int) countdown + "", 200, 620);
                } else {
                    draw.setFont(new Font("Verdana", Font.BOLD, 100));
                    draw.drawString("Start", 200, 200);
                }
            }
        }

        @Override
        public void update(double dt) {

            if(gameClient.getConnectedPlayers().get(0) != null) readyPlayer1 = gameClient.getConnectedPlayers().get(0).isReady();
            if(gameClient.getConnectedPlayers().get(1) != null) readyPlayer2 = gameClient.getConnectedPlayers().get(1).isReady();

            if(errorTime > 0) errorTime -= dt;
            if(countdown > 0) countdown -= dt;

            if(countdown <= 0 && countdown != -1) {

                if(!gameClient.getData().isSpectator()) {

                    gameClient.countdownOver();
                }
            }

            if(toSpecSlot != null) {

                if(toSpecSlot.getClickToSpectate().isClicked() && !maxSpec) {

                    if(!gameClient.getData().isSpectator()) {

                        boolean temp = true;

                        for(Map.Entry<Integer, ClientData> entry : gameClient.getConnectedSpectators().entrySet()) {

                            if(entry.getValue().getSpectatorID() == toSpecSlot.getSpecID()) {

                                temp = false;
                            }
                        }

                        if(temp) gameClient.playerToSpectator(gameClient.getData().getClientID(), toSpecSlot.getSpecID());
                    }
                }
            }

            if(disconnect.isClicked()) {

                if(gameClient.getData().isHost()) gameClient.closeServer();
                else gameClient.disconnect();
            }

            if(readyPlayer1) {

                if(unReady1.isClicked() && gameClient.getData().getClientID() == 0) {

                    gameClient.setReady(false, 0);
                }
            } else {

                if(ready1.isClicked() && gameClient.getData().getClientID() == 0) {

                    gameClient.setReady(true, 0);
                }
            }

            if(readyPlayer2) {

                if(unReady2.isClicked() && gameClient.getData().getClientID() == 1) {

                    gameClient.setReady(false, 1);
                }
            } else {

                if(ready2.isClicked() && gameClient.getData().getClientID() == 1) {

                    gameClient.setReady(true, 1);
                }
            }

            if(playerClickToJoin1 != null)
                if(playerClickToJoin1.isClicked()) {

                    gameClient.spectatorToPlayer(gameClient.getData().getSpectatorID(), 0);
                }

            if(playerClickToJoin2 != null)
                if(playerClickToJoin2.isClicked()) {

                    gameClient.spectatorToPlayer(gameClient.getData().getSpectatorID(), 1);
                }

            if(kick1.isClicked()) {

                if(gameClient.getData().isHost()) {

                    if(gameClient.getConnectedPlayers().get(0) != null) {

                        if(!gameClient.getConnectedPlayers().get(0).isHost()) {

                            gameClient.kickPlayer(0);
                        }
                    }
                } else errorTime = 3;
            }

            if(kick2.isClicked()) {

                if(gameClient.getData().isHost()) {

                    if(gameClient.getConnectedPlayers().get(1) != null) {

                        if(!gameClient.getConnectedPlayers().get(1).isHost()) {

                            gameClient.kickPlayer(1);
                        }
                    }
                } else errorTime = 3;
            }

            for (int i = 0; i < slots.size(); i++) {

                if(slots.get(i).getKick().isClicked()) {

                    if(gameClient.getData().isHost()) {

                        if(gameClient.getConnectedSpectators().get(slots.get(i).spectatorID) != null) {

                            if(gameClient.getConnectedSpectators().get(slots.get(i).spectatorID).isHost()) {

                                gameClient.kickSpectator(slots.get(i).getSpectatorID());
                            }
                        }
                    } else errorTime = 3;
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        public void createSpectatorSlot(int spectatorID, String name) {

            SpectatorSlot slot = new SpectatorSlot(678 , 140 + 115 * spectatorID, 250, 50, spectatorID, name);
            display.getActivePanel().drawObjectOnPanel(slot.getKick());
            slots.add(slot);
        }

        public void removeSpectatorSlot(int spectatorID) {

            for (int i = 0; i < slots.size(); i++) {

                if(slots.get(i).getSpectatorID() == spectatorID) {

                    display.getActivePanel().removeObjectFromPanel(slots.get(i).getKick());
                    slots.remove(i);
                }
            }
        }

        public void setCountdown(int countdown) {

            this.countdown = countdown;
        }

        public void removeToSpectator(int specID) {

            if(toSpecSlot != null) {

                if(toSpecSlot.getSpecID() == specID) {

                    display.getActivePanel().removeObjectFromPanel(toSpecSlot.getClickToSpectate());
                }
            }
        }

        public void createToSpectator(int specID) {

            if(specID != -1) {

                if(toSpecSlot != null) {

                    if(display.getActivePanel().contains(toSpecSlot.getClickToSpectate())) {

                        display.getActivePanel().removeObjectFromPanel(toSpecSlot.getClickToSpectate());
                    }
                }
                toSpecSlot = new ToSpecSlot(specID);
                display.getActivePanel().drawObjectOnPanel(toSpecSlot.getClickToSpectate());
            } else {

                if(toSpecSlot != null) {

                    if(display.getActivePanel().contains(toSpecSlot.getClickToSpectate())) {

                        display.getActivePanel().removeObjectFromPanel(toSpecSlot.getClickToSpectate());
                    }
                }
                toSpecSlot = null;
                maxSpec = true;
            }
        }

        public void setMaxSpec(boolean spec) {

            maxSpec = spec;
        }

        private class ToSpecSlot {

                    //Attribute
                private int specID;

                    //Referenzen
                private Button clickToSpectate;

            public ToSpecSlot(int specID) {

                this.specID = specID;
                clickToSpectate = new Button(678 , 140 + 115 * specID, 250, 50, "res/images/lobby/click-spectate-button", true);
            }

            public Button getClickToSpectate() {

                return clickToSpectate;
            }

            public int getSpecID() {

                return specID;
            }
        }

        private class SpectatorSlot {

                    //Attribute
                private int x;
                private int y;
                private int width;
                private int height;
                private String name;
                private int spectatorID;

                    //Referenzen
                private Button kick;
                private BufferedImage slot;

            public SpectatorSlot(int x, int y, int width, int height, int spectatorID, String name) {

                this.x = x;
                this.y = y;
                this.name = name;
                this.width = width;
                this.height = height;
                this.spectatorID = spectatorID;
                this.kick = new Button(x + 170, y + 7, 70, 33, "res/images/lobby/kick-button", true);
                this.slot  = ImageHelper.getImage("res/images/lobby/spectator-tab.png");
            }

            public int getX() {

                return x;
            }

            public int getY() {

                return y;
            }

            public int getWidth() {

                return width;
            }

            public int getHeight() {

                return height;
            }

            public BufferedImage getSlot() {

                return slot;
            }

            public int getSpectatorID() {

                return spectatorID;
            }

            public String getName() {

                return name;
            }

            public Button getKick() {

                return kick;
            }
        }
    }
