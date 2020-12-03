package main.games.MazeGame;

public class Zombielvl2 {

    private int posX;
    private int posY;
    private String imageZombie = "main/games/MazeGame/zombielvl2.png";

    public Zombielvl2(int x, int y) {
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
}
