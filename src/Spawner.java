package src;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.*;


//using a lane system, this class manages what spaceObjects spawn (asteroids and enemies) and in which lanes, so they dont collide 
public class Spawner {
    private List<SpaceObjects> spaceObjects; //array containing asteroids

    private Random random; 
    private int laneCount; //number of lanes in game
    private int laneWidth; //width of a lane
    private int size = 64;

    //timer 
    private double spawnTimer = 0; 
    private  double spawn_interval = 0.6; //every x seconds 

    //difficulty (acts as velocity) 
    public int difficulty = 6;
       
    //defining object of AsteroidManager
    public Spawner(int laneCount, int gameWidth) {
        this.spaceObjects = new ArrayList<>();
        this.random = new Random();
        this.laneCount = laneCount;
        this.laneWidth = gameWidth / laneCount;
    }

    public void update(double delta) {
        //update asteroids 
        for (SpaceObjects spaceObject: spaceObjects) {
            spaceObject.update(delta);
        }

        //remove objects out of bounds and award points
        spaceObjects.removeIf(spaceObject -> {
            if (spaceObject.getY() > GamePanel.HEIGHT) {
                //points increase
                if (spaceObject instanceof Asteroid) {
                    GamePanel.increaseScore(35); 
                }

                return true;
            }
            return false; 
        });

        // Spawn new asteroids randomly
        spawnTimer += delta / 60.0; //converting delta to seconds
        
        //temporary difficulty over time implementation

        if (spawnTimer >= spawn_interval) {
            spawnspaceObject();
            
            if (difficulty < 10) {
                difficulty += 1;
                spawn_interval -= 0.05;
            }

            spawnTimer = 0; //reset timer
        }
    }

    private void spawnspaceObject() {
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
        int startY = -size; 

        // Create asteroids with their respective positions
        Asteroid asteroid1 = new Asteroid(lane1, x1, startY, size, size, difficulty, "src\\sprites\\asteroid_placeholder.png");
        Asteroid asteroid2 = new Asteroid(lane2, x2, startY, size, size, difficulty, "src\\sprites\\asteroid_placeholder.png");
        Asteroid asteroid3 = new Asteroid(lane3, x3, startY, size, size, difficulty, "src\\sprites\\asteroid_placeholder.png");

        //Create enemy type
        Enemy enemy1 = new Enemy(lane1, x1, startY, size, size, difficulty, 0, "src\\sprites\\enemy.png");

        
        

        //Different ways of spawning for each wave
        if (method == 1) {
            spaceObjects.add(asteroid1);
        }
        else if (method == 2) {
            spaceObjects.add(asteroid1);
            spaceObjects.add(asteroid2);
        }
        else if (method == 3) {
            int random2 = random.nextInt(2) + 1;
            
            if (random2 == 1) {
                spaceObjects.add(asteroid2);
                spaceObjects.add(asteroid3);
            } 
            else if (random2 == 2) {
                spaceObjects.add(enemy1);
                spaceObjects.add(asteroid2);
            }
        } else {
            return;
        }
        
    }

    //calculate X position based on lane number
    private int calculateLaneX(int lane) {
        return (lane - 1) * laneWidth + (laneWidth/2) - (size/2); //centered in lane
    }

    public List<SpaceObjects> getspaceObjects() {
        return spaceObjects;
    }

    public void draw(Graphics g) {
        for (SpaceObjects spaceObject: spaceObjects) {
            spaceObject.draw(g);
        }
    }

}