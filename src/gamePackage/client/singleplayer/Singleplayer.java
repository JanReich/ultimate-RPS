package gamePackage.client.singleplayer;

import gamePackage.client.menu.MenuController;
import graphics.Display;
import graphics.interfaces.BasicInteractableObject;
import toolBox.DrawHelper;
import toolBox.ImageHelper;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Singleplayer implements BasicInteractableObject {

    private BufferedImage background;
    private BufferedImage stein;
    private BufferedImage papier;
    private BufferedImage schere;
    private BufferedImage win;
    private BufferedImage lost;
    private BufferedImage linkeHand;
    private BufferedImage rechteHand;
    private MenuController controller;
    private int auswahlStein;
    private int auswahlPapier;
    private int auswahlSchere;
    private int auswahl = 0;
    private boolean won = false;
    private boolean canPlay = true;
    private boolean end = false;
    private int KIscore = 0;
    private int playerscore = 0;
    private String motivationText = "->";




    public Singleplayer(Display display){

        this.auswahlPapier = 1;
        this.auswahlSchere = 2;
        this.auswahlStein  = 3;
        stein = ImageHelper.getImage("res/images/Singleplayer/stone.png");
        papier = ImageHelper.getImage("res/images/Singleplayer/paper.png");
        schere = ImageHelper.getImage("res/images/Singleplayer/schere.png");
        background = ImageHelper.getImage("res/images/Singleplayer/background.png");
        win = ImageHelper.getImage("res/images/Singleplayer/win.png");
        lost = ImageHelper.getImage("res/images/Singleplayer/lost.png");

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
            if (event.getX() >= 416 && event.getX() <= 546 && event.getY() >= 516 && event.getY() <= 646) {
                System.out.println("Stein");
                auswahl = auswahlStein;

                KImove();
            }

            //Papier entspricht der Nummer 1
            if (event.getX() >= 546 && event.getX() <= 676 && event.getY() >= 516 && event.getY() <= 646) {
                System.out.println("Papier");
                auswahl = auswahlPapier;

                KImove();

            }


            //Schere entspricht der Nummer 2
            if (event.getX() >= 286 && event.getX() <= 416 && event.getY() >= 516 && event.getY() <= 646) {
                System.out.println("Schere");
                auswahl = auswahlSchere;

                KImove();

            }
        }

    }

    public void KImove(){
        int KIauswahl = (int)(Math.random()*3+1);
        if(auswahl==1&&KIauswahl==3||auswahl==2&&KIauswahl==1||auswahl==3&&KIauswahl==2){

            playerscore = playerscore +1;

            motivationText = "-> Nice!";
        }else if(auswahl==KIauswahl){
            canPlay = true;

            motivationText = "-> Unentschieden...";
        }else{

            KIscore = KIscore + 1;

            motivationText = "-> Verloren :(";
        }

        if(playerscore >= 3){
            canPlay=false;
            motivationText = "Geiler Typ!";
            won = true;
            end = true;

            //WinScreen
        }else if(KIscore >= 3){
            canPlay=false;
            motivationText = "Du bist kacke!";
            won = false;
            end = true;
            //LooseScreen
        }

    }


    @Override
    public void update(double delta) {

    }

    @Override
    public void draw(DrawHelper draw) {
        draw.drawImage(background,0,0,1000,1000);
        if(canPlay) {
            draw.drawImage(stein, 416, 516, 128, 128);
            draw.drawImage(schere, 286, 516, 128, 128);
            draw.drawImage(papier, 546, 516, 128, 128);
        }

        draw.drawString("Player: "+ playerscore +" ----- KI: "+KIscore,370,50);
        draw.drawString(motivationText,370,80);

        if(end){
            if(won){
                draw.drawImage(win, 250, 250, 500, 500);
            }else if(!won){
                draw.drawImage(lost, 250, 150, 500, 457);
            }
        }
    }

}