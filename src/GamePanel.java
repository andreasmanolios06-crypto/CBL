package src;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class GamePanel extends JPanel implements Runnable {
    //constants defining the game panel size
    public static final int WIDTH = 600; 
    public static final int HEIGHT = 800;

    //Game state variables
    private Thread gameThread;
    private volatile boolean running = false; //volatile ensures all threads see the most recent value
    private static final int TARGET_FPS = 60;
    private static final long OPTIMAL_TIME = 1000000000L / TARGET_FPS;

    private int[] starX = new int[100]; //X positions of 100 stars
    private int[] starY = new int[100]; //Y positions of 100 stars
    private int[] starSpeed = new int[100]; //Speed of each star

    //fps tracking variables 
    private long lastFpsTime = 0;
    private int frameCount = 0;
    private int currentFps = 0;

    //keyhandler declaration
    KeyHandler keyHandler = new KeyHandler();

    //player instance
    private Player player = new Player(
        WIDTH/2 - 32, 
        HEIGHT - 100, 
        64, 
        64, 
        8, 
        3,
        "src/sprites/swordfish_sprite.png");

    //asteroid manager instance
    private Spawner spawner = new Spawner(5, GamePanel.WIDTH);

    //game panel instance   
    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true); //can recieve key input focus
        setDoubleBuffered(true);

        //Allowing our keyHandler to listen to keys
        addKeyListener(keyHandler);
        
        //make sure window is focused so you can detect input
        requestFocusInWindow();

        //Initializing the stars positions and speeds
        for (int i = 0; i < starX.length; i++) {
            starX[i] = (int)(Math.random() * WIDTH);
            starY[i] = (int)(Math.random() * HEIGHT);
            starSpeed[i] = 1 + (int)(Math.random() * 3); //Speed between 1, 2 or 3
        }
    }
    
    public void startGame() {
        running = true; 
        gameThread = new Thread(this); //create thread that runs this class
        gameThread.start(); //start the thread (calls run() method)
    }

    @Override
    public void run() {
        long lastUpdateTime = System.nanoTime();

        while (running) {
            long now = System.nanoTime(); //current time in nanoseconds
            long updateLength = now - lastUpdateTime; //how long since last frame
            lastUpdateTime = now; //remember current time for next frame

            double delta = updateLength / (double)OPTIMAL_TIME; //optimal time should be 16.67 (eg. at delta = 1.0 game runs 60fps, delta = 2.0 game runs at 30 fps, ect)

            updateGame(delta); //update the game state with delta time
            repaint(); //request Swing to redraw the screen
            updateFPS(); //Update FPS Counter

            //Calculate sleep time to maintain 60 FPS 
            long sleepTime = (lastUpdateTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime); //executing thread sleeps for sleep time, to ensure running of program at constant rate
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
        
    private void updateGame(double delta) {
        //Update star positions (moving down the screen) - simple approach
        for (int i = 0; i < starY.length; i++) {
            starY[i] += starSpeed[i]; // Remove delta for stars - they're background
            
            //when a star goes off screen, reset it to the top
            if (starY[i] > HEIGHT) {
                starY[i] = 0;
                starX[i] = (int)(Math.random() * WIDTH);
                starSpeed[i] = 1 + (int)(Math.random() * 3);
            }
        }

        //player movement with delta time 
        player.update(keyHandler, delta);

        //spawner movement with delta time
        spawner.update(delta);

        checkCollisions();
    }
    
    private void updateFPS() {
        frameCount++;
        long currentTime = System.nanoTime();

        //Update FPS counter every second 
        if (currentTime - lastFpsTime >= 1000000000L) {
            currentFps = frameCount;
            frameCount = 0;
            lastFpsTime = currentTime;
        }
    }

    public void checkCollisions() {
        //temporary lists
        ArrayList<SpaceObjects> objectsToRemove = new ArrayList<>();
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();

        //check collisions between player and space objects
        for (SpaceObjects spaceObject : spawner.getspaceObjects()) {
            if (player.intersects(spaceObject)) {
                player.takeDamage();
                objectsToRemove.add(spaceObject);
                break; //one collisions per frame
            }
        }

        //collisions between player and bullets
        for (Bullet bullet : player.bullets) {
            for (SpaceObjects spaceObject : spawner.getspaceObjects()) {
                if ((spaceObject instanceof Enemy || spaceObject instanceof Asteroid) && 
                    bullet.intersects(spaceObject)) {
                    
                    bulletsToRemove.add(bullet);
                    
                    if (spaceObject instanceof Enemy) {
                        Enemy enemy = (Enemy) spaceObject;
                        enemy.takeDamage();
                        if (!enemy.isActive()) {
                            objectsToRemove.add(spaceObject);
                        }
                    } else if (spaceObject instanceof Asteroid) {
            
                    }
                    break;
                } 
            }
        }

        // collisions between enemy bullets and player or asteroids
        for (SpaceObjects spaceObject : spawner.getspaceObjects()) {
            if (spaceObject instanceof Enemy) {
                Enemy enemy = (Enemy) spaceObject;
                
                for (Bullet bullet : enemy.getBullets()) {
                    //for player
                    if (bullet.getBounds().intersects(player.getBounds())) {
                        bulletsToRemove.add(bullet);
                        player.takeDamage();
                        System.out.println("Player hit by enemy bullet");
                        continue; // next bullet
                    }
                    
                    // for asteroids
                    for (SpaceObjects asteroid : spawner.getspaceObjects()) {
                        if (asteroid instanceof Asteroid && bullet.getBounds().intersects(asteroid.getBounds())) {
                            
                            bulletsToRemove.add(bullet);  // Only remove the bullet
                            System.out.println("Enemy bullet hit asteroid (bullet destroyed)");
                            break;
                            
                        }
                    }
                }
            }    
        }

        //remove objects after loop
        spawner.getspaceObjects().removeAll(objectsToRemove);
        player.bullets.removeAll(bulletsToRemove);
        
        //remove enemy bullets from enemies
        for (SpaceObjects spaceObject : spawner.getspaceObjects()) {
            if (spaceObject instanceof Enemy) {
                Enemy enemy = (Enemy) spaceObject;
                enemy.getBullets().removeAll(bulletsToRemove);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //Drawing background 
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //Draw stars
        g.setColor(Color.WHITE);
        for (int i = 0; i < starX.length; i++) {
            g.fillRect(starX[i], starY[i], 2, 2);
        }

        //Draw player sprite
        if (player.isActive()) {
            player.draw(g);
        }
        

        //Draw asteroid sprite
        spawner.draw(g);

        //Draw FPS Count 
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("FPS: " + currentFps, 10, 20);
    }
  
    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }
}