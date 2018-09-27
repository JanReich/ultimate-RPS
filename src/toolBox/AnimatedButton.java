package toolBox;

import graphics.interfaces.AdvancedInteractableObject;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

    public class AnimatedButton implements AdvancedInteractableObject {

                //Attribute
            private double x;
            private double y;
            private double width;
            private double height;

            private boolean inside;
            private boolean clicked;

                //Referenzen
            private Animation animation;
            private BufferedImage image;

        public AnimatedButton(double x, double y, double width, double height, String path) {

            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;

            this.image = ImageHelper.getImage(path);
            this.animation = new Animation(path, 0.4, 8, 0, true);
        }

        public void paint(DrawHelper draw) {

            if(!inside) draw.drawImage(image, x, y, width, height);
            else draw.drawImage(animation.getAnimation(), x, y, width, height);
        }

        @Override
        public void mouseMoved(MouseEvent e) {

            if(e.getX() >= (int) x && e.getX() <= (int) (x + width) && e.getY() >= (int) y && e.getY() <= (int) (y + height)) {

                inside = true;
                animation.start();
            } else {

                inside = false;
                animation.stop();
            }
        }

        @Override
        public void mouseDragged(MouseEvent event) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

            if(e.getX() >= (int) x && e.getX() <= (int) (x + width) && e.getY() >= (int) y && e.getY() <= (int) (y + height))
                clicked = true;
        }

        @Override
        public void keyPressed(KeyEvent event) {

        }

        @Override
        public void keyReleased(KeyEvent event) {

        }

        @Override
        public void mouseReleased(MouseEvent event) {

            clicked = false;
        }

        @Override
        public void update(double delta) {

        }

        @Override
        public void draw(DrawHelper draw) {

        }

        public boolean isClicked() {

            return clicked;
        }
    }
