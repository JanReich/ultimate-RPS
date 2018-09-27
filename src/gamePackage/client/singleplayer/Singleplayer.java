package gamePackage.client.singleplayer;

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



    public Singleplayer(int auswahlPapier, int auswahlSchere, int auswahlStein){

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


        //Papier entspricht der Nummer 1



        //Schere entspricht der Nummer 2

    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void draw(DrawHelper draw) {
        draw.drawImage(stein,416,416,128,128);
    }
}