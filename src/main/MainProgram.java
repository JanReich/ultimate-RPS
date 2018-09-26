package main;

import gamePackage.client.GameClient;
import gamePackage.client.menu.MenuController;
import gamePackage.server.GameServer;
import graphics.Display;
import toolBox.TextTransfer;

import java.awt.*;

public class MainProgram {

                //Attribute

                //Referenzen
            private Display display;
            private MenuController controller;

        public MainProgram() {

            display = new Display();
            controller = new MenuController(display);

            display.getActivePanel().addManagement(controller);

            new GameServer(666);
            new GameClient("localhost", 666);
        }

        public static void main(String[] args) {

            EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {

                    new MainProgram();
                }
            });
        }
    }
