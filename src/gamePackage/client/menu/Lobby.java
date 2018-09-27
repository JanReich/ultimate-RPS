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

            //draw.drawButton(playerClickToJoin1);
            //draw.drawButton(playerClickToJoin2);
        }

        @Override
        public void mouseReleased(MouseEvent event) {

        }
    }
