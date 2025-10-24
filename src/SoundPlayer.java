package src;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer { 
    
    public static void playSound(String filePath) {
        try { //try to play sound effects
            File soundFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Play the sound
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}