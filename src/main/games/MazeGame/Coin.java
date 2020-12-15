package main.games.MazeGame;

public class Coin {

    private int posX;
    private int posY;
    public Coin(int x, int y) {
        posX = x;
        posY = y;
    }
    public String getImage() {
        return "main/games/MazeGame/coin.png";
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
