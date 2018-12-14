import javax.swing.plaf.synth.SynthMenuBarUI;
import java.util.Random;

public class Spiders extends Sprite {

    public int health = 2;

    public Spiders(int x, int y) {
        super(x, y);

        initSpider();
    }

    private void initSpider() {

        loadImage("src/img/spider.jpg");
        getImageDimensions();
    }

    public void move() {
        Random randomNum = new Random();
        int options = 0 + randomNum.nextInt(4);
        if(options == 0) {
            if (x < 550) {
                x += 10;
            }
            if (y < 550) {
                y += 5;
            }
        }
        else if (options == 1)
        {
            if (x < 550) {
                x += 10;
            }
            if (y > 30) {
                y -= 5;
            }
        }
        else if (options == 2)
        {
            if (x > 30) {
                x -= 10;
            }
            if (y < 550) {
                y += 5;
            }
        }
        else if (options == 3)
        {
            if (x > 30) {
                x -= 10;
            }
            if (y > 30) {
                y -= 5;
            }
        }
    }

}

