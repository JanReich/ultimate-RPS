package gamePackage.client;

import graphics.Display;
import graphics.interfaces.BasicInteractableObject;
import toolBox.Animation;
import toolBox.Button;
import toolBox.DrawHelper;
import toolBox.ImageHelper;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

    public class OnlineMenu implements BasicInteractableObject {

                //Attribute
            /**
             * 1 = stein,
             * 2 = papier,
             * 3 = schere
             */
            private int currentChoose;
            private boolean canChoose;
            private boolean playAnimation;

            private int scoreLeft;
            private int scoreRight;
            private String usernameLeft;
            private String usernameRight;
            private int choosePlayerLeft;
            private int choosePlayerRight;

                //Referenzen
            private Display display;
            private GameClient gameClient;
            private BufferedImage background;

                //Buttons
            private Button submit;
            private BufferedImage stone;
            private BufferedImage paper;
            private BufferedImage sissors;
            private BufferedImage selectedstone;
            private BufferedImage selectedPaper;
            private BufferedImage selectedSissors;

            private Animation left;
            private Animation right;

        public OnlineMenu(Display display, GameClient gameClient) {


            this.canChoose = true;
            this.display = display;
            this.gameClient = gameClient;
            init();
            loadUsername();
        }

        private void init() {

            this.background = ImageHelper.getImage("res/images/Singleplayer/background.png");

            this.stone = ImageHelper.getImage("res/images/Singleplayer/stone.png");
            this.paper = ImageHelper.getImage("res/images/Singleplayer/paper.png");
            this.sissors = ImageHelper.getImage("res/images/Singleplayer/scissor.png");
            this.selectedPaper = ImageHelper.getImage("res/images/Singleplayer/paper-chosen.png");
            this.selectedstone = ImageHelper.getImage("res/images/Singleplayer/stone-chosen.png");
            this.selectedSissors = ImageHelper.getImage("res/images/Singleplayer/scissor-chosen.png");

            submit = new Button(420, 450, 150, 80, "res/images/menu/buttons/submit", true);
            left = new Animation("res/images/animations/Stein.png", 0.04, 22, 0, false);
            right = new Animation("res/images/animations/Stein-rechts.png", 0.04, 22, 0, false);
            display.getActivePanel().drawObjectOnPanel(submit);
        }

        private void loadUsername() {

            //Username für die Player setzen
            if(!gameClient.getData().isSpectator()) {

                usernameLeft = gameClient.getData().getUsername();

                if(gameClient.getConnectedPlayers().get(0).getClientID() != gameClient.getData().getClientID()) {

                    usernameRight = gameClient.getConnectedPlayers().get(0).getUsername();
                } else {

                    usernameRight = gameClient.getConnectedPlayers().get(1).getUsername();
                }
            }
            //Username für die Spectator festlegen
            else {

                if(gameClient.getConnectedPlayers().get(0) != null && gameClient.getConnectedPlayers().get(1) != null) {

                    usernameLeft = gameClient.getConnectedPlayers().get(0).getUsername();
                    usernameRight = gameClient.getConnectedPlayers().get(1).getUsername();
                }
            }
        }

        @Override
        public void draw(DrawHelper draw) {

            draw.drawImage(background, 0, 0, display.getWidth(), display.getHeight());

            if(!gameClient.getData().isSpectator()) {

                if(currentChoose == 1) draw.drawImage(selectedstone, 430, 300, 128, 128);
                else draw.drawImage(stone, 430, 300, 128, 128);

                if(currentChoose == 2) draw.drawImage(selectedPaper, 560, 300, 128, 128);
                else draw.drawImage(paper, 560, 300, 128, 128);

                if(currentChoose == 3) draw.drawImage(selectedSissors, 300, 300, 128, 128);
                else draw.drawImage(sissors, 300, 300, 128, 128);

                if(canChoose) draw.drawButton(submit);
            }

            if(right.isFinished() || left.isFinished()) {

                draw.drawImage(left.getAnimation(), 0,400,350,350);
                draw.drawImage(right.getAnimation(), 615, 400, 350, 350);
            } else if(playAnimation) {

                draw.drawImage(left.getAnimation(), 0,400,350,350);
                draw.drawImage(right.getAnimation(), 615, 400, 350, 350);
            } else if(!playAnimation) {

                draw.drawImage(left.getFirstSprite(), 0,400,350,350);
                draw.drawImage(right.getFirstSprite(), 615, 400, 350, 350);
            }

            draw.setColour(Color.BLACK);
            draw.setFont(new Font("Impact", Font.BOLD, 30));
            draw.drawString(usernameLeft, 10, 730);
            draw.drawString(usernameRight, 765, 730);

            draw.drawString("Score: ", 450, 10);
            draw.drawString(scoreLeft + " : " + scoreRight, 400, 60);

                //Button's ausgrauen
            if(!canChoose) {
                draw.setColour(new Color(0, 0, 0, 100));
                draw.fillRec(300, 300, 388, 128);
            }

        }

        @Override
        public void update(double delta) {

                //1 = stein
                //2 = papier
                //3 = schere
            if((choosePlayerLeft == 1 || choosePlayerLeft == 2 || choosePlayerLeft == 3) && (choosePlayerRight == 1 || choosePlayerRight == 2 || choosePlayerRight == 3) && !playAnimation) {

                createAnimation();
                playAnimation = true;
            }

            if(left.isFinished() && right.isFinished() && playAnimation) {

                chooseWinner();
            }

            if(submit.isClicked()) {

                if(currentChoose == 1 || currentChoose == 2 || currentChoose == 3) {

                    canChoose = false;
                    gameClient.choose(gameClient.getData().getClientID(), currentChoose);
                }
            }
        }

        private void createAnimation() {

            switch (choosePlayerLeft) {

                case 1:
                    left = new Animation("res/images/animations/Stein.png", 0.04, 22, 0, false);
                    break;
                case 2:
                    left = new Animation("res/images/animations/Papier.png", 0.04, 22, 0, false);
                    break;
                case 3:
                    left = new Animation("res/images/animations/Schere.png", 0.04, 22, 0, false);
                    break;
            }
            display.getActivePanel().drawObjectOnPanel(left);

            switch (choosePlayerRight) {

                case 1:
                    right = new Animation("res/images/animations/Stein-rechts.png", 0.04, 22, 0, false);
                    break;
                case 2:
                    right = new Animation("res/images/animations/Papier-rechts.png", 0.04, 22, 0, false);
                    break;
                case 3:
                    right = new Animation("res/images/animations/Schere-rechts.png", 0.04, 22, 0, false);
                    break;
            }
            display.getActivePanel().drawObjectOnPanel(right);

            playAnimation = true;
        }

        private void chooseWinner() {

            playAnimation = false;

                //1 = stein
                //2 = papier
                //3 = schere

                //unentschieden
            if(choosePlayerLeft == choosePlayerRight) {


            }

                //playerLeft Scores
            else if((choosePlayerLeft == 1 && choosePlayerRight == 3) || (choosePlayerLeft == 2 && choosePlayerRight == 1) || (choosePlayerLeft == 3 && choosePlayerRight == 2)) {

                scoreLeft ++;
            }

                //playerRight Scores
            else if((choosePlayerRight == 1 && choosePlayerLeft == 3) || (choosePlayerRight == 2 && choosePlayerLeft == 1) || (choosePlayerRight == 3 && choosePlayerLeft == 2)) {

                scoreRight ++;
            }

            currentChoose = 0;
            choosePlayerLeft = 0;
            choosePlayerRight = 0;
            canChoose = true;
        }

        @Override
        public void keyPressed(KeyEvent event) {

        }

        @Override
        public void keyReleased(KeyEvent event) {

        }

        @Override
        public void mouseReleased(MouseEvent event) {

            if(this.canChoose) {

                if (isInside(event, 430, 300, 128, 128)) {

                    currentChoose = 1;
                } else if (isInside(event, 300, 300, 128, 128)) {

                    currentChoose = 3;
                } else if (isInside(event, 560, 300, 128, 128)) {

                    currentChoose = 2;
                }
            }
        }

        public void setChoose(int clientID, int choose) {

                //Damit man sich selbst immer links sieht
            if(!gameClient.getData().isSpectator()) {

                if(gameClient.getData().getClientID() == clientID) {

                    choosePlayerLeft = choose;
                } else {

                    choosePlayerRight = choose;
                }
            }

                //Damit einen die Spectator sehen können
            else if(gameClient.getConnectedPlayers().get(0) != null && gameClient.getConnectedPlayers().get(1) != null) {

                if(clientID == gameClient.getConnectedPlayers().get(0).getClientID()) {

                    choosePlayerLeft = choose;
                } else if(clientID == gameClient.getConnectedPlayers().get(1).getClientID()) {

                    choosePlayerRight = choose;
                }
            }
        }

        private boolean isInside(MouseEvent e, int x, int y, int width, int height) {

            if(e.getX() > x && e.getX() < x + width && e.getY() > y && e.getY() < y + height) return true; return false;
        }
    }
