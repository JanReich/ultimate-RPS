package main;

import gamePackage.client.menu.MenuController;
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
