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

                //Referenzen
            private BufferedImage image;
            private Inputmanager nameInput;

        public NameMenu(Display display) {

            super(display);
        }

        @Override
        public void init() {

            nameInput = new Inputmanager(11);
            display.getActivePanel().addManagement(nameInput);
            image = ImageHelper.getImage("res/images/menu/name-menu.png");
        }

        @Override
        public void draw(DrawHelper draw) {

            draw.drawImage(image, 0, 0, display.getWidth(), display.getHeight());
            draw.setFont(new Font("consola", Font.BOLD, 25));
            draw.drawString(nameInput.getInputQuerry(), 375, 501);
        }

        @Override
        public void mouseReleased(MouseEvent event) {

            if(isInside(event, 350, 465, 250, 40))
                nameInput.setTyping(true);
            else
                nameInput.setTyping(false);
        }
    }
