package gamePackage.client.singleplayer;

import graphics.interfaces.BasicInteractableObject;
import toolBox.DrawHelper;

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
    private String auswahlStein;
    private String auswahlPapier;
    private String auswahlSchere;
    //


    public Singleplayer(String auswahlPapier, String auswahlSchere, String auswahlStein){

        this.auswahlPapier = "Papier";
        this.auswahlSchere = "Schere";
        this.auswahlStein  = "Stein";

    }

    @Override
    public void keyPressed(KeyEvent event) {

    }

    @Override
    public void keyReleased(KeyEvent event) {

    }

    @Override
    public void mouseReleased(MouseEvent event) {

    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void draw(DrawHelper draw) {

    }
}