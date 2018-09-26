package gamePackage.client.menu;

import graphics.Display;
import toolBox.DrawHelper;
import toolBox.ImageHelper;
import toolBox.TextTransfer;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

    public class MultiPlayerMenu extends Menu {

                //Attribute
            private boolean crtlPressed;

                //Referenzen

        public MultiPlayerMenu(Display display) {

            super(display);
        }

        @Override
        public void init() {

            background = ImageHelper.getImage("res/images/menu/multi-menu.png");
        }

        @Override
        public void draw(DrawHelper draw) {

                //draw background
            draw.drawImage(background, 0, 0, display.getWidth(), display.getHeight());
        }

        @Override
        public void mouseReleased(MouseEvent event) {

        }
    }
