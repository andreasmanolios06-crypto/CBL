package src;

import javax.swing.*;
import java.awt.*;  // for CardLayout

public class Main {
    public static void main(String[] args) {
        //preloading sounds to avoid frame drops
        SoundPlayer.playSound("src\\sounds\\laserShoot.wav");
        SoundPlayer.playSound("src\\sounds\\explosion.wav");
        SoundPlayer.playSound("src\\sounds\\hitHurt.wav");
        SoundPlayer.playSound("src\\sounds\\random.wav");
        SoundPlayer.playSound("src\\sounds\\enemylaserShoot.wav");
        SoundPlayer.playSound("src\\sounds\\playerDamage.wav");

        // Create the main window
        JFrame frame = new JFrame("ASTEROID BLUES");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        //swap between MENU and GAME using a CardLayout
        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);

        //creating game panel
        GamePanel gamePanel = new GamePanel(frame);


        //create start menu
        StartMenuPanel startMenu = new StartMenuPanel(new Runnable() {
            @Override
            public void run() {
                cardLayout.show(container, "GAME");
                
                gamePanel.startGame();

                // gets keyboard focus
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        gamePanel.requestFocusInWindow();
                    }
                });

            }
        });

        

        //both screens to the container
        container.add(startMenu, "MENU");
        container.add(gamePanel, "GAME");

        //put the container in the window and show it
        frame.setContentPane(container);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //start on the menu screen
        cardLayout.show(container, "MENU");
    }
}
