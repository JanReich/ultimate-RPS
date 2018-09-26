package gamePackage.client.menu;

import graphics.Display;
import toolBox.Button;
import toolBox.DrawHelper;
import toolBox.ImageHelper;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

    public class ServerMenu extends Menu {

                //Attribute

                //Referenzen
            private BufferedImage popup;

                //Button's

            private Button back;
            private Button close;
            private Button popupAdd;

            private Button add;
            private Button direct;

        public ServerMenu(Display display, MenuController controller) {

            super(display, controller);
        }

        @Override
        public void init() {

            popup = ImageHelper.getImage("res/images/menu/add-popup.png");
            background = ImageHelper.getImage("res/images/menu/server-menu.png");

            this.close = new Button(763, 323, 45, 45, "res/images/menu/buttons/close-button", true);
            display.getActivePanel().drawObjectOnPanel(close);

            this.back = new Button(30, 860, 155, 50, "res/images/menu/buttons/Back-button", true);
            display.getActivePanel().drawObjectOnPanel(back);

            this.add = new Button(228, 796, 166, 60, "res/images/menu/buttons/Add-server-button",true);
            display.getActivePanel().drawObjectOnPanel(add);

            this.direct = new Button(580, 796, 166, 60, "res/images/menu/buttons/Add-server-button",true);
            display.getActivePanel().drawObjectOnPanel(direct);
        }

        @Override
        public void remove() {

            display.getActivePanel().removeObjectFromPanel(add);
            add = null;
            display.getActivePanel().removeObjectFromPanel(back);
            back = null;
            display.getActivePanel().removeObjectFromPanel(direct);
            direct = null;
        }

        @Override
        public void draw(DrawHelper draw) {

                //draw background
            draw.drawImage(background, 0, 0, display.getWidth(), display.getHeight());

                //draw popup
            draw.drawImage(popup, 115, 250, 730, 450);

                //button's
            draw.drawButton(back);
            draw.drawButton(close);
            draw.drawButton(add);
            draw.drawButton(direct);
        }

        @Override
        public void mouseReleased(MouseEvent event) {

        }

        @Override
        public void update(double dt) {

            if(back.isClicked()) {

                controller.removeServerMenu();
                controller.createMultiplayerMenu();
            }
        }
    }
