package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class GamePanel extends JPanel implements ActionListener {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 800;

    private Timer gameTimer; 
    private int[] starX = new int[100];
    private int[] starY = new int[100];
    private int[] starSpeed = new int[400];

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);

        //Initializing the stars positions and speeds
        for (int i = 0; i < starX.length; i++) {
            starX[i] = (int)(Math.random() * WIDTH);
            starY[i] = (int)(Math.random() * HEIGHT);
            starSpeed[i] = 1 + (int)(Math.random() * 3);
        }

        //Create game timer (60 FPS)

        gameTimer = new Timer(1000/60, this);
        gameTimer.start(); //Begin playing animation
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateGame();
        repaint(); //screen refresh
    }        
        
    private void updateGame() {
        //Update star positions
        for (int i = 0; i < starY.length; i++) {
            starY[i] += starSpeed[i];
            
            //when a star goes off screen, reset it to the top
            if (starY[i] > HEIGHT) {
                starY[i] = 0;
                starX[i] = (int)(Math.random() * WIDTH);
                starSpeed[i] = 1 + (int)(Math.random() * 3);
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

        //Future game drawing code goes here
    }
}

