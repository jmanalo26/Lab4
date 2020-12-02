package main.gui.mazefront.actors;


import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import main.gui.mazefront.SpriteSheet;


abstract class Entity {

    private ImageView imageView;
    private final Group imageGroup;
    private final SpriteSheet spriteSheet;


    private double x;
    private double y;
    private double velX;
    private double velY;
    private double speed;

    private boolean isMovingRight, isMovingLeft, isMovingUp, isMovingDown, isDead, isHeadingRight = true, isHeadingLeft, isAttacking, isIdle = true;

    private double totalHealth;
    private double currentHealth;

    public Entity(SpriteSheet spriteSheet, double x, double y, double speed, double totalHealth) {
//        this.imageView = imageView;
        this.spriteSheet = spriteSheet;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.totalHealth = totalHealth;
        this.currentHealth = totalHealth;

        imageView = spriteSheet.getImageAt(0, 0);
        imageGroup = new Group(imageView);
        initiliateStartingPos();

    }

    public void initiliateStartingPos() {
        imageGroup.setTranslateY(y);
        imageGroup.setTranslateX(x);
    }

    public void walkRight() {
        imageGroup.setTranslateX(x += speed);
    }

    public void walkLeft() {
        imageGroup.setTranslateX(x -= speed);
    }

    public void walkUp() {
        imageGroup.setTranslateY(y -= speed);
    }

    public void walkDown() {
        imageGroup.setTranslateY(y += speed);
    }

//    public void attack() {
//
//    }
//
//    public void death() {
//
//    }

    public void setViewPort(int offset_x, int offset_y, int width, int height) {
        imageView.setViewport(new Rectangle2D(offset_x, offset_y, width, height));
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Group getImageGroup() {
        return imageGroup;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVelX() {
        return velX;
    }

    public void setVel(double velX, double velY) {
        this.velX = velX;
        this.velY = velY;
    }

    public void addVel(double velX, double velY) {
        this.velX += velX;
        this.velY += velY;
    }

    public double getVelY() {
        return velY;
    }

    public boolean isMovingRight() {
        return isMovingRight;
    }

    public void setMovingRight(boolean movingRight) {
        isMovingRight = movingRight;
    }

    public boolean isMovingLeft() {
        return isMovingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        isMovingLeft = movingLeft;
    }

    public boolean isMovingUp() {
        return isMovingUp;
    }

    public void setMovingUp(boolean movingUp) {
        isMovingUp = movingUp;
    }

    public boolean isMovingDown() {
        return isMovingDown;
    }

    public void setMovingDown(boolean movingDown) {
        isMovingDown = movingDown;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isHeadingRight() {
        return isHeadingRight;
    }

    public void setHeadingRight(boolean headingRight) {
        isHeadingRight = headingRight;
    }

    public boolean isHeadingLeft() {
        return isHeadingLeft;
    }

    public void setHeadingLeft(boolean headingLeft) {
        isHeadingLeft = headingLeft;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public boolean isIdle() {
        return isIdle;
    }

    public void setIdle(boolean idle) {
        isIdle = idle;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void damageHealth(double hp) {
        this.currentHealth -= hp;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    public double getTotalHealth() {
        return totalHealth;
    }

    public void setTotalHealth(double totalHealth) {
        this.totalHealth = totalHealth;
    }
    //    public void translateX(){
//
//    }
//
//    public void translateY()
}
