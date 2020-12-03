package main.games.MazeGame;

public class Obstaclelvl3 {

    private int posX;
    private int posY;
    public Obstaclelvl3(int x, int y) {
        posX = x;
        posY = y;
    }
    public String getImage() {
        return "main/games/MazeGame/treelvl3.png";
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
