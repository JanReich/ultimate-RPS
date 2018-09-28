package gamePackage.client.menu;

import gamePackage.client.GameClient;
import graphics.Display;
import toolBox.Button;
import toolBox.DrawHelper;
import toolBox.ImageHelper;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

    public class Lobby extends Menu {

                //Attribute
            private boolean readyPlayer1;
            private boolean readyPlayer2;

                //Referenzen
            private GameClient gameClient;
            private BufferedImage playerLaser1;
            private BufferedImage playerLaser2;

            private Button playerClickToJoin1;
            private Button playerClickToJoin2;

                //Button's
            private Button ready1;
            private Button ready2;

            private Button unReady1;
            private Button unReady2;

            private Button kick1;
            private Button kick2;

        public Lobby(Display display, GameClient gameClient) {

            super(display);
            this.gameClient = gameClient;
        }

        @Override
        public void init() {

                //background
            background = ImageHelper.getImage("res/images/lobby/lobby-menu.png");
                //PlayerLaser
            playerLaser1 = ImageHelper.getImage("res/images/lobby/player-laser1.png");
            playerLaser2 = ImageHelper.getImage("res/images/lobby/player-laser2.png");
                //ReadyButton's
            ready1 = new Button(330 , 289 ,79, 26, "res/images/lobby/ready-button", true);
            ready2 = new Button(270 , 625 ,70, 26, "res/images/lobby/ready-button", true);
                //UnReadyButton
            unReady1 = new Button(270 , 625 ,70, 26, "res/images/lobby/unReady-button", true);
            unReady2 = new Button(270 , 625 ,70, 26, "res/images/lobby/unReady-button", true);
                //KickButton
            kick1 = new Button(333 , 317 ,70, 26, "res/images/lobby/kick-button", true);
            kick2 = new Button(270 , 653 ,70, 26, "res/images/lobby/kick-button", true);


                //Add to Display
            display.getActivePanel().drawObjectOnPanel(ready1);
            display.getActivePanel().drawObjectOnPanel(ready2);

            display.getActivePanel().drawObjectOnPanel(kick1);
            display.getActivePanel().drawObjectOnPanel(kick2);


            //playerClickToJoin1 = new Button(162, 285, 161, 61, "res/images/lobby/click-play-button", true);
            //playerClickToJoin2 = new Button(348, 622, 161, 61, "res/images/lobby/click-play-button", true);






            /*clickSpectateButton =  ImageHelper.getImage("res/images/lobby/click-spectate-button.png");
            clickSpectateButtonHover = ImageHelper.getImage("res/images/lobby/click-spectate-button-hover.png");
            kickButton1 = ImageHelper.getImage("res/images/lobby/kick-button.png");
            kickButton2 = ImageHelper.getImage("res/images/lobby/kick-button.png");
            kickButton3 = ImageHelper.getImage("res/images/lobby/kick-button.png");
            kickButton4 = ImageHelper.getImage("res/images/lobby/kick-button.png");
            kickButtonHover = ImageHelper.getImage("res/images/lobby/kick-button-hover.png");

            spectator1 = ImageHelper.getImage("res/images/lobby/spectator-tab.png");
            spectator2 = ImageHelper.getImage("res/images/lobby/spectator-tab.png");
            spectator3 = ImageHelper.getImage("res/images/lobby/spectator-tab.png");*/

        }

        @Override
        public void remove() {

            display.getActivePanel().removeObjectFromPanel(kick1);
            display.getActivePanel().removeObjectFromPanel(kick2);
            display.getActivePanel().removeObjectFromPanel(unReady1);
            display.getActivePanel().removeObjectFromPanel(unReady2);
        }

        @Override
        public void draw(DrawHelper draw) {

                //draw background
            draw.drawImage(background, 0, 0, display.getWidth(), display.getHeight());
                //draw PlayerLaser
            draw.drawImage(playerLaser1, 49, 265, 278, 109);
            draw.drawImage(playerLaser2, 348, 600, 278, 109);
                //draw KickButton's
            draw.drawButton(kick1);
            draw.drawButton(kick2);


                //draw ReadyButton's
            if(!readyPlayer1) {

                draw.drawButton(ready1);
            } else {

                draw.drawButton(unReady1);
            }

            if(!readyPlayer2) {

                draw.drawButton(ready2);
            } else {

                draw.drawButton(unReady2);
            }

            /*draw.drawImage(kickButton1, 333 , 317 ,70, 26);
            draw.drawImage(kickButton2, 270 , 653 ,70, 26);
            draw.drawImage(spectator1, 675 , 130,260, 60);
            draw.drawImage(spectator2, 675 , 200,260, 60);
            draw.drawImage(spectator3, 675 , 270,260, 60);
            draw.drawImage(kickButton3, 853 , 140 ,70, 33);
            draw.drawImage(kickButton4, 853 , 210 ,70, 33);*/
            //Abstand von y immer +70


            //draw.drawButton(playerClickToJoin1);
            //draw.drawButton(playerClickToJoin2);
        }

        @Override
        public void update(double dt) {

            if(ready1 != null) {

                if(!gameClient.getData().isSpectator() && gameClient.getData().getClientID() == 0) {

                    if(ready1.isClicked())
                        gameClient.setReady(!readyPlayer1, 0);
                }
            }

            if(unReady1 != null) {

                if(!gameClient.getData().isSpectator() && gameClient.getData().getClientID() == 1) {

                    if(unReady2.isClicked())
                        gameClient.setReady(!readyPlayer1, 0);
                }
            }
        }


        public void setReadyButton(int client, boolean ready) {

            System.out.println("test");
                if(ready) {

                    if(client == 0) {

                        display.getActivePanel().drawObjectOnPanel(unReady1);
                        display.getActivePanel().removeObjectFromPanel(ready1);
                        this.readyPlayer1 = ready;
                    }
                }


        }

        @Override
        public void mouseReleased(MouseEvent event) {

        }
    }
