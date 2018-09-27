package gamePackage.client.menu;

import config.ServerListConfig;
import graphics.Display;
import toolBox.Button;
import toolBox.DrawHelper;
import toolBox.ImageHelper;
import toolBox.Inputmanager;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

    public class ServerMenu extends Menu {

            //Attribute
        private boolean addressPopup;

            //Referenzen
        private BufferedImage popup;

            //Serverslots
        private ServerSlot slot1;
        private ServerSlot slot2;
        private ServerSlot slot3;
        private ServerSlot slot4;
        private ServerListConfig config;

        private Inputmanager ipInput;
        private Inputmanager portInput;
        private Inputmanager nameInput;

            //Button's
        private Button back;
        private Button closeButton;
        private Button popupAddButton;

        private Button add;
        private Button direct;

        public ServerMenu(Display display, MenuController controller) {

            super(display, controller);
        }

        @Override
        public void init() {

            popup = ImageHelper.getImage("res/images/menu/add-popup.png");
            background = ImageHelper.getImage("res/images/menu/server-menu.png");

            this.back = new Button(30, 860, 155, 50, "res/images/menu/buttons/Back-button", true);
            display.getActivePanel().drawObjectOnPanel(back);
            this.add = new Button(228, 796, 166, 60, "res/images/menu/buttons/Add-server-button",true);
            display.getActivePanel().drawObjectOnPanel(add);
            this.direct = new Button(580, 796, 166, 60, "res/images/menu/buttons/Add-server-button",true);
            display.getActivePanel().drawObjectOnPanel(direct);

            config = new ServerListConfig();

            if(config.isSlot1())
                slot1 = createServerSlot(1);
            if(config.isSlot2())
                slot2 = createServerSlot(2);
            if(config.isSlot3())
                slot3 = createServerSlot(3);
            if(config.isSlot4())
                slot4 = createServerSlot(4);
        }

        @Override
        public void remove() {

            removePopup();
            display.getActivePanel().removeObjectFromPanel(add);
            add = null;
            display.getActivePanel().removeObjectFromPanel(back);
            back = null;
            display.getActivePanel().removeObjectFromPanel(direct);
            direct = null;
            display.getActivePanel().removeObjectFromPanel(closeButton);
            closeButton = null;
            display.getActivePanel().removeObjectFromPanel(popupAddButton);
            popupAddButton = null;
        }

        @Override
        public void draw(DrawHelper draw) {

                //draw background
            draw.drawImage(background, 0, 0, display.getWidth(), display.getHeight());

                //draw server slots
            if(config.isSlot1()) {

                draw.drawImage(slot1.getImage(), slot1.getX(), slot1.getY(), slot1.getWidth(), slot1.getHeight());
                draw.drawButton(slot1.getDelete());
                draw.drawButton(slot1.getJoin());
            }

            if(config.isSlot2()) {

                draw.drawImage(slot2.getImage(), slot2.getX(), slot2.getY(), slot2.getWidth(), slot2.getHeight());
                draw.drawButton(slot2.getDelete());
                draw.drawButton(slot2.getJoin());
            }

            if(config.isSlot3()) {

                draw.drawImage(slot3.getImage(), slot3.getX(), slot3.getY(), slot3.getWidth(), slot3.getHeight());
                draw.drawButton(slot3.getDelete());
                draw.drawButton(slot3.getJoin());
            }

            if(config.isSlot4()) {

                draw.drawImage(slot4.getImage(), slot4.getX(), slot4.getY(), slot4.getWidth(), slot4.getHeight());
                draw.drawButton(slot4.getDelete());
                draw.drawButton(slot4.getJoin());
            }

                //button's
            draw.drawButton(back);
            draw.drawButton(add);
            draw.drawButton(direct);

            if(addressPopup) {

                    //draw popup
                draw.drawImage(popup, 115, 250, 730, 450);

                    //draw Button's
                draw.drawButton(closeButton);
                draw.drawButton(popupAddButton);

                    //draw Strings
                draw.drawString(nameInput.getInputQuerry(), 385, 352);
                draw.drawString(ipInput.getInputQuerry(), 375,472);
                draw.drawString(portInput.getInputQuerry(), 375, 592);
            }
        }

        @Override
        public void mouseReleased(MouseEvent event) {

            if(addressPopup) {

                if (isInside(event, 375, 330, 230, 25)) {

                    ipInput.setTyping(false);
                    portInput.setTyping(false);

                    nameInput.setTyping(true);
                } else if (isInside(event, 375, 450, 230, 25)) {

                    ipInput.setTyping(true);

                    portInput.setTyping(false);
                    nameInput.setTyping(false);
                } else if (isInside(event, 375, 570, 230, 25)) {

                    ipInput.setTyping(false);
                    nameInput.setTyping(false);

                    portInput.setTyping(true);
                } else {

                    ipInput.setTyping(false);
                    portInput.setTyping(false);
                    nameInput.setTyping(false);
                }
            }
        }

        @Override
        public void update(double dt) {

            if(back.isClicked()) {

                controller.removeServerMenu();
                controller.createMultiplayerMenu();
            }

            else if(add.isClicked()) {
                if(!addressPopup) createPopup();
            }

            if(addressPopup) {
                if (closeButton.isClicked()) removePopup();
            }


            if(config.isSlot1())
                if(slot1.getDelete().isClicked()) {

                    removeServerSlot(slot1, 1);
                    slot1 = null;
                }

            if(config.isSlot2())
                if(slot2.getDelete().isClicked()) {

                    removeServerSlot(slot2, 2);
                    slot2 = null;
                }

            if(config.isSlot3())
                if(slot3.getDelete().isClicked()) {

                    removeServerSlot(slot3, 3);
                    slot3 = null;
                }

            if(config.isSlot4())
                if(slot4.getDelete().isClicked()) {

                    removeServerSlot(slot4, 4);
                    slot4 = null;
                }
        }

        private void createPopup() {

            if(!addressPopup) {

                addressPopup = true;
                this.closeButton = new Button(763, 323, 45, 45, "res/images/menu/buttons/close-button", true);
                display.getActivePanel().drawObjectOnPanel(closeButton);
                this.popupAddButton = new Button(680, 560, 125, 50, "res/images/menu/buttons/Add-server-button", true);
                display.getActivePanel().drawObjectOnPanel(popupAddButton);

                ipInput = new Inputmanager(12);
                display.getActivePanel().addManagement(ipInput);
                portInput = new Inputmanager(5);
                display.getActivePanel().addManagement(portInput);
                nameInput = new Inputmanager();
                display.getActivePanel().addManagement(nameInput);
            }
        }

        private void removePopup() {

            if(addressPopup) {

                addressPopup = false;
                display.getActivePanel().removeObjectFromPanel(closeButton);
                closeButton = null;
                display.getActivePanel().removeObjectFromPanel(popupAddButton);
                popupAddButton = null;

                display.getActivePanel().removeObjectFromPanel(ipInput);
                ipInput = null;
                display.getActivePanel().removeObjectFromPanel(portInput);
                portInput = null;
                display.getActivePanel().removeObjectFromPanel(nameInput);
                nameInput = null;
            }
        }

        private ServerSlot createServerSlot(int index) {

            ServerSlot temp = null;

            switch (index) {

                case 1:
                    temp = new ServerSlot(125, 235, 705, 110);
                    break;
                case 2:
                    temp = new ServerSlot(125, 355, 705, 110);
                    break;
                case 3:
                    temp = new ServerSlot(125, 475, 705, 110);
                    break;
                case 4:
                    temp = new ServerSlot(125, 595, 705, 110);
                    break;
            }

            if(temp != null) {

                display.getActivePanel().drawObjectOnPanel(temp.getJoin());
                display.getActivePanel().drawObjectOnPanel(temp.getDelete());
            }
            return temp;
        }

        private void removeServerSlot(ServerSlot slot, int type) {

            display.getActivePanel().removeObjectFromPanel(slot.getJoin());
            display.getActivePanel().removeObjectFromPanel(slot.getDelete());

            switch (type) {

                case 1:
                    config.setSlot1(false);
                    config.setName1("Empty");
                    config.setServerIP1("Empty");
                    config.setServerPort1(-1);
                    break;
                case 2:
                    config.setSlot2(false);
                    config.setName2("Empty");
                    config.setServerIP2("Empty");
                    config.setServerPort2(-1);
                    break;
                case 3:
                    config.setSlot3(false);
                    config.setName3("Empty");
                    config.setServerIP3("Empty");
                    config.setServerPort3(-1);
                    break;
                case 4:
                    config.setSlot4(false);
                    config.setName4("Empty");
                    config.setServerIP4("Empty");
                    config.setServerPort4(-1);
                    break;
            }
            config.save();
        }

        private class ServerSlot {

                    //Attribute
                private int x;
                private int y;
                private int width;
                private int height;

                    //Referenzen
                private Button join;
                private Button delete;
                private BufferedImage image;

            public ServerSlot(int x, int y, int width, int height) {

                this.x = x;
                this.y = y;
                this.width = width;
                this.height = height;

                this.image = ImageHelper.getImage("res/images/menu/server-slot.png");

                join = new Button(x + 613, y + 72, 78, 30, "res/images/menu/buttons/ok-button", true);
                delete = new Button(x + 674, y + 6, 25, 25, "res/images/menu/buttons/close-button",true);
            }

            // GETTER

            public int getX() {

                return x;
            }

            public int getY() {

                return y;
            }

            public int getWidth() {

                return width;
            }

            public int getHeight() {

                return height;
            }

            public Button getJoin() {

                return join;
            }

            public Button getDelete() {

                return delete;
            }

            public BufferedImage getImage() {

                return image;
            }
        }
    }
