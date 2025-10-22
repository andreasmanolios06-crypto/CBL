package src;

public class Asteroid extends SpaceObjects {
    private int lane; //determine which lane it will be loaded in

    //Constructor to initialize player position and size
    public Asteroid(int lane, int startX, int startY, int width, int height, int velocity, String spritePath) {
        //Initialize asteroid position and sizes
        super(startX, startY, width, height, velocity, spritePath);
        
    }

    @Override
    public void update (double delta) {
        fall(delta);
    }
    
    private void fall(double delta) {
        y += velocity * delta;
    }
}
