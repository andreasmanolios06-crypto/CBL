package src;
import javax.swing.*;
import java.awt.*;

public class Asteroid extends Entity {
    private int lane; //determine which lane it will be loaded in

    private int velocity; //Movement speed of asteroid

    private Image sprite; //Scaled asteroid sprite image


    //Constructor to initialize player position and size
    public Asteroid(int lane, int startX, int startY, int width, int height, int velocity, String spritePath) {
        //Initialize asteroid position and sizes
        super(startX, startY, width, height);
        this.velocity = velocity;

        loadSprite(spritePath); //Load asteroid sprite from file
        
    }

    public void update(double delta) {
        fall(delta);
    }
    
    //load image from file
    private void loadSprite(String path) { 
        ImageIcon icon = new ImageIcon(path);
        sprite = icon.getImage();
    }

    //Draw asteroid on screen
    public void draw(Graphics g) {
        g.drawImage(sprite, x, y, width, height, null);
    }

    //causes movement of asteroid
    private void fall(double delta) {
        y += velocity * delta;
    }
    
}
