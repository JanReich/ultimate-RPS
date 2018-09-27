package main;

import gamePackage.client.menu.MenuController;

import gamePackage.server.GameServer;
import graphics.Display;

import java.awt.*;

public class MainProgram {

                //Attribute

                //Referenzen
            private Display display;
            private MenuController controller;

        public MainProgram() {

            new GameServer(666, 2, 2,2,true);

            display = new Display();
            controller = new MenuController(display);

            display.getActivePanel().addManagement(controller);
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
