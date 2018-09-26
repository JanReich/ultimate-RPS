package gamePackage.client.menu;

import graphics.Display;
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
            private int errorID;

                //Referenzen
            private BufferedImage image;
            private Inputmanager nameInput;

        public NameMenu(Display display, int type) {

            super(display);
            this.type = type;
        }

        @Override
        public void init() {

            nameInput = new Inputmanager(11);
            display.getActivePanel().addManagement(nameInput);
            image = ImageHelper.getImage("res/images/menu/name-menu.png");
        }

        @Override
        public void draw(DrawHelper draw) {

            draw.setColour(Color.BLACK);
            draw.drawImage(image, 0, 0, display.getWidth(), display.getHeight());
            draw.setFont(new Font("consola", Font.BOLD, 25));
            draw.drawString(nameInput.getInputQuerry(), 375, 501);

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

        public void remove() {

            display.getActivePanel().removeObjectFromPanel(nameInput);
        }

        public Inputmanager getNameInput() {

            return nameInput;
        }

        /**
         * In dieser Methode wird eine ID für einen Fehler übergeben, damit dieser graphisch dargestellt werden kann.
         * 0 = kein Fehler
         * 1 = Name zu kurz
         */
        public void setMistake(int id) {

            errorID = id;
        }

        public int getType() {

            return type;
        }
    }
