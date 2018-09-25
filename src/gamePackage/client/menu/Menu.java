package gamePackage.client.menu;

import graphics.Display;
import graphics.interfaces.BasicInteractableObject;
import toolBox.DrawHelper;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public abstract class Menu implements BasicInteractableObject {

                //Attribute


                //Referenzen
            protected Display display;
            protected BufferedImage background;

        public Menu(Display display) {

            this.display = display;

            init();
        }

        /**
         * Die init-Methode wird einmal aufgerufen und in dieser werden die verschiedenen
         * Objekte initalisiert und es können beispielsweise auch Bilder geladen werden.
         */
        public abstract void init();

        /**
         * Diese Methode liefert einen Boolean zurück, ob der Cursor in der übergebenen Box ist.
         * @return boolean (if Mouse-Cursor is inside the box)
         */
        public boolean isInside(MouseEvent e, double x, double y, double scale) {

            return this.isInside(e, x, y, scale, scale);
        }

        /**
         * Diese Methode liefert einen Boolean zurück, ob der Cursor in der übergebenen Box ist.
         * @return boolean (if Mouse-Cursor is inside the box)
         */
        public boolean isInside(MouseEvent e, double x, double y, double width, double height) {

            if(e.getX() >= (int) x && e.getX() <= (int) (x + width) && e.getY() >= (int) y && e.getY() <= (int) (y + height)) return true; return false;
        }

        @Override
        public void keyPressed(KeyEvent event) {

        }

        @Override
        public void keyReleased(KeyEvent event) {

        }

        @Override
        public void update(double delta) {

        }

        @Override
        public abstract void draw(DrawHelper draw);

        @Override
        public abstract void mouseReleased(MouseEvent event);
    }
