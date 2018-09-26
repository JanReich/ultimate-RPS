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
             *  3 = NameMenu
             *  4= TournamentMenu
             */
            private int menuType;

                //Referenzen
            private Display display;

                //different menu's
            private MainMenu mainMenu;
            private NameMenu nameMenu;
            private ServerMenu serverMenu;
            private MultiPlayerMenu multiplayerMenu;

        public MenuController(Display display) {

            this.display = display;

            createMainMenu();
        }

        @Override
        public void update(double delta) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

            @Override
        public void mouseReleased(MouseEvent e) {

            System.out.println("X: " + e.getX() + " - Y: " + e.getY());
        }

        //
        // TYPE: 0 - MainMenu
        //

        public void createMainMenu() {

            this.mainMenu = new MainMenu(display, this);
            display.getActivePanel().drawObjectOnPanel(mainMenu);
        }


        public void removeMainMenu() {

            mainMenu.remove();
            display.getActivePanel().removeObjectFromPanel(mainMenu);
            mainMenu = null;
        }

        //
        // TYPE: 1 - MultiplayerMenu
        //

        public void createMultiplayerMenu() {

            this.multiplayerMenu = new MultiPlayerMenu(display, this);
            display.getActivePanel().drawObjectOnPanel(multiplayerMenu);
        }

        public void removeMultiplayerMenu()  {

            multiplayerMenu.remove();
            display.getActivePanel().removeObjectFromPanel(multiplayerMenu);
            multiplayerMenu = null;
        }

        //
        // TYPE: 2 - ServerMenu
        //

        public void createServerMenu() {

            this.serverMenu = new ServerMenu(display, this);
            display.getActivePanel().drawObjectOnPanel(serverMenu);
        }

        public void removeServerMenu() {

            serverMenu.remove();
            display.getActivePanel().removeObjectFromPanel(serverMenu);
            serverMenu = null;
        }

        //
        // TYPE: 3 - NameMenu
        //

        public void createNameMenu(int type) {

            this.nameMenu = new NameMenu(display, this, type);
            display.getActivePanel().drawObjectOnPanel(nameMenu);
        }

        public void removeNameMenu() {

            nameMenu.remove();
            display.getActivePanel().removeObjectFromPanel(nameMenu);
            nameMenu = null;
        }

        //
        // TYPE: 4 - TournamentMenu
        //

        public void createTournamentMenu() {


        }

        public void removeTournamentMenu() {


        }
    }
