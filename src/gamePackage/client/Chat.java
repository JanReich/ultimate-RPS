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
            draw.setColour(new Color(200, 200, 200, 255));

            draw.setFont(new Font("Impact", Font.PLAIN, 18));


            if(messages.size() >= 5) {

                draw.drawString("<" + messages.get(messages.size() - 5).getUsername() + ">: " + messages.get(messages.size() - 5).getMessage(), 75,750);
                draw.drawString("<" + messages.get(messages.size() - 4).getUsername() + ">: " + messages.get(messages.size() - 4).getMessage(), 75,770);
                draw.drawString("<" + messages.get(messages.size() - 3).getUsername() + ">: " + messages.get(messages.size() - 3).getMessage(), 75,790);
                draw.drawString("<" + messages.get(messages.size() - 2).getUsername() + ">: " + messages.get(messages.size() - 2).getMessage(), 75,810);
                draw.drawString("<" + messages.get(messages.size() - 1).getUsername() + ">: " + messages.get(messages.size() - 1).getMessage(), 75,830);
            } else {

                for (int i = 0; i < messages.size(); i++) {
                    draw.drawString("<" + messages.get(i).getUsername() + ">: " + messages.get(i).getMessage(), 75,750+20*i);

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
