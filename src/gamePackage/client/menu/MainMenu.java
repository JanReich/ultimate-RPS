package gamePackage.client.menu;

import graphics.Display;
import toolBox.Button;
import toolBox.DrawHelper;
import toolBox.ImageHelper;

import java.awt.event.MouseEvent;

    public class MainMenu extends Menu {

                    //Attribute

                    //Referenzen
                private Button duell;
                private Button tournament;
                private Button singleplayer;

            public MainMenu(Display display, MenuController controller) {

                super(display, controller);
            }

        @Override
        public void init() {

            background = ImageHelper.getImage("res/images/menu/main-menu-temp.png");

                //Button's
            this.singleplayer = new Button(305, 380, 350, 85, "res/images/menu/buttons/sing-button", true);
            display.getActivePanel().drawObjectOnPanel(singleplayer);
            this.duell = new Button(75, 600, 350, 85,"res/images/menu/buttons/duell-button", true);
            display.getActivePanel().drawObjectOnPanel(duell);
            this.tournament = new Button(540, 600, 350, 85,"res/images/menu/buttons/tournament-button", true);
            display.getActivePanel().drawObjectOnPanel(tournament);
        }

        public void remove() {

                //remove buttons
            display.getActivePanel().removeObjectFromPanel(duell);
            display.getActivePanel().removeObjectFromPanel(tournament);
            display.getActivePanel().removeObjectFromPanel(singleplayer);

            duell = null;
            tournament = null;
            singleplayer = null;
        }

        @Override
        public void draw(DrawHelper draw) {

                //draw Background
            draw.drawImage(background, 0, 0, display.getWidth(), display.getHeight());

                //Button's
            draw.drawButton(duell);
            draw.drawButton(tournament);
            draw.drawButton(singleplayer);
        }

        @Override
        public void update(double dt) {

            if(singleplayer.isClicked()) {

                    //Starte den Singleplayer-Modus
                display.getActivePanel().removeObjectFromPanel(controller);
                controller.removeMainMenu();

            }

                //Starte MultiMenu
            else if(duell.isClicked()) {

                controller.removeMainMenu();
                controller.createNameMenu(0);
            }

                //Starte Tunier-Menü
            else if(tournament.isClicked()) {

                controller.removeMainMenu();
                controller.createNameMenu(1);
            }
        }

        @Override
        public void mouseReleased(MouseEvent event) {

        }
    }
