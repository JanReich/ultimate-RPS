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

        //Stein entspricht der Nummer 3
        if(event.getX()>=416&&event.getX()<=546&&event.getY()>=516&&event.getY()<=646){
            System.out.println("Stein");
            auswahl = auswahlStein;
        }

        //Papier entspricht der Nummer 1
        if(event.getX()>=546&&event.getX()<=676&&event.getY()>=516&&event.getY()<=646){
            System.out.println("Papier");
            auswahl = auswahlPapier;
        }


        //Schere entspricht der Nummer 2
        if(event.getX()>=286&&event.getX()<=416&&event.getY()>=516&&event.getY()<=646){
            System.out.println("Schere");
            auswahl = auswahlSchere;
        }

    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void draw(DrawHelper draw) {
        draw.drawImage(stein,416,516,128,128);
        draw.drawImage(schere,286,516,128,128);
        draw.drawImage(papier,546,516,128,128);
    }
}