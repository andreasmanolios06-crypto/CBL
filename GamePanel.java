package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class GamePanel extends JPanel implements ActionListener {
    //constants defining the game panel size
    public static final int WIDTH = 600; 
    public static final int HEIGHT = 800;

    //Game state variables
    private Timer gameTimer; 
    private int[] starX = new int[100]; //X positions of 100 stars
    private int[] starY = new int[100]; //Y positions of 100 stars
    private int[] starSpeed = new int[100]; //Speed of each star

    //keyhandler declaration
    KeyHandler keyHandler = new KeyHandler();

    //player instance
    private Player player = new Player(
        WIDTH/2 - 32, 
        HEIGHT - 100, 
        64, 
        64, 
        7, 
        "src/sprites/swordfish_sprite.png");



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

        //Create game timer (60 FPS) (1000ms/60 = ~16.67ms per frame)
        gameTimer = new Timer(1000/60, this);  
        gameTimer.start(); //Begin playing animation
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateGame();
        repaint(); //screen refresh
    }        
        
    private void updateGame() {
        //Update star positions (moving down the screen)
        for (int i = 0; i < starY.length; i++) {
            starY[i] += starSpeed[i];
            
            //when a star goes off screen, reset it to the top
            if (starY[i] > HEIGHT) {
                starY[i] = 0;
                starX[i] = (int)(Math.random() * WIDTH);
                starSpeed[i] = 1 + (int)(Math.random() * 3);
            }
        }

        //player movement
        player.update(keyHandler);
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
        player.draw(g);

    }


}

