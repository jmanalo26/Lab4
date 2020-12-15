package main.games.MazeGame;

public class Box {

    private int posX;
    private int posY;
    public Box(int x, int y) {
        posX = x;
        posY = y;
    }
    public String getImage() {
        return "main/games/MazeGame/box.png";
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}


