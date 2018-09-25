package gamePackage.client.menu;

import graphics.Display;
import toolBox.DrawHelper;
import toolBox.ImageHelper;

import java.awt.event.MouseEvent;

    public class ServerMenu extends Menu {

                    //Attribute

                    //Referenzen

            public ServerMenu(Display display) {

                super(display);
            }

        @Override
        public void init() {

            background = ImageHelper.getImage("res/images/menu/server-menu.png");
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
