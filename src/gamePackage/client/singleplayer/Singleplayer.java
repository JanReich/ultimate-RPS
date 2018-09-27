package gamePackage.client.singleplayer;

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
    private BufferedImage linkeHand;
    private BufferedImage rechteHand;
    private int auswahlStein;
    private int auswahlPapier;
    private int auswahlSchere;
    private int auswahl = 0;
    private boolean won = false;
    private boolean canPlay = true;



    public Singleplayer(Display display){

        this.auswahlPapier = 1;
        this.auswahlSchere = 2;
        this.auswahlStein  = 3;
        stein = ImageHelper.getImage("res/images/Singleplayer/stone.png");
        papier = ImageHelper.getImage("res/images/Singleplayer/paper.png");
        schere = ImageHelper.getImage("res/images/Singleplayer/schere.png");

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
                canPlay = false;
                KImove();
            }

            //Papier entspricht der Nummer 1
            if (event.getX() >= 546 && event.getX() <= 676 && event.getY() >= 516 && event.getY() <= 646) {
                System.out.println("Papier");
                auswahl = auswahlPapier;
                canPlay = false;
                KImove();

            }


            //Schere entspricht der Nummer 2
            if (event.getX() >= 286 && event.getX() <= 416 && event.getY() >= 516 && event.getY() <= 646) {
                System.out.println("Schere");
                auswahl = auswahlSchere;
                canPlay = false;
                KImove();

            }
        }

    }

    public void KImove(){
        int KIauswahl = (int)(Math.random()*3+1);
        if(auswahl==1&&KIauswahl==3||auswahl==2&&KIauswahl==1||auswahl==3&&KIauswahl==2){
            won = true;
        }else if(auswahl==KIauswahl){
            canPlay = true;
            System.out.println("Unentschieden");
        }else{
            won = false;
        }
        if(won){

            System.out.println("Du hast verloren");
        }else{

            System.out.println("Du hast gewonnen");
        }

    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void draw(DrawHelper draw) {
        if(canPlay) {
            draw.drawImage(stein, 416, 516, 128, 128);
            draw.drawImage(schere, 286, 516, 128, 128);
            draw.drawImage(papier, 546, 516, 128, 128);
        }
    }
}