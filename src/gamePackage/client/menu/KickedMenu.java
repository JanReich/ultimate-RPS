package gamePackage.client.menu;

import graphics.Display;
import toolBox.Animation;
import toolBox.Button;
import toolBox.DrawHelper;

import java.awt.event.MouseEvent;

    public class KickedMenu extends Menu {

                    //Attribute

                    //Referenzen
                private Button back;
                private Animation animation;

        public KickedMenu(Display display) {

            super(display);

        }
        @Override
        public void init() {

            back = new Button(10, 825, 275, 100, "res/images/menu/buttons/back-to-menu", true);
            display.getActivePanel().drawObjectOnPanel(back);
            animation = new Animation("res/images/menu/kicked-screen.png", 0.04, 21, 0, true);
            display.getActivePanel().drawObjectOnPanel(animation);
        }

        @Override
        public void remove() {

            display.getActivePanel().removeObjectFromPanel(animation);
            display.getActivePanel().removeObjectFromPanel(back);
            display.getActivePanel().removeObjectFromPanel(this);
            back = null;
            animation = null;
        }

        @Override
        public void update(double delta) {

            if(back.isClicked()) {

                remove();
                MenuController controller = new MenuController(display);
                display.getActivePanel().addManagement(controller);
            }
        }

        @Override
        public void draw(DrawHelper draw) {

            draw.drawImage(animation.getAnimation(), 0, 0, display.getWidth(), display.getHeight());
            draw.drawButton(back);
        }

        @Override
        public void mouseReleased(MouseEvent event) {

        }
    }
