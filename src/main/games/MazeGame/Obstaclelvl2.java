package main.games.MazeGame;

public class Obstaclelvl2 {

    private int posX;
    private int posY;
    public Obstaclelvl2(int x, int y) {
        posX = x;
        posY = y;
    }
    public String getImage() {
        return "main/games/MazeGame/treelvl2.png";
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
