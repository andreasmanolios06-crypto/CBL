package src;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.*;


//using a lane system, this class manages what entities spawn (asteroids and enemies) and in which lanes, so they dont collide 
public class Spawner {
    private List<Asteroid> asteroids; //array containing asteroids
    private Random random; 
    private int laneCount; //number of lanes in game
    private int laneWidth; //width of a lane
    private int asteroidSize = 64;

    //timer 
    private double spawnTimer = 0; 
    private  double spawn_interval = 0.5; //every x seconds

    //TODO: FIX DIFFICULTY 
    private int difficulty = 2;
    
    //defining object of AsteroidManager
    public Spawner(int laneCount, int gameWidth) {
        this.asteroids = new ArrayList<>();
        this.random = new Random();
        this.laneCount = laneCount;
        this.laneWidth = gameWidth / laneCount;
    }

    public void update(double delta) {
        //update asteroids 
        for (Asteroid asteroid: asteroids) {
            asteroid.update(delta);
        }

        //remove asteroids if they out of bounds
        asteroids.removeIf(asteroid -> asteroid.getY() > GamePanel.HEIGHT);

        // Spawn new asteroids randomly
        spawnTimer += delta / 60.0; //converting delta to seconds
        
        //temporary difficulty over time implementation
        if (difficulty < 20) {
            difficulty += 0.5;
        }

        if (spawnTimer >= spawn_interval) {
            spawnAsteroid();
            spawnTimer = 0; //reset timer
        }
    }

    private void spawnAsteroid() {
        //selects spawning method 
        int method = random.nextInt(4) + 1;
    
        // Get 1-3 different lanes
        int lane1 = random.nextInt(laneCount) + 1;
        int lane2 = random.nextInt(laneCount) + 1;
        int lane3 = random.nextInt(laneCount) + 1;

        
        while (lane2 == lane1 || lane2 == lane3) {
            lane2 = random.nextInt(laneCount) + 1;
        }
        while (lane3 == lane1 || lane3 == lane2) {
            lane3 = random.nextInt(laneCount) + 1;
        }
    
        // Calculate positions for each lane
        int x1 = calculateLaneX(lane1);
        int x2 = calculateLaneX(lane2);
        int x3 = calculateLaneX(lane3);
        int startY = -asteroidSize; 

        // Create asteroids with their respective positions
        Asteroid asteroid1 = new Asteroid(lane1, x1, startY, asteroidSize, asteroidSize, 3*difficulty, "src/sprites/asteroid_placeholder.png");
        Asteroid asteroid2 = new Asteroid(lane2, x2, startY, asteroidSize, asteroidSize, 3*difficulty, "src/sprites/asteroid_placeholder.png");
        Asteroid asteroid3 = new Asteroid(lane3, x3, startY, asteroidSize, asteroidSize, 3*difficulty, "src/sprites/asteroid_placeholder.png");
        

        //Different ways of spawning for each wave
        if (method == 1) {
            asteroids.add(asteroid1);
        }
        else if (method == 2) {
            asteroids.add(asteroid1);
            asteroids.add(asteroid2);
        }
        else if (method == 3) {
            int random2 = random.nextInt(2) + 1;
            
            if (random2 == 1) {
                asteroids.add(asteroid1);
                asteroids.add(asteroid2);
                asteroids.add(asteroid3);
            } 
            else if (random2 == 2) {
                asteroids.add(asteroid1);
                asteroids.add(asteroid2);
            }
        } else {
            return;
        }
        
    }

    //calculate X position based on lane number
    private int calculateLaneX(int lane) {
        return (lane - 1) * laneWidth + (laneWidth/2) - (asteroidSize/2); //centered in lane
    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    public void draw(Graphics g) {
        for (Asteroid asteroid: asteroids) {
            asteroid.draw(g);
        }
    }

}
