package src;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends SpaceObjects {
    private int lane; //determine which lane it will be loaded in
    private int health;
    private boolean active = true;

    // Shooting variables
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private long lastShotTime = 0;
    private final long shootDelay = 2000; // 2 seconds between shots
    private Random random = new Random();

    //Constructor to initialize player position and size
    public Enemy(int lane, int startX, int startY, int width, int height, int velocity, int health, String spritePath) {
        //Initialize asteroid position and sizes
        super(startX, startY, width, height, velocity, spritePath);
        this.health = health;
        
    }

    @Override
    public void update (double delta) {
        fall(delta);

        if (health <= 0) {
            die();
        }

        // Random shooting
        long now = System.currentTimeMillis();
        if (now - lastShotTime >= shootDelay) {
            if (random.nextDouble() < 0.3) { // 30% chance to shoot each interval
                int bulletX = x + width / 2 - 2; // center of enemy
                int bulletY = y + height; // below the enemy
                bullets.add(new Bullet(bulletX, bulletY, 4, 12, 10, false));
                SoundPlayer.playSound("src/sounds/enemylaserShoot.wav");
                lastShotTime = now;
            }
        }

        // Update enemy bullets
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            b.update();
            if (!b.alive) {
                bullets.remove(i);
                i--;
            }
        }
    }
    
    private void fall(double delta) {
        y += velocity * delta;
    }

    private void die() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void takeDamage() {
        health--;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    @Override
    public void draw(java.awt.Graphics g) {
        super.draw(g); //Draw the enemy sprite
        
        //Draw enemy bullets red color
        for (Bullet b : bullets) {
            b.draw(g);
        }
    }
}


