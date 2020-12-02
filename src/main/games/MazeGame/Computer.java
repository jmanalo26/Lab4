package main.games.MazeGame;

public class Computer {

    private int posX;
    private int posY;
    public Computer(int x, int y) {
        posX = x;
        posY = y;
    }
    public String getImage() {
        return "main/games/MazeGame/computer.png";
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}

