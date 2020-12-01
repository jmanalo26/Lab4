package main.games.MazeGame;

public class Zombie {

    private int posX;
    private int posY;

    public Zombie(int x, int y) {
        posX = x;
        posY = y;
    }
    public String getImage() {
        return "main/games/MazeGame/zombie.jpg";
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
