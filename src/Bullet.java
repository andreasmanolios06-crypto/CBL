package src;

import java.awt.*;

public class Bullet extends Entity{
    public boolean alive = true;
    private int velocity;
    private boolean isPlayerBullet;

    public Bullet(int startX, int startY, int width, int height, int velocity, boolean isPlayerBullet) {
        super(startX, startY, width, height);
        this.velocity = velocity;
        this.isPlayerBullet = isPlayerBullet;
    }

    public void update() {
        if (isPlayerBullet) {
            y -= velocity; //Player bullets go up
        } else {
            y += velocity; //Enemy bullets go down
        }
        
        // Remove if off screen
        if (y + height < 0 || y > GamePanel.HEIGHT) {
            alive = false;
        }
    }

    public void draw(Graphics g) {
        if (isPlayerBullet) {
            g.setColor(Color.CYAN); // blue player bullets
        } else {
            g.setColor(Color.RED); // red enemy bullets
        }
        g.fillRect(x, y, width, height);
    }
    

    // for future collisions if needed
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean getIsPlayerBullet(){
        return this.isPlayerBullet;
    }
}
