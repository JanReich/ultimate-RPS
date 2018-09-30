package gamePackage.client.menu;

import graphics.Display;
import graphics.interfaces.GraphicalObject;
import toolBox.DrawHelper;
import toolBox.ImageHelper;

import java.awt.image.BufferedImage;

    public class CannotConnect implements GraphicalObject {

                //Attribute

                //Referenzen
            private Display display;
            private BufferedImage image;

        public CannotConnect(Display display) {

            this.display = display;
            this.image = ImageHelper.getImage("res/images/menu/cant-connect.png");
        }

        @Override
        public void update(double delta) {

        }

        @Override
        public void draw(DrawHelper draw) {

            draw.drawImage(image, 0 ,0, display.getWidth(), display.getHeight());
        }
    }
