package src;
import javax.swing.*;
import java.awt.*;

public class Player extends Entity{
    private int velocity; //Movement speed of player
    private int lives; //number of lives player has

    private boolean active = true; 

    private Image sprite; //Scaled player sprite image


    //Constructor to initialize player position and size
    public Player(int startX, int startY, int width, int height, int velocity, int lives, String spritePath) {
        //Initialize asteroid position and sizes
        super(startX, startY, width, height);
        this.lives = lives;
        this.velocity = velocity;

        loadSprite(spritePath); //Load asteroid sprite from file
        
    }
    
    public void update(KeyHandler keyHandler, double delta) {
        if (keyHandler.leftPressed && keyHandler.rightPressed) {
            //both pressed, character remains stationary
            return;
        }
        if (keyHandler.leftPressed) {
            moveLeft(delta);
        }
        if (keyHandler.rightPressed) {
            moveRight(delta);
        }

        if (lives <= 0) {
            die();
        }
    }

    
    //Loads player sprite from file
    private void loadSprite(String path) {
        //load image from file 
        ImageIcon icon = new ImageIcon(path);
        sprite = icon.getImage();
    }

    //Draw player on screen
    public void draw(Graphics g) {
        g.drawImage(sprite, x, y, width, height, null);
    }
    
    //Moves character to the left
    private void moveLeft(double delta) {
        x -= velocity * delta;
        if (x < 0) x = 0; //Prevent moving off left edge
    }

    //Moves character to the right
    private void moveRight(double delta) {
        x += velocity * delta;
        if (x + width > GamePanel.WIDTH) x = GamePanel.WIDTH - width; //Prevent moving off right edge
    }

    private void die() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void takeDamage() {
        lives--;
    }

}
