

public class Mushrooms extends Sprite {

    public int health = 3;

    public Mushrooms(int x, int y) {
        super(x, y);

        initMushroom();
    }

    private void initMushroom() {

        loadImage("src/img/mushroom.png");
        getImageDimensions();
    }


}
