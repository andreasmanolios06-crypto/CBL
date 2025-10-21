package src;

import javax.swing.*;
import java.awt.*;  // for CardLayout

public class Main {
    public static void main(String[] args) {
        // Create the main window
        JFrame frame = new JFrame("Space Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // We’ll swap between MENU and GAME using a CardLayout
        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);

        // Create the game panel
        GamePanel gamePanel = new GamePanel();

        // Create the start menu; when the button is clicked, show the game
        StartMenuPanel startMenu = new StartMenuPanel(new Runnable() {
            @Override
            public void run() {
                cardLayout.show(container, "GAME");
                
                gamePanel.startGame();

                // make sure the game panel gets keyboard focus right after showing
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        gamePanel.requestFocusInWindow();
                    }
                });

            }
        });

        // Add both screens to the container
        container.add(startMenu, "MENU");
        container.add(gamePanel, "GAME");

        // Put the container in the window and show it
        frame.setContentPane(container);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Start on the menu screen
        cardLayout.show(container, "MENU");
    }
}
