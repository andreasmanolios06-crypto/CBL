import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;

public class Game {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Demo"); //creating a frame (new window)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit application when window is closed
        frame.setLayout(null); //using no layout managers

        JButton button = new JButton("test"); //creating a button
        button.setBounds(300,275,200,50); //setting position and size of button

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Button clicked!"); //showing a message dialog when button is clicked
            }
        });

        frame.add(button); //adding button to frame

        frame.setSize(800,600); //setting size of frame
        frame.setVisible(true); //making frame visible

    }
}
