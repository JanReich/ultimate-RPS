package gamePackage.client.menu;

import graphics.Display;
import toolBox.DrawHelper;
import toolBox.ImageHelper;
import toolBox.TextTransfer;

import java.awt.event.MouseEvent;

    public class MainMenu extends Menu {

                    //Attribute

                    //Referenzen

            public MainMenu(Display display) {

                super(display);
            }

        @Override
        public void init() {

            background = ImageHelper.getImage("res/images/menu/main-menu-temp.png");
        }

        @Override
        public void draw(DrawHelper draw) {

                //draw Background
            draw.drawImage(background, 0, 0, display.getWidth(), display.getHeight());
        }

        @Override
        public void mouseReleased(MouseEvent event) {

        }
    }
