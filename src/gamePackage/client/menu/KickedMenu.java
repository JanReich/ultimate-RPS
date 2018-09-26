package gamePackage.client.menu;

import graphics.Display;
import toolBox.Button;
import toolBox.DrawHelper;

import java.awt.event.MouseEvent;

    public class KickedMenu extends Menu {

                    //Attribute

                    //Referenzen
                private Button back;

        public KickedMenu(Display display) {

            super(display);
        }

        @Override
        public void init() {

            back = new Button(100, 100, 100, 80, "res/images/menu/buttons/", true)
        }

        @Override
        public void draw(DrawHelper draw) {

        }

        @Override
        public void mouseReleased(MouseEvent event) {

        }
    }
