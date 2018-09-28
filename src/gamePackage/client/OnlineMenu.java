package gamePackage.client;

import graphics.Display;
import graphics.interfaces.BasicInteractableObject;
import toolBox.Animation;
import toolBox.DrawHelper;
import toolBox.ImageHelper;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

    public class OnlineMenu implements BasicInteractableObject {

                //Attribute
            private boolean playAnimation;

                //Referenzen
            private Display display;
            private GameClient gameClient;
            private BufferedImage background;

                //Buttons
            private BufferedImage stone;
            private BufferedImage paper;
            private BufferedImage sissors;
            private BufferedImage selectedstone;
            private BufferedImage selectedPaper;
            private BufferedImage selectedSissors;

            private Animation left;
            private Animation right;

        public OnlineMenu(Display display, GameClient gameClient) {

            this.display = display;
            this.gameClient = gameClient;
            this.background = ImageHelper.getImage("res/images/Singleplayer/background.png");

            this.stone = ImageHelper.getImage("res/images/Singleplayer/stone.png");
            this.paper = ImageHelper.getImage("res/images/Singleplayer/paper.png");
            this.sissors = ImageHelper.getImage("res/images/Singleplayer/scissor.png");
            this.selectedPaper = ImageHelper.getImage("res/images/Singleplayer/scissor-chosen.png");
            this.selectedstone = ImageHelper.getImage("res/images/Singleplayer/stone-chosen.png");
            this.selectedSissors = ImageHelper.getImage("res/images/Singleplayer/scissor-chosen.png");

            left = new Animation("res/images/Singleplayer/stone-ani-left.png", 0.04, 22, 0, false);
            right = new Animation("res/images/Singleplayer/stone-ani-right.png", 0.04, 22, 0, false);
        }

        @Override
        public void draw(DrawHelper draw) {

            draw.drawImage(background, 0, 0, display.getWidth(), display.getHeight());

            if(!gameClient.getData().isSpectator()) {

                draw.drawImage(stone, 430, 300, 128, 128);
                draw.drawImage(sissors, 300, 300, 128, 128);
                draw.drawImage(paper, 560, 300, 128, 128);
            }

            if(!playAnimation) {

                draw.drawImage(left.getFirstSprite(), 0,400,350,350);
                draw.drawImage(right.getFirstSprite(), 615, 400, 350, 350);
            }
        }

        @Override
        public void update(double delta) {

        }

        @Override
        public void keyPressed(KeyEvent event) {

        }

        @Override
        public void keyReleased(KeyEvent event) {

        }

        @Override
        public void mouseReleased(MouseEvent event) {

        }
    }
