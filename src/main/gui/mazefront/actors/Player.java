package main.gui.mazefront.actors;

import javafx.util.Duration;
import main.gui.mazefront.SpriteAnimation;
import main.gui.mazefront.SpriteSheet;


public class Player extends Entity {

    private int COLUMNS = 10;
    private int COUNT = 10;
    private int OFFSET_X = 15;
    private int OFFSET_Y = 8;
    private int WIDTH = 24;
    private int HEIGHT = 33;

    private SpriteAnimation playerAnimation;
//    new SpriteAnimation(
//            imageView,
//            Duration.millis(1500),
//            COUNT, COLUMNS,
//            OFFSET_X, OFFSET_Y,
//            WIDTH, HEIGHT
//    );

    public Player(SpriteSheet spriteSheet, double x, double y, double speed, double totalHealth) {
        super(spriteSheet, x, y, speed, totalHealth);
        playerAnimation = new SpriteAnimation(
                spriteSheet.getImageAt(0, 0),
                Duration.millis(1500),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
    }

    @Override
    public void walkRight() {
        super.walkRight();
        COLUMNS = 15;
        COUNT = 15;
        OFFSET_X = 14;
        OFFSET_Y = 151;
        setAnimationsParameters();
    }

    //
    @Override
    public void walkLeft() {
        super.walkLeft();
        COLUMNS = 15;
        COUNT = 15;
        OFFSET_X = 10;
        OFFSET_Y = 103;
        setAnimationsParameters();
    }

    //
    public void walkUpHeadingLeft() {
        super.walkUp();
        COLUMNS = 15;
        COUNT = 15;
        OFFSET_X = 10;
        OFFSET_Y = 103;
        setAnimationsParameters();
    }

    public void walkDownHeadingLeft() {
        super.walkDown();
        COLUMNS = 15;
        COUNT = 15;
        OFFSET_X = 10;
        OFFSET_Y = 103;
        setAnimationsParameters();
    }

    public void walkUpHeadingRight() {
        super.walkUp();
        COLUMNS = 15;
        COUNT = 15;
        OFFSET_X = 14;
        OFFSET_Y = 151;
        setAnimationsParameters();
    }

    public void walkDownHeadingRight() {
        super.walkDown();
        COLUMNS = 15;
        COUNT = 15;
        OFFSET_X = 14;
        OFFSET_Y = 151;
        setAnimationsParameters();
    }

    public void idleHeadingRight() {
        COLUMNS = 10;
        COUNT = 10;
        OFFSET_X = 9;
        OFFSET_Y = 56;
        WIDTH = 24;
        HEIGHT = 33;
        setAnimationsParameters();
    }

    public void idleHeadingLeft() {
        COLUMNS = 10;
        COUNT = 10;
        OFFSET_X = 15;
        OFFSET_Y = 8;
        WIDTH = 24;
        HEIGHT = 33;
        setAnimationsParameters();
    }

    private void setAnimationsParameters() {
        playerAnimation.setColumns(COLUMNS);
        playerAnimation.setCount(COUNT);
        playerAnimation.setOffsetX(OFFSET_X);
        playerAnimation.setOffsetY(OFFSET_Y);
        playerAnimation.setWidth(WIDTH);
        playerAnimation.setHeight(HEIGHT);
    }

    public void deadAnimation() {
        playerAnimation.stop();
        if (super.isHeadingLeft()) {
            COLUMNS = 8;
            COUNT = 8;
            OFFSET_X = 15;
            OFFSET_Y = 297;
            WIDTH = 21;
            HEIGHT = 31;
        } else {
            COLUMNS = 8;
            COUNT = 8;
            OFFSET_X = 12;
            OFFSET_Y = 345;
            WIDTH = 22;
            HEIGHT = 31;
        }
        playerAnimation.stop();
        setPlayerAnimation(new SpriteAnimation(
                super.getSpriteSheet().getImageAt(0, 0),
                Duration.millis(1500),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        ));
    }

    public SpriteAnimation getPlayerAnimation() {
        return playerAnimation;
    }

    public void setPlayerAnimation(SpriteAnimation playerAnimation) {
        this.playerAnimation = playerAnimation;
    }

    //
//    public void walkDown() {
//
//    }
}
