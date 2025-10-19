package game;

import java.awt.*;

public class Bullet {
    public int x, y;
    public int width = 4;
    public int height = 12;
    public int speed = 12;
    public boolean alive = true;

    public Bullet(int startX, int startY) {
        x = startX;
        y = startY;
    }

    public void update() {
        y -= speed;
        if (y + height < 0) {
            alive = false;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.CYAN); // blaster color
        g.fillRect(x, y, width, height);
    }

    // for future collisions if needed
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
