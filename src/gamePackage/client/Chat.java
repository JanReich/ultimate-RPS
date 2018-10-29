package gamePackage.client;

import graphics.interfaces.GraphicalObject;
import toolBox.DrawHelper;

import java.awt.*;
import java.util.ArrayList;

    public class Chat implements GraphicalObject {

                //Attribute

                //Referenzen
            private ArrayList<Message> messages;

        public Chat() {

            messages = new ArrayList<>();

            processNewMessage("Jan", "Dave stinkt");
        }

        public void processNewMessage(String username, String message) {

            messages.add(new Message(username, message));
        }

        @Override
        public void update(double delta) {

        }

        @Override
        public void draw(DrawHelper draw) {

            for(Message message : messages) {

                draw.setFont(new Font("", Font.PLAIN, 16));
                if(draw.getFontWidth("<" + message.getUsername() + ">:" + message.getMessage()) < 600) {

                    draw.drawString("<" + message.getUsername() + ">: " + message.getMessage(), 60,800);
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
    }
