package main.games.MazeGame;

public class Player {
    private int posX;
    private int posY;
    public Player(int x, int y) {
        posX = x;
        posY = y;
    }
    public String getImage() {
        return "main/games/MazeGame/sprite1.png";
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
