public class Centipede extends Sprite {


    public int health = 3;
    public int direction = 0;
    public int cflag = 0;
    public int dflag = 0;

    public Centipede(int x, int y) {
        super(x, y);

        initCentipede();
    }

    private void initCentipede() {

        loadImage("src/img/centipede.jpg");
        getImageDimensions();
    }

    public void move() {

        if(cflag == 0){
            if (direction == 1) {
            if (x < 575) {
                x += 2;
            } else {
                direction = 0;
                cflag = 1;
            }
            }
            else {
                if (x > 1) {
                    x -= 2;
                } else {
                    direction = 1;
                    cflag = 1;

                }
            }
        }
        else {
            if (dflag == 1) {
                if (y < 550) {
                    y += 25;
                    cflag = 0;
                } else {
                    dflag = 0;
                }
            } else {
                if (y > 1) {
                    y -= 25;
                    cflag = 0;
                } else {
                    dflag = 1;
                }
            }
        }
    }


    public void moveDown() {
        if (dflag == 1) {
            if (y < 550) {
                y += 25;
            } else {
                dflag = 0;
            }
        } else {
            if (y > 1) {
                y -= 25;
            } else {
                dflag = 1;
            }
        }

    }
}

