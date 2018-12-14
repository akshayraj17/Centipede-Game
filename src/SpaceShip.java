import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class SpaceShip extends Sprite {

    private int dx;
    private int dy;
    private List<Missile> missiles;

    public SpaceShip(int x, int y) {
        super(x, y);

        initSpaceShip();
    }

    private void initSpaceShip() {

        missiles = new ArrayList<>();

        loadImage("src/img/craft.jpg");
        getImageDimensions();
    }

    public void move() {
        x = dx;
        y = dy;
    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    public void mousePressed(MouseEvent e)
    {
        fire();
        System.out.println("pressed");
    }

    public void mouseEntered(MouseEvent e)
    {
        if(e.getY() > 525 && e.getY() < 560) {
            dy = e.getY();
            dx = e.getX();
        }
        System.out.println("entered");

    }
    public void mouseExited(MouseEvent e)
    {
        //dx = e.getX();
        //dy = e.getY();
        System.out.println("exited");

    }
    // override MML two abstract methods
    public void mouseMoved(MouseEvent e) {
        if(e.getY() > 525 && e.getY() < 560) {
            dy = e.getY();
            dx = e.getX();
        }
    }

    public void fire() {
        missiles.add(new Missile(x + width, y + height / 2));
    }


}