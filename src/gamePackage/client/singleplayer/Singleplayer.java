package gamePackage.client.singleplayer;

import gamePackage.client.menu.MenuController;
import graphics.Display;
import graphics.interfaces.BasicInteractableObject;
import toolBox.Animation;
import toolBox.DrawHelper;
import toolBox.Button;
import toolBox.ImageHelper;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Singleplayer implements BasicInteractableObject {

    private Display display;
    private MenuController controller;
    private BufferedImage background;
    private BufferedImage stein;
    private BufferedImage papier;
    private BufferedImage schere;
    private BufferedImage win;
    private BufferedImage lost;
    private Animation playerHand;
    private Animation comHand;
    private Button backButton;


    private int choice = 0;
    private int kiChoice = 0;
    private boolean won = false;
    private boolean canPlay = true;
    private boolean end = false;
    private int kiScore = 0;
    private int playerscore = 0;
    private String motivationText = "->";


    public Singleplayer(Display display, MenuController controller){
        this.display = display;
        this.controller = controller;
        init();


    }

    public void init(){
        stein = ImageHelper.getImage("res/images/Singleplayer/stone.png");
        papier = ImageHelper.getImage("res/images/Singleplayer/paper.png");
        schere = ImageHelper.getImage("res/images/Singleplayer/schere.png");
        background = ImageHelper.getImage("res/images/Singleplayer/background.png");
        win = ImageHelper.getImage("res/images/Singleplayer/win.png");
        lost = ImageHelper.getImage("res/images/Singleplayer/lost.png");
        playerHand = new Animation("res/images/Singleplayer/stone-ani.png", 0.04, 22, 0, false);
        comHand = new Animation("res/images/Singleplayer/stone-ani-right.png", 0.04, 22, 0, false);
        backButton = new Button(50,850,210,56,"res/images/menu/buttons/kicked-back",true);
        display.getActivePanel().drawObjectOnPanel(backButton);
    }

    public void createAnimation(){
        if(choice!=0) {
            if (choice == 1) {
                playerHand = new Animation("res/images/Singleplayer/stone-ani.png", 0.04, 22, 0, false);
            } else if (choice == 2) {
                playerHand = new Animation("res/images/Singleplayer/paper-ani.png", 0.04, 22, 0, false);
            } else if (choice == 3) {
                playerHand = new Animation("res/images/Singleplayer/scissors-ani.png", 0.04, 22, 0, false);
            }
            display.getActivePanel().drawObjectOnPanel(playerHand);
        }

        if(kiChoice!=0) {
            if (kiChoice == 1) {
                comHand = new Animation("res/images/Singleplayer/stone-ani-right.png", 0.04, 22, 0, false);
            } else if (kiChoice == 2) {
                comHand = new Animation("res/images/Singleplayer/paper-ani-right.png", 0.04, 22, 0, false);
            } else if (kiChoice == 3) {
                comHand = new Animation("res/images/Singleplayer/scissors-ani-right.png", 0.04, 22, 0, false);
            }
            display.getActivePanel().drawObjectOnPanel(comHand);
        }


    }

    @Override
    public void draw(DrawHelper draw) {


        draw.drawImage(background,0,0,1000,1000);
        if(canPlay) {
            draw.drawImage(stein, 430, 300, 128, 128);
            draw.drawImage(schere, 300, 300, 128, 128);
            draw.drawImage(papier, 560, 300, 128, 128);
        }

        draw.drawString("Player: "+ playerscore +" ----- KI: "+ kiScore,370,50);
        draw.drawString(motivationText,370,80);

        if(end){
            if(won){
                draw.drawImage(win, 250, 250, 500, 500);
            }else if(!won){
                draw.drawImage(lost, 250, 150, 500, 457);
            }
        }
        if(choice != 0) {
            draw.drawImage(playerHand.getAnimation(), 0, 400, 350, 350);
        }else{
            draw.drawImage(playerHand.getFirstSprite(),0,400,350,350);
        }
        if(kiChoice != 0){
            draw.drawImage(comHand.getAnimation(), 615, 400, 350, 350);
        }else{
            draw.drawImage(comHand.getFirstSprite(),615,400,350,350);
        }
        if(end){
            draw.drawButton(backButton);
        }



    }

    @Override
    public void keyPressed(KeyEvent event) {

    }

    @Override
    public void keyReleased(KeyEvent event) {

    }

    @Override
    public void mouseReleased(MouseEvent event) {
        if(canPlay) {
            //Stein entspricht der Nummer 3
            if (event.getX() >= 430 && event.getX() <= 430+128 && event.getY() >= 300 && event.getY() <= 300+128) {
                System.out.println("Stein");
                choice = 1;
                kiChoice = (int)(Math.random()*3+1);
                canPlay = false;
            }

            //Papier entspricht der Nummer 1
            if (event.getX() >= 560 && event.getX() <= 560+128 && event.getY() >= 300 && event.getY() <= 300+128) {
                System.out.println("Papier");
                choice = 2;
                kiChoice = (int)(Math.random()*3+1);
                canPlay = false;
            }


            //Schere entspricht der Nummer 2
            if (event.getX() >= 300 && event.getX() <= 300+128 && event.getY() >= 300 && event.getY() <= 300+128) {
                System.out.println("Schere");
                choice = 3;
                kiChoice = (int)(Math.random()*3+1);
                canPlay = false;
            }
            createAnimation();
        }

    }



    public void chooseWinner(){
        if(choice ==1&&kiChoice==3|| choice ==2&&kiChoice==1|| choice ==3&&kiChoice==2){
            playerscore = playerscore +1;
            motivationText = "-> Nice!";
        }else if(choice ==kiChoice){
            canPlay = true;
            motivationText = "-> Unentschieden...";
        }else{
            kiScore = kiScore + 1;
            motivationText = "-> Verloren :(";
        }

        if(playerscore >= 3){
            motivationText = "Geiler Typ!";
            won = true;
            end = true;
            canPlay = false;
            //WinScreen
        }else if(kiScore >= 3){
            motivationText = "Du bist kacke!";
            won = false;
            end = true;
            canPlay = false;
            //LooseScreen
        }


    }


    @Override
    public void update(double delta) {
        if(playerHand.isFinished()&& !canPlay && !end){
            canPlay = true;
            chooseWinner();
        }

        if(backButton.isClicked() && end){
            controller.createMainMenu();
            controller.removeSingleplayer();
        }

    }



}