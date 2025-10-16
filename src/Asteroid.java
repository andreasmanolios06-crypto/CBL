package src;
import javax.swing.*;
import java.awt.*;

public class Asteroid {
    //Variables defining asteroid size & position
    private int width;
    private int height;

    private int lane; //determine which lane it will be loaded in

    private int x; //Initial X position of asteroid
    private int y; //Initial Y position of asteroid

    private int velocity; //Movement speed of asteroid

    private Image sprite; //Scaled asteroid sprite image


    //Constructor to initialize player position and size
    public Asteroid(int lane, int startX, int startY, int width, int height, int velocity, String spritePath) {
        //Initialize asteroid position and sizes
        this.lane = lane;
        this.x = startX;
        this.y = startY;
        this.width = width;
        this.height = height;
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

    //returns Y-coordinate position of asteroid
    public int getY() {
        return this.y;
    }
    
}
