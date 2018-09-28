package gamePackage.client.menu;

import graphics.Display;
import toolBox.Button;
import toolBox.DrawHelper;
import toolBox.ImageHelper;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

    public class Lobby extends Menu {

                //Attribute

                //Referenzen
            private BufferedImage playerLaser1;
            private BufferedImage playerLaser2;

            private Button playerClickToJoin1;
            private Button playerClickToJoin2;

            private BufferedImage clickSpectateButton;
            private BufferedImage clickSpectateButtonHover;
            private BufferedImage kickButton1;
            private BufferedImage kickButton2;
            private BufferedImage kickButton3;
            private BufferedImage kickButton4;
            private BufferedImage kickButtonHover;
            private BufferedImage lobbymenu;
            private BufferedImage notready1;
            private BufferedImage notready2;
            private BufferedImage ready;
            private BufferedImage ready2;
            private BufferedImage spectator1;
            private BufferedImage spectator2;
            private BufferedImage spectator3;

        public Lobby(Display display) {

            super(display);
        }

        @Override
        public void init() {

                //TODO: Buttons einfügen, dabei darauf achten, dass diese 4% kleiner werden!
                //TODO: ALLE BUTTONS schonaml an die richtigen Stellen setzen. Falls Sie
                //TODO: sich micht etwas anderem überlappen, dann die entsprechende Textur auskommentieren!

            playerLaser1 = ImageHelper.getImage("res/images/lobby/player-laser1.png");
            playerLaser2 = ImageHelper.getImage("res/images/lobby/player-laser2.png");

            playerClickToJoin1 = new Button(162, 285, 161, 61, "res/images/lobby/click-play-button", true);
            display.getActivePanel().drawObjectOnPanel(playerClickToJoin1);
            playerClickToJoin2 = new Button(348, 622, 161, 61, "res/images/lobby/click-play-button", true);
            display.getActivePanel().drawObjectOnPanel(playerClickToJoin2);

            background = ImageHelper.getImage("res/images/lobby/lobby-menu.png");

            clickSpectateButton =  ImageHelper.getImage("res/images/lobby/click-spectate-button.png");
            clickSpectateButtonHover = ImageHelper.getImage("res/images/lobby/click-spectate-button-hover.png");
            kickButton1 = ImageHelper.getImage("res/images/lobby/kick-button.png");
            kickButton2 = ImageHelper.getImage("res/images/lobby/kick-button.png");
            kickButton3 = ImageHelper.getImage("res/images/lobby/kick-button.png");
            kickButton4 = ImageHelper.getImage("res/images/lobby/kick-button.png");
            kickButtonHover = ImageHelper.getImage("res/images/lobby/kick-button-hover.png");
            notready1 = ImageHelper.getImage("res/images/lobby/notready-button1.png");
            notready2 = ImageHelper.getImage("res/images/lobby/notready-button2.png");
            ready = ImageHelper.getImage("res/images/lobby/ready-button.png");
            ready2 = ImageHelper.getImage("res/images/lobby/ready-button-hover.png");
            spectator1 = ImageHelper.getImage("res/images/lobby/spectator-tab.png");
            spectator2 = ImageHelper.getImage("res/images/lobby/spectator-tab.png");
            spectator3 = ImageHelper.getImage("res/images/lobby/spectator-tab.png");

        }

        @Override
        public void remove() {

            display.getActivePanel().removeObjectFromPanel(playerClickToJoin1);
            playerClickToJoin1 = null;
            display.getActivePanel().removeObjectFromPanel(playerClickToJoin2);
            playerClickToJoin2 = null;
        }

        @Override
        public void draw(DrawHelper draw) {

                //draw background
            draw.drawImage(background, 0, 0, display.getWidth(), display.getHeight());

            draw.drawImage(playerLaser1, 49, 265, 278, 109);
            draw.drawImage(playerLaser2, 348, 600, 278, 109);
            draw.drawImage(kickButton1, 333 , 317 ,70, 26);
            draw.drawImage(kickButton2, 270 , 653 ,70, 26);
            draw.drawImage(notready1, 330 , 289 ,79, 26);
            draw.drawImage(notready2, 270 , 625 ,70, 26);
            draw.drawImage(ready, 330 , 289 ,79, 26);
            draw.drawImage(ready2, 270 , 625 ,70, 26);
            draw.drawImage(spectator1, 675 , 130,260, 60);
            draw.drawImage(spectator2, 675 , 200,260, 60);
            draw.drawImage(spectator3, 675 , 270,260, 60);
            draw.drawImage(kickButton3, 853 , 140 ,70, 33);
            draw.drawImage(kickButton4, 853 , 210 ,70, 33);
            //Abstand von y immer +70


            //draw.drawButton(playerClickToJoin1);
            //draw.drawButton(playerClickToJoin2);
        }

        @Override
        public void mouseReleased(MouseEvent event) {

        }
    }
