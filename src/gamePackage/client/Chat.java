package gamePackage.client;

import graphics.Display;
import graphics.interfaces.BasicInteractableObject;
import toolBox.DrawHelper;
import toolBox.Inputmanager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

    public class Chat implements BasicInteractableObject {

                //Attribute

                //Referenzen
            private Inputmanager input;
            private ArrayList<Message> messages;
            private GameClient gameClient;

        public Chat(Display display, GameClient gameClient) {

            input = new Inputmanager();
            this.gameClient = gameClient;
            display.getActivePanel().addManagement(input);
            messages = new ArrayList<>();
        }

        public void processNewMessage(String username, String message) {

            messages.add(new Message(username, message));
        }

        @Override
        public void update(double delta) {

        }

        @Override
        public void draw(DrawHelper draw) {

            draw.setColour(new Color(0, 0, 0, 160));
            draw.fillRec(50, 850, 550, 35);

            draw.setColour(Color.WHITE);
            draw.drawString(input.getInputQuerry(), 75, 880);


            draw.setColour(new Color(0, 0, 0, 160));
            draw.fillRec(50, 725, 550, 120);
            draw.setColour(Color.BLACK);


            if(messages.size() >= 3) {

                draw.drawString("<" + messages.get(messages.size() - 3).getUsername() + ">: " + messages.get(messages.size() - 3).getMessage(), 75,760);
                draw.drawString("<" + messages.get(messages.size() - 2).getUsername() + ">: " + messages.get(messages.size() - 2).getMessage(), 75,800);
                draw.drawString("<" + messages.get(messages.size() - 1).getUsername() + ">: " + messages.get(messages.size() - 1).getMessage(), 75,840);
            } else {

                if(messages.size() == 2) {

                    draw.drawString("<" + messages.get(0).getUsername() + ">: " + messages.get(0).getMessage(), 75,760);
                    draw.drawString("<" + messages.get(1).getUsername() + ">: " + messages.get(1).getMessage(), 75,800);
                } else if(messages.size() == 1) {

                    draw.drawString("<" + messages.get(0).getUsername() + ">: " + messages.get(0).getMessage(), 75,760);
                }
            }
        }


        private class Message {

                    //Attribute

                    //Referenzen
                private String message;
                private String username;

            public Message(String username, String message) {

                this.message = message;
                this.username = username;
            }

            public String getMessage() {

                return message;
            }

            public String getUsername() {

                return username;
            }
        }

        @Override
        public void keyPressed(KeyEvent event) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

            if(e.getKeyCode() == KeyEvent.VK_ENTER) {

                gameClient.sendChatMessage(input.getInputQuerry());
                input.setInputQuerry("");
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

            if(e.getX() > 50 && e.getX() < 600 && e.getY() > 850 && e.getX() < 885) {

                input.setTyping(true);
            } else {

                input.setTyping(false);
            }
        }
    }
