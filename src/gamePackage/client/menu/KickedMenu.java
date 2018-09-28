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

            back = new Button(100, 100, 100, 80, "res/images/menu/buttons/kicked-back", true);
            display.getActivePanel().drawObjectOnPanel(back);
            animation = new Animation("res/images/menu/kicked-screen.png", 0.04, 22, 0, true);
            display.getActivePanel().drawObjectOnPanel(animation);
        }

        @Override
        public void remove() {

            display.getActivePanel().removeObjectFromPanel(animation);
            display.getActivePanel().removeObjectFromPanel(back);
            back = null;
            animation = null;
        }

        @Override
        public void update(double delta) {

            if(back.isClicked()) {


            }
        }

        @Override
        public void draw(DrawHelper draw) {

            draw.drawImage(animation.getAnimation(), 0, 0, display.getWidth(), display.getHeight());
        }

        @Override
        public void mouseReleased(MouseEvent event) {

        }
    }
