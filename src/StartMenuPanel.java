package src;

import javax.swing.*;
import java.awt.*;

public class StartMenuPanel extends JPanel {

    public StartMenuPanel(Runnable startGameAction) {
        // vertical stack layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(GamePanel.WIDTH, GamePanel.HEIGHT));
        setBackground(Color.BLACK);

        // push content to exact center vertically
        add(Box.createVerticalGlue());

        // --- Title ---
        JLabel title = new JLabel("SPACE GAME");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Monospaced", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        add(title);

        // space between title and button
        add(Box.createRigidArea(new Dimension(0, 40)));

        // --- Start Button ---
        JButton startButton = new JButton("START");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setFont(new Font("Monospaced", Font.BOLD, 24));
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> startGameAction.run());
        add(startButton);

        // keep everything centered
        add(Box.createVerticalGlue());
    }
}
