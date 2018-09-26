package gamePackage.client.menu;

import graphics.Display;
import toolBox.Button;
import toolBox.DrawHelper;
import toolBox.ImageHelper;
import toolBox.Inputmanager;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

    public class NameMenu extends Menu {

                //Attribute
            /**
             * Type beschreibt, welches Menü als nächstes kommt
             * 0 = duell
             * 1 = tournament
             */
            private int type;
            /**
             * In dieser Methode wird eine ID für einen Fehler übergeben, damit dieser graphisch dargestellt werden kann.
             * 0 = kein Fehler
             * 1 = Name zu kurz
             */
            private int errorID;

                //Referenzen
            private Button ok;
            private BufferedImage image;
            private Inputmanager nameInput;

        public NameMenu(Display display, MenuController controller, int type) {

            super(display, controller);
            this.type = type;
        }

        @Override
        public void init() {

            nameInput = new Inputmanager(11);
            display.getActivePanel().addManagement(nameInput);
            image = ImageHelper.getImage("res/images/menu/name-menu.png");

            this.ok = new Button(637, 462, 80, 50,"res/images/menu/buttons/ok-button", true);
            display.getActivePanel().drawObjectOnPanel(ok);
        }

        public void remove() {

            display.getActivePanel().removeObjectFromPanel(nameInput);
            display.getActivePanel().removeObjectFromPanel(ok);
            ok = null;
        }

        @Override
        public void draw(DrawHelper draw) {

            draw.setColour(Color.BLACK);
            draw.drawImage(image, 0, 0, display.getWidth(), display.getHeight());
            draw.setFont(new Font("consola", Font.BOLD, 25));
            draw.drawString(nameInput.getInputQuerry(), 375, 501);

                //Button's
            draw.drawButton(ok);

                //Name too short
            if(errorID == 1) {

                draw.setColour(Color.RED);
                draw.setFont(new Font("Impact", Font.BOLD, 25));
                draw.drawString("Name too short!", 375, 450);
            }
        }

        @Override
        public void mouseReleased(MouseEvent event) {

            if(isInside(event, 350, 465, 250, 40))
                nameInput.setTyping(true);
            else
                nameInput.setTyping(false);
        }

        @Override
        public void update(double dt) {

            if(ok.isClicked()) {

                if(getNameInput().getInputQuerry().length() >= 3) {

                    if(type == 0) {

                        controller.removeNameMenu();
                        controller.createMultiplayerMenu();
                    } else if(type == 1) {

                        controller.removeNameMenu();
                        controller.createTournamentMenu();
                    }
                } else
                    errorID = 1;
            }
        }

        public Inputmanager getNameInput() {

            return nameInput;
        }

        public int getType() {

            return type;
        }
    }
