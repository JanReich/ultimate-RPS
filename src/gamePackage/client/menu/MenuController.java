package gamePackage.client.menu;

import gamePackage.client.singleplayer.Singleplayer;
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
            private Singleplayer singleplayer;


            private String username;

        public MenuController(Display display) {

            this.display = display;

            createMainMenu();
        }

        @Override
        public void update(double delta) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

            if(KeyEvent.VK_SPACE == e.getKeyCode())
                System.out.println("___________________________________________");
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

            if(mainMenu != null) {

                mainMenu.remove();
                display.getActivePanel().removeObjectFromPanel(mainMenu);
                mainMenu = null;
            }
        }

        //
        // TYPE: 1 - MultiplayerMenu
        //

        public void createMultiplayerMenu(String gameType) {

            this.multiplayerMenu = new MultiPlayerMenu(display, this, gameType, username);
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

        public void createServerMenu(String gameType) {

            this.serverMenu = new ServerMenu(display, this, gameType, username);
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

        public void createNameMenu(String gameType) {

            this.nameMenu = new NameMenu(display, this, gameType);
            display.getActivePanel().drawObjectOnPanel(nameMenu);
        }

        public void removeNameMenu() {

            if(nameMenu != null) {

                username = nameMenu.getNameInput().getInputQuerry();
                nameMenu.remove();
                display.getActivePanel().removeObjectFromPanel(nameMenu);
                nameMenu = null;
            }
        }

        public void createSingleplayer(){
            singleplayer = new Singleplayer(display, this);
            display.getActivePanel().drawObjectOnPanel(singleplayer);
        }
        public void removeSingleplayer(){
            display.getActivePanel().removeObjectFromPanel(singleplayer);
            singleplayer = null;
        }
    }
