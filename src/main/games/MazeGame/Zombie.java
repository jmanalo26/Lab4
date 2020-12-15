package main.games.MazeGame;

public class Zombie {

    private int posX;
    private int posY;
    private String imageZombie = "resources/images/spritesheet/zombie_lvl_1.png";

    public Zombie(int x, int y) {
        posX = x;
        posY = y;
    }
    public String getImage() {
        return imageZombie;
    }

    public void setImage(String image) {
         imageZombie = image;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
