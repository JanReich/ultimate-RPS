package gamePackage.client.menu;

import graphics.Display;
import toolBox.DrawHelper;
import toolBox.ImageHelper;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Lobby extends Menu {

                //Attribute

                //Referenzen
            private BufferedImage playerLaser1;
            private BufferedImage playerLaser2;

        public Lobby(Display display) {

            super(display);
        }

        @Override
        public void init() {

            playerLaser1 = ImageHelper.getImage("res/images/lobby/player-laser1.png");
            playerLaser2 = ImageHelper.getImage("res/images/lobby/player-laser2.png");
            background = ImageHelper.getImage("res/images/lobby/lobby-menu.png");
        }

        @Override
        public void remove() {

        }

        @Override
        public void draw(DrawHelper draw) {

                //draw background
            draw.drawImage(background, 0, 0, display.getWidth(), display.getHeight());

            draw.drawImage(playerLaser1, 49, 265, 278, 109);
        }

        @Override
        public void mouseReleased(MouseEvent event) {

        }
    }
