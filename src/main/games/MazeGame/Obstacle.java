package main.games.MazeGame;

public class Obstacle {

    private int posX;
    private int posY;
    public Obstacle(int x, int y) {
        posX = x;
        posY = y;
    }
    public String getImage() {
        return "main/games/MazeGame/obstacle.png";
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
