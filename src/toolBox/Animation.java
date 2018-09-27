package toolBox;

import graphics.interfaces.GraphicalObject;

import java.awt.image.BufferedImage;

/**
 * Created by David on 20.09.2017.
 */
public class Animation implements GraphicalObject {

    private double spriteTimer;
    private double animationSpeed;
    private double breakTime;
    private int spriteNumber;
    private int amountOfImages;
    private boolean repeating;
    private boolean stopped;

    private BufferedImage image;
    private BufferedImage[] sprites;
    private BufferedImage spriteSheet;



    public Animation(String path, double animationSpeed, int amountOfImages, double breakTime){
        this.animationSpeed = animationSpeed;
        this.amountOfImages = amountOfImages;
        this.breakTime = breakTime;
        this.spriteSheet = ImageHelper.getImage(path);
        this.sprites = new BufferedImage[amountOfImages];
        repeating = true;
        spriteTimer = animationSpeed;
        splitSheet();
    }
    public Animation(String path, double animationSpeed, int amountOfImages, double breakTime, boolean repeating){
        this.animationSpeed = animationSpeed;
        this.amountOfImages = amountOfImages;
        this.breakTime = breakTime;
        this.spriteSheet = ImageHelper.getImage(path);
        this.sprites = new BufferedImage[amountOfImages];
        this.repeating = repeating;
        spriteTimer = animationSpeed;
        splitSheet();
    }

    public void splitSheet(){
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSubimage(spriteSheet.getWidth()/amountOfImages * i, 0, spriteSheet.getWidth()/amountOfImages, spriteSheet.getHeight());
        }
    }

    public BufferedImage getAnimation(){
        if (spriteTimer < 0) {
            if (spriteNumber <= sprites.length - 1) {
                image = sprites[spriteNumber];
                spriteNumber += 1;
                spriteTimer = animationSpeed;
            } else if(repeating){
                spriteNumber = 0;
                spriteTimer = breakTime;
            }
        }
        return image;
    }

    @Override
    public void update(double dt) {

        if(stopped)
            spriteTimer = spriteTimer -dt;
    }

    @Override
    public void draw(DrawHelper draw) {

    }

    public void start() {

        stopped = false;
    }

    public void stop() {

        stopped = true;
    }
}
