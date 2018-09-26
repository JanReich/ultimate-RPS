package gamePackage.client.menu;

import graphics.Display;
import toolBox.Button;
import toolBox.DrawHelper;

import java.awt.event.MouseEvent;

    public class KickedMenu extends Menu {

                    //Attribute

                    //Referenzen
                private Button back;
                private MenuController controller;

        public KickedMenu(Display display, MenuController controller) {

            super(display);

            this.controller = controller;
        }

        @Override
        public void init() {

            back = new Button(100, 100, 100, 80, "res/images/menu/buttons/kicked-back", true);
            display.getActivePanel().drawObjectOnPanel(back);
        }

        @Override
        public void update(double delta) {

            if(back.isClicked()) {


            }
        }

        @Override
        public void draw(DrawHelper draw) {

        }

        @Override
        public void mouseReleased(MouseEvent event) {

        }
    }
