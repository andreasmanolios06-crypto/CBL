package src;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {
    
    public GameOverPanel(Runnable restartGameAction, int score) {
        // vertical stack layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(GamePanel.WIDTH, GamePanel.HEIGHT));
        setBackground(Color.BLACK);

        // push content to exact center vertically
        add(Box.createVerticalGlue());

        //title
        JLabel title = new JLabel("GAME OVER");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Monospaced", Font.BOLD, 48));
        title.setForeground(Color.WHITE); // Changed from RED to WHITE to match start screen
        add(title);

        // space between title and score
        add(Box.createRigidArea(new Dimension(0, 30)));

        // score display
        JLabel scoreLabel = new JLabel("FINAL SCORE: " + score);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 30));
        scoreLabel.setForeground(Color.WHITE);
        add(scoreLabel);

        // space between score and button
        add(Box.createRigidArea(new Dimension(0, 40)));

        // restart button
        JButton restartButton = new JButton("PLAY AGAIN");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setFont(new Font("Monospaced", Font.BOLD, 24));
        restartButton.setFocusPainted(false);
        restartButton.addActionListener(e -> restartGameAction.run());
        add(restartButton);

        // keep everything centered
        add(Box.createVerticalGlue());
    }
}