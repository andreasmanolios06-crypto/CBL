package src;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean leftPressed, rightPressed;


    public void keyTyped(KeyEvent e) {
        //required by keylistener
        //mehtod for character input (typing)
    }

    @Override

    /* 
     * Detects what keys are being pressed/typed and sets the 
     * corresponding booleans to true if they are.
     */
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
 
        if (code == KeyEvent.VK_A|| code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
    }

    /* 
     * Detects what keys have been released and sets the 
     * corresponding booleans to false if they are.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode(); 

        if (code == KeyEvent.VK_A|| code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }
}
