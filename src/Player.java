    package src;
    import javax.swing.*;
    import java.awt.*;
    import java.util.ArrayList;

    public class Player extends Entity{
        private int velocity; //Movement speed of player
        private int lives; //number of lives player has

        private boolean active = true; 

        private Image sprite; //Scaled player sprite image

        public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
        private long lastShotTime = 0;
        private final long shootDelay = 300; // 0.3 seconds


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

            // Shooting logic
            // Shooting while holding SPACE (every 0.3s)
            if (keyHandler.shootPressed) {
                long now = System.currentTimeMillis();
                if (now - lastShotTime >= shootDelay) {
                    int bulletX = x + width / 2 - 2; // center of ship
                    int bulletY = y - 10;            // just above the ship
                    bullets.add(new Bullet(bulletX, bulletY, 4, 12, 12, true));
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

            //draw bullets
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
