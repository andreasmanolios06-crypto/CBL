package src;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //Drawing the background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //Drawing the stars
        g.setColor(Color.WHITE);
        for (int i = 0; i < 100; i++) {
            int x = (int)(Math.random() * WIDTH);
            int y = (int)(Math.random() * HEIGHT);
            g.fillRect(x, y, 2, 2);
        }

    }
    
}
