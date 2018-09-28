package gamePackage.client.menu;

import gamePackage.client.OnlineManager;
import graphics.Display;
import toolBox.Button;
import toolBox.DrawHelper;
import toolBox.ImageHelper;

import java.awt.event.MouseEvent;

    public class MultiPlayerMenu extends Menu {

                //Attribute
            private boolean crtlPressed;

                //Referenzen
            private Button join;
            private Button host;
            private Button back;

            private String username;
            private String gameType;
            private OnlineManager onlineManager;

        public MultiPlayerMenu(Display display, MenuController controller, String gameType, String username) {

            super(display, controller);

            this.username = username;
            this.gameType = gameType;
        }

        @Override
        public void init() {

            background = ImageHelper.getImage("res/images/menu/multi-menu.png");

                //Buttons
            this.join = new Button(315, 310, 330, 90, "res/images/menu/buttons/join-button", true);
            display.getActivePanel().drawObjectOnPanel(join);
            this.host = new Button(315, 470, 330, 90, "res/images/menu/buttons/host-button", true);
            display.getActivePanel().drawObjectOnPanel(host);
            this.back = new Button(30, 860, 155, 50, "res/images/menu/buttons/Back-button", true);
            display.getActivePanel().drawObjectOnPanel(back);
        }

        @Override
        public void remove() {

            display.getActivePanel().removeObjectFromPanel(join);
            display.getActivePanel().removeObjectFromPanel(host);
            join = null;
            display.getActivePanel().removeObjectFromPanel(back);
            host = null;
            back = null;
        }

        @Override
        public void draw(DrawHelper draw) {

                //draw background
            draw.drawImage(background, 0, 0, display.getWidth(), display.getHeight());

                //Button's
            draw.drawButton(join);
            draw.drawButton(host);
            draw.drawButton(back);
        }

        @Override
        public void update(double dt) {

            if(join.isClicked()) {

                controller.removeMultiplayerMenu();
                controller.createServerMenu(gameType);
            }

            else if(host.isClicked()) {

                onlineManager = new OnlineManager(display, gameType, username);
                remove();
                display.getActivePanel().removeObjectFromPanel(this);
            }

            //Zurück zum MainMenu
            else if(back.isClicked()) {

                controller.removeMultiplayerMenu();
                controller.createMainMenu();
            }
        }

        @Override
        public void mouseReleased(MouseEvent event) {

        }
    }