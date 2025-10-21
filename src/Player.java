package src;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class Player {
    //Variables defining player size & position
    private int width;
    private int height;

    private int x; //Initial X position of player
    private int y; //Initial Y position of player

    private int velocity; //Movement speed of player

    private Image sprite; //Scaled player sprite image

    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private long lastShotTime = 0;
    private final long shootDelay = 300; // 0.3 seconds



    //Constructor to initialize player position and size
    public Player(int startX, int startY, int width, int height, int velocity, String spritePath) {
        //Initialize player position and sizes
        this.x = startX;
        this.y = startY;
        this.width = width;
        this.height = height;
        this.velocity = velocity;

        loadSprite(spritePath); //Load player sprite from file
        
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

        // Shooting logic
        // Shooting while holding SPACE (every 0.3s)
        if (keyHandler.shootPressed) {
            long now = System.currentTimeMillis();
            if (now - lastShotTime >= shootDelay) {
                int bulletX = x + width / 2 - 2; // center of ship
                int bulletY = y - 10;            // just above the ship
                bullets.add(new Bullet(bulletX, bulletY));
                lastShotTime = now;
            }
}

// Update bullets and remove the ones off-screen
for (int i = 0; i < bullets.size(); i++) {
    Bullet b = bullets.get(i);
    b.update();
    if (!b.alive) {
        bullets.remove(i);
        i--;
    }
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

        for (Bullet b : bullets) {
        b.draw(g);
        }
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
}