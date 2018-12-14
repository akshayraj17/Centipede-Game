public class Missile extends Sprite {

    private final int BOARD_H = 0;
    private final int MISSILE_SPEED = 6;

    public Missile(int x, int y) {
        super(x, y);

        initMissile();
    }

    private void initMissile() {

        loadImage("src/img/missile.jpg");
        getImageDimensions();
    }

    public void move() {

        y -= MISSILE_SPEED;
        if (y < BOARD_H) {
            visible = false;
        }
    }
}
