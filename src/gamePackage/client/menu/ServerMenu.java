package gamePackage.client.menu;

import config.ServerListConfig;
import gamePackage.client.OnlineManager;
import graphics.Display;
import toolBox.*;
import toolBox.Button;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.InetSocketAddress;
import java.net.Socket;

    public class ServerMenu extends Menu {

                //Attribute
            private int errorID;
            private boolean directPopup;
            private boolean addressPopup;

            private boolean con1;
            private boolean con2;
            private boolean con3;
            private boolean con4;

                //Referenzen
            private String username;
            private BufferedImage popup;
            private BufferedImage direcPopup;
            private OnlineManager onlineManager;

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
            private Button join;
            private Button closeButton;
            private Button popupAddButton;
            private AnimatedButton refresh;

            private Button add;
            private Button direct;

            public ServerMenu(Display display, MenuController controller, String username) {

                super(display, controller);

                this.username = username;
            }

            @Override
            public void init() {

                popup = ImageHelper.getImage("res/images/menu/add-popup.png");
                background = ImageHelper.getImage("res/images/menu/server-menu.png");
                direcPopup = ImageHelper.getImage("res/images/menu/direct-popup.png");

                this.back = new Button(30, 860, 155, 50, "res/images/menu/buttons/Back-button", true);
                display.getActivePanel().drawObjectOnPanel(back);
                this.add = new Button(228, 796, 166, 60, "res/images/menu/buttons/Add-server-button",true);
                display.getActivePanel().drawObjectOnPanel(add);
                this.direct = new Button(580, 796, 166, 60, "res/images/menu/buttons/direct-button",true);
                display.getActivePanel().drawObjectOnPanel(direct);
                this.refresh = new AnimatedButton(458, 796, 60, 60, "res/images/menu/buttons/refresh-button");
                display.getActivePanel().drawObjectOnPanel(refresh);

                config = new ServerListConfig();
                loadSlots();
            }

            private void loadSlots() {

                if(config.isSlot1())
                    slot1 = createServerSlot(1);
                if(config.isSlot2())
                    slot2 = createServerSlot(2);
                if(config.isSlot3())
                    slot3 = createServerSlot(3);
                if(config.isSlot4())
                    slot4 = createServerSlot(4);

                if(add != null && config.isSlot1() && config.isSlot2() && config.isSlot3() && config.isSlot4()) {

                    display.getActivePanel().removeObjectFromPanel(add);
                    add = null;
                }
                else if(add == null && (!config.isSlot1() || !config.isSlot2() || !config.isSlot3() || !config.isSlot4())) {

                    this.add = new Button(228, 796, 166, 60, "res/images/menu/buttons/Add-server-button",true);
                    display.getActivePanel().drawObjectOnPanel(add);
                }
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
                display.getActivePanel().removeObjectFromPanel(refresh);
                refresh = null;
            }

            @Override
            public void draw(DrawHelper draw) {

                    //draw background
                draw.setColour(Color.BLACK);
                draw.drawImage(background, 0, 0, display.getWidth(), display.getHeight());

                    //draw server slots
                if(config.isSlot1()) {

                    drawSlot(draw, slot1, con1);

                    drawSlotinformation(draw, 1, slot1.getX(), slot1.getY());
                }

                if(config.isSlot2()) {

                    drawSlot(draw, slot2, con2);

                    drawSlotinformation(draw, 2, slot2.getX(), slot2.getY());
                }

                if(config.isSlot3()) {

                    drawSlot(draw, slot3, con3);

                    drawSlotinformation(draw, 3, slot3.getX(), slot3.getY());
                }

                if(config.isSlot4()) {

                    drawSlot(draw, slot4, con4);

                    drawSlotinformation(draw, 4, slot4.getX(), slot4.getY());
                }

                    //button's
                draw.drawButton(back);
                draw.drawButton(add);
                draw.drawButton(direct);
                draw.drawButton(refresh);

                if(directPopup) {

                        //draw  popup
                    draw.drawImage(direcPopup, 115, 330, 730, 300);

                        //Draw Button's
                    draw.drawButton(join);
                    draw.drawButton(closeButton);

                    draw.drawString(ipInput.getInputQuerry(), 380, 415);
                    draw.drawString(portInput.getInputQuerry(), 380, 530);
                }

                if(addressPopup) {

                        //draw popup
                    draw.drawImage(popup, 115, 250, 730, 450);

                        //draw Button's
                    draw.drawButton(closeButton);
                    draw.drawButton(popupAddButton);

                        //draw Strings
                    draw.setColour(Color.BLACK);
                    draw.drawString(nameInput.getInputQuerry(), 385, 352);
                    draw.drawString(ipInput.getInputQuerry(), 385,472);
                    draw.drawString(portInput.getInputQuerry(), 385, 592);

                    draw.setColour(Color.RED);
                    draw.setFont(new Font("Impact", Font.BOLD, 30));

                    if (errorID == 1) {

                        draw.drawString("ungültige IP!", 385, 660);
                    } else if (errorID == 2) {

                        draw.drawString("Die Eingabefelder dürfen nicht leer sein!", 187, 660);
                    } else if(errorID == 3) {

                        draw.drawString("Der Port muss numerisch sein!", 265, 660);
                    }
                }
            }

            public void drawSlot(DrawHelper draw, ServerSlot slot, boolean connection) {

                draw.drawImage(slot.getImage(), slot.getX(), slot.getY(), slot.getWidth(), slot.getHeight());
                draw.drawButton(slot.getDelete());
                draw.drawButton(slot.getJoin());

                if(connection) draw.drawImage(slot.getConnectionTrue(), slot.getX() + 640, slot.getY() + 31, 50, 40);
                else draw.drawImage(slot.getConnectionFalse(), slot.getX() + 640, slot.getY() + 31, 50, 40);
            }

            @Override
            public void mouseReleased(MouseEvent event) {

                if(directPopup) {

                    if (isInside(event, 375, 390, 240, 25)) {

                        ipInput.setTyping(true);
                        portInput.setTyping(false);
                    } else if (isInside(event, 375, 505, 240, 25)) {

                        portInput.setTyping(true);
                        ipInput.setTyping(false);
                    } else {

                        ipInput.setTyping(false);
                        portInput.setTyping(false);
                    }
                } else if(addressPopup) {

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

                if(refresh.isClicked() && !addressPopup && !directPopup) {

                    if(config.isSlot1()) con1 = checkServerAlive(config.getServerIP1(), config.getServerPort1());
                    if(config.isSlot2()) con2 = checkServerAlive(config.getServerIP2(), config.getServerPort2());
                    if(config.isSlot3()) con3 = checkServerAlive(config.getServerIP3(), config.getServerPort3());
                    if(config.isSlot4()) con4 = checkServerAlive(config.getServerIP4(), config.getServerPort4());
                }

                if(add != null)
                    if(add.isClicked() && !directPopup)
                        if(!addressPopup) createPopup();

                if(direct != null) {
                    if(direct.isClicked() && !addressPopup) {

                        if(!directPopup) createDirectPopup();
                    }
                }

                if(closeButton != null)
                    if (closeButton.isClicked()) {

                        if(addressPopup) removePopup();
                        else if(directPopup) removeDirecPopUp();
                    }

                if(directPopup)
                    if(join.isClicked()) {

                        if(ipInput.getInputQuerry().contains(".") || ipInput.getInputQuerry().equalsIgnoreCase("localhost")) {

                            if (portInput.getInputQuerry().length() != 0) {

                                try {

                                    connect(ipInput.getInputQuerry(), Integer.parseInt(portInput.getInputQuerry()));
                                    removeDirecPopUp();
                                } catch (NumberFormatException e) {

                                    errorID = 3;
                                }
                            } else errorID = 2;
                        } else errorID = 1;
                    }

                if(config.isSlot1()) {

                    if (slot1.getJoin().isClicked()) {

                        connect(config.getServerIP1(), config.getServerPort1());
                    }

                    if (slot1.getDelete().isClicked()) {

                        removeServerSlot(slot1, 1);
                        slot1 = null;
                    }
                }

                if(config.isSlot2()) {

                    if (slot2.getJoin().isClicked()) {

                        connect(config.getServerIP2(), config.getServerPort2());
                    }

                    if (slot2.getDelete().isClicked()) {

                        removeServerSlot(slot2, 2);
                        slot2 = null;
                    }
                }

                if(config.isSlot3()) {

                    if (slot3.getJoin().isClicked()) {

                        connect(config.getServerIP3(), config.getServerPort3());
                    }

                    if (slot3.getDelete().isClicked()) {

                        removeServerSlot(slot3, 3);
                        slot3 = null;
                    }
                }

                if(config.isSlot4()) {

                    if (slot4.getJoin().isClicked()) {

                        connect(config.getServerIP4(), config.getServerPort4());
                    }

                    if (slot4.getDelete().isClicked()) {

                        removeServerSlot(slot4, 4);
                        slot4 = null;
                    }
                }

                /**
                 * ErrorID:
                 * 1 = ungültige IP
                 * 2 = Felder dürfen nicht leer sein
                 * 3 = Der Port muss numerisch sein
                 */
                if(addressPopup)
                    if(popupAddButton.isClicked()) {

                        if(ipInput.getInputQuerry().contains(".") || ipInput.getInputQuerry().equalsIgnoreCase("localhost")) {

                            if(nameInput.getInputQuerry().length() != 0 && portInput.getInputQuerry().length() != 0) {

                                try {

                                    if(!config.isSlot1()) {
                                        saveServer(1, ipInput.getInputQuerry(), Integer.parseInt(portInput.getInputQuerry()), nameInput.getInputQuerry());
                                    } else if(!config.isSlot2()) {
                                        saveServer(2, ipInput.getInputQuerry(), Integer.parseInt(portInput.getInputQuerry()), nameInput.getInputQuerry());
                                    } else if(!config.isSlot3()) {
                                        saveServer(3, ipInput.getInputQuerry(), Integer.parseInt(portInput.getInputQuerry()), nameInput.getInputQuerry());
                                    } else if(!config.isSlot4()) {
                                        saveServer(4, ipInput.getInputQuerry(), Integer.parseInt(portInput.getInputQuerry()), nameInput.getInputQuerry());
                                    }

                                    removePopup();
                                    loadSlots();
                                } catch (NumberFormatException e) {

                                    errorID = 3;
                                }
                            } else errorID = 2;
                        } else errorID = 1;
                    }
            }

            private void connect(String serverIP, int serverPort) {

                onlineManager = new OnlineManager(display, username, serverIP, serverPort);
                display.getActivePanel().removeObjectFromPanel(this);
            }

            private void createPopup() {

                if(!addressPopup) {

                    addressPopup = true;
                    this.closeButton = new Button(763, 323, 45, 45, "res/images/menu/buttons/close-button", true);
                    display.getActivePanel().drawObjectOnPanel(closeButton);
                    this.popupAddButton = new Button(680, 560, 125, 50, "res/images/menu/buttons/Add-server-button", true);
                    display.getActivePanel().drawObjectOnPanel(popupAddButton);

                    ipInput = new Inputmanager(15);
                    display.getActivePanel().addManagement(ipInput);
                    portInput = new Inputmanager(5);
                    display.getActivePanel().addManagement(portInput);
                    nameInput = new Inputmanager(12);
                    display.getActivePanel().addManagement(nameInput);
                }
            }

            private void removePopup() {

                if(addressPopup) {

                    errorID = -1;
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

            private void createDirectPopup() {

                directPopup = true;
                this.join = new Button(690, 500, 110, 45, "res/images/menu/buttons/Join-server-button", true);
                display.getActivePanel().drawObjectOnPanel(join);
                this.closeButton = new Button(763, 372, 45, 45, "res/images/menu/buttons/close-button", true);
                display.getActivePanel().drawObjectOnPanel(closeButton);

                ipInput = new Inputmanager(15);
                display.getActivePanel().addManagement(ipInput);
                portInput = new Inputmanager(5);
                display.getActivePanel().addManagement(portInput);
            }

            private void removeDirecPopUp() {

                errorID = -1;
                directPopup = false;
                display.getActivePanel().removeObjectFromPanel(closeButton);
                this.closeButton = null;
                display.getActivePanel().removeObjectFromPanel(join);
                this.join = null;
                display.getActivePanel().removeObjectFromPanel(ipInput);
                ipInput = null;
                display.getActivePanel().removeObjectFromPanel(portInput);
                portInput = null;
            }

            private ServerSlot createServerSlot(int index) {

                ServerSlot temp = null;

                switch (index) {

                    case 1:
                        temp = new ServerSlot(125, 235, 705, 110);
                        con1 = checkServerAlive(config.getServerIP1(), config.getServerPort1());
                        break;
                    case 2:
                        temp = new ServerSlot(125, 355, 705, 110);
                        con2 = checkServerAlive(config.getServerIP2(), config.getServerPort2());
                        break;
                    case 3:
                        temp = new ServerSlot(125, 475, 705, 110);
                        con3 = checkServerAlive(config.getServerIP3(), config.getServerPort3());
                        break;
                    case 4:
                        temp = new ServerSlot(125, 595, 705, 110);
                        con4 = checkServerAlive(config.getServerIP4(), config.getServerPort4());
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

                loadSlots();
                config.save();
            }

            public void saveServer(int type, String serverIP, int serverPort, String name) {

                switch (type) {

                    case 1:
                        config.setSlot1(true);
                        config.setName1(name);
                        config.setServerIP1(serverIP);
                        config.setServerPort1(serverPort);
                        break;
                    case 2:
                        config.setSlot2(true);
                        config.setName2(name);
                        config.setServerIP2(serverIP);
                        config.setServerPort2(serverPort);
                        break;
                    case 3:
                        config.setSlot3(true);
                        config.setName3(name);
                        config.setServerIP3(serverIP);
                        config.setServerPort3(serverPort);
                        break;
                    case 4:
                        config.setSlot4(true);
                        config.setName4(name);
                        config.setServerIP4(serverIP);
                        config.setServerPort4(serverPort);
                        break;
                }
                config.save();
            }

            private boolean checkServerAlive(String ip, int port) {

                try {

                    Socket sock = new Socket ();
                    sock.connect(new InetSocketAddress(ip, port), 200);
                    sock.close();
                    return true;
                } catch (Exception e) {

                    return false;
                }
            }

            private void drawSlotinformation(DrawHelper draw, int type, int x, int y) {

                draw.setColour(Color.BLACK);
                draw.setFont(new Font("Impact", Font.PLAIN, 25));

                if(type == 1) {

                    draw.drawString(config.getName1() + "", x + 74, y + 37);
                    draw.setFont(new Font("Impact", Font.PLAIN, 25));
                    draw.drawString(config.getServerIP1() + "", x + 120, y + 72);
                    draw.drawString(config.getServerPort1() + "", x + 120, y + 97);
                } else if(type == 2) {

                    draw.drawString(config.getName2() + "", x + 74, y + 37);
                    draw.setFont(new Font("Impact", Font.PLAIN, 25));
                    draw.drawString(config.getServerIP2() + "", x + 120, y + 72);
                    draw.drawString(config.getServerPort2() + "", x + 120, y + 97);
                } else if(type == 3) {

                    draw.drawString(config.getName3() + "", x + 74, y + 37);
                    draw.setFont(new Font("Impact", Font.PLAIN, 25));
                    draw.drawString(config.getServerIP3() + "", x + 120, y + 72);
                    draw.drawString(config.getServerPort3() + "", x + 120, y + 97);
                } else if(type == 4) {

                    draw.drawString(config.getName4() + "", x + 74, y + 37);
                    draw.setFont(new Font("Impact", Font.PLAIN, 25));
                    draw.drawString(config.getServerIP4() + "", x + 120, y + 72);
                    draw.drawString(config.getServerPort4() + "", x + 120, y + 97);
                }
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
                    private BufferedImage connectionTrue;
                    private BufferedImage connectionFalse;

                public ServerSlot(int x, int y, int width, int height) {

                    this.x = x;
                    this.y = y;
                    this.width = width;
                    this.height = height;

                    this.image = ImageHelper.getImage("res/images/menu/server-slot.png");
                    this.connectionTrue = ImageHelper.getImage("res/images/menu/connection-icon2.png");
                    this.connectionFalse = ImageHelper.getImage("res/images/menu/connection-icon1.png");

                    join = new Button(x + 613, y + 72, 78, 30, "res/images/menu/buttons/Join-server-button", true);
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

                public BufferedImage getConnectionTrue() {

                    return connectionTrue;
                }

                public BufferedImage getConnectionFalse() {

                    return connectionFalse;
                }
            }
        }
