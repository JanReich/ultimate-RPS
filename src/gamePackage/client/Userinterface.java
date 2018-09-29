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

    public class Userinterface implements BasicInteractableObject {

                //Attribute
            /**
             * 1 = stein,
             * 2 = papier,
             * 3 = schere
             */
            private int currentChoose;
            private boolean canChoose;
            private boolean playAnimation;

            /**
             * 1 = Lose
             * 2 = Win
             */
            private int winstateLeft;
            private int winstateRight;

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
            private BufferedImage win;
            private BufferedImage lose;
            private BufferedImage stone;
            private BufferedImage paper;
            private BufferedImage scissors;
            private BufferedImage selectedStone;
            private BufferedImage selectedPaper;
            private BufferedImage selectedScissors;

            private Animation left;
            private Animation right;

        public Userinterface(Display display, GameClient gameClient) {


            this.canChoose = true;
            this.display = display;
            this.gameClient = gameClient;
            init();
            loadUsername();
        }

        public void remove() {

            display.getActivePanel().removeObjectFromPanel(left);
            left = null;
            display.getActivePanel().removeObjectFromPanel(right);
            right = null;
            if(display.getActivePanel().contains(submit)) display.getActivePanel().removeObjectFromPanel(submit);
        }

        private void init() {

            this.background = ImageHelper.getImage("res/images/Singleplayer/background.png");

            this.win = ImageHelper.getImage("res/images/Singleplayer/win.png");
            this.lose = ImageHelper.getImage("res/images/Singleplayer/lost.png");
            this.stone = ImageHelper.getImage("res/images/Singleplayer/stone.png");
            this.paper = ImageHelper.getImage("res/images/Singleplayer/paper.png");
            this.scissors = ImageHelper.getImage("res/images/Singleplayer/scissor.png");
            this.selectedPaper = ImageHelper.getImage("res/images/Singleplayer/paper-chosen.png");
            this.selectedStone = ImageHelper.getImage("res/images/Singleplayer/stone-chosen.png");
            this.selectedScissors = ImageHelper.getImage("res/images/Singleplayer/scissor-chosen.png");

            submit = new Button(390, 450, 200, 80, "res/images/menu/buttons/submit", true);
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

                if(currentChoose == 1) draw.drawImage(selectedStone, 430, 300, 128, 128);
                else draw.drawImage(stone, 430, 300, 128, 128);

                if(currentChoose == 2) draw.drawImage(selectedPaper, 560, 300, 128, 128);
                else draw.drawImage(paper, 560, 300, 128, 128);

                if(currentChoose == 3) draw.drawImage(selectedScissors, 300, 300, 128, 128);
                else draw.drawImage(scissors, 300, 300, 128, 128);

                if(canChoose && (winstateRight != 1 || winstateRight != 2))  draw.drawButton(submit);
            }

            if(winstateLeft == 1) {

                draw.drawImage(lose, 0, 0, 350);
            } else if(winstateLeft == 2) {

                draw.drawImage(win, 0, 0, 350);
            }

            if(winstateRight == 1) {

                draw.drawImage(lose, 600, 0, 350);
            } else if(winstateRight == 2) {

                draw.drawImage(win, 600, 0, 350);
            }

            if(right != null && left != null)
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

            draw.drawString("Score: ", 425, 40);
            draw.drawString(scoreLeft + " : " + scoreRight, 440, 80);

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

            if(left != null && right != null)
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

            if(scoreLeft == 3) {

                winstateLeft = 2;
                winstateRight = 1;
            } else if(scoreRight == 3) {

                winstateLeft = 1;
                winstateRight = 2;
            } else {

                canChoose = true;
            }

            currentChoose = 0;
            choosePlayerLeft = 0;
            choosePlayerRight = 0;
        }

        @Override
        public void keyPressed(KeyEvent event) {

        }

        @Override
        public void keyReleased(KeyEvent event) {

        }

        @Override
        public void mouseReleased(MouseEvent event) {

            if(this.canChoose && winstateRight != 1 || winstateRight != 2) {

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
