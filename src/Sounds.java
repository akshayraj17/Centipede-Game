import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class Sounds extends JFrame {
    Clip clip;

    public Sounds() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Test Sound Clip");
        this.setSize(300, 200);
        this.setVisible(true);
        try {
            File soundFile = new File("src/shoot_sound.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void stopSound(){
        clip.stop();
    }

}