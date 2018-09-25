package gamePackage.client.menu;

import graphics.Display;
import graphics.interfaces.ManagementObject;
import toolBox.Button;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

    public class MenuController implements ManagementObject {

                //Attribute
            /**
             * -1 = Fehler - kein aktives menu
             *  0 = MainMenu
             *  1 = MultiMenu
             *  2 = ServerMenu
             */
            private int menuType;

                //Referenzen
            private Display display;

                    //different menu's
                private MainMenu mainMenu;
                private ServerMenu serverMenu;
                private MultiPlayerMenu multiplayerMenu;

                    //MainMenu button's
                private Button duell;
                private Button tournament;
                private Button singleplayer;

                    //MultiMenu button's
                private Button join;
                private Button host;
                private Button back;

        public MenuController(Display display) {

            this.display = display;

            init();
        }

        private void init() {

                //Menu's initialisieren
            this.mainMenu = new MainMenu(display);
            this.serverMenu = new ServerMenu(display);
            this.multiplayerMenu = new MultiPlayerMenu(display);

            createMainMenu();

            this.join = new Button(315, 310, 330, 90, "res/images/menu/buttons/sing-button", true);
            this.host = new Button(315, 470, 330, 90, "res/images/menu/buttons/sing-button", true);
            this.back = new Button(70, 810, 155, 50, "res/images/menu/buttons/sing-button", true);
        }

        private void createMainMenu() {

            display.getActivePanel().drawObjectOnPanel(mainMenu);

                //MainMenu Buttons
            this.singleplayer = new Button(305, 380, 350, 85, "res/images/menu/buttons/sing-button", true);
            display.getActivePanel().drawObjectOnPanel(singleplayer);
            this.duell = new Button(75, 600, 350, 85,"res/images/menu/buttons/sing-button", true);
            display.getActivePanel().drawObjectOnPanel(duell);
            this.tournament = new Button(540, 600, 350, 85,"res/images/menu/buttons/sing-button", true);
            display.getActivePanel().drawObjectOnPanel(tournament);
        }


        @Override
        public void update(double delta) {

                //MainMenu
            if(menuType == 0) {

                if(singleplayer.isClicked() || duell.isClicked() || tournament.isClicked()) {

                        //Remove buttons
                    display.getActivePanel().removeObjectFromPanel(duell);
                    display.getActivePanel().removeObjectFromPanel(tournament);
                    display.getActivePanel().removeObjectFromPanel(singleplayer);

                        //Remove menu
                    display.getActivePanel().removeObjectFromPanel(mainMenu);
                }

                    //Rufe das ServerMenu auf und entferne alle Elemente des MainMenu
                if(singleplayer.isClicked()) {

                        //Starte den Singleplayer-Modus
                    menuType = -1;
                    display.getActivePanel().removeObjectFromPanel(this);

                }

                    //Starte MultiMenu
                else if(duell.isClicked()) {

                    menuType = 1;
                    display.getActivePanel().drawObjectOnPanel(multiplayerMenu);

                        //Button hinzufügen
                    display.getActivePanel().drawObjectOnPanel(join);
                    display.getActivePanel().drawObjectOnPanel(host);
                    display.getActivePanel().drawObjectOnPanel(back);
                }

                    //Starte Tunier-Menü
                else if(tournament.isClicked()) {

                    //TODO: WENN TEXTUR
                }
            }

                //MultiMenu
            else if(menuType == 1) {

                if(back.isClicked() || join.isClicked() || host.isClicked()) {

                    display.getActivePanel().removeObjectFromPanel(join);
                    display.getActivePanel().removeObjectFromPanel(host);
                    display.getActivePanel().removeObjectFromPanel(back);
                }

                    //Zurück zum MainMenu
                if(back.isClicked()) {

                    menuType = 0;
                    createMainMenu();
                }
            }

                //ServerMenu
            else if(menuType == 2) {


            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

            System.out.println("X: " + e.getX() + " - Y: " + e.getY());
        }
    }
