package src;
import javax.swing.*;
import java.awt.*;

public abstract class SpaceObjects extends Entity {

    private Image sprite;
    protected int velocity;
    
    //Constructor to initialize player position and size
    public SpaceObjects(int startX, int startY, int width, int height, int velocity, String spritePath) {
        //Initialize asteroid position and sizes
        super(startX, startY, width, height);
        this.velocity = velocity;

        loadSprite(spritePath);
    }

    //Draw asteroid on screen
    public void draw(Graphics g) {
        g.drawImage(sprite, x, y, width, height, null);
    }

    //load image from file
    private void loadSprite(String path) { 
        ImageIcon icon = new ImageIcon(path);
        sprite = icon.getImage();
    }

    public abstract void update(double delta);
}
