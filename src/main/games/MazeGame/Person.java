package main.games.MazeGame;

public class Person {

    private int posX;
    private int posY;
    public Person(int x, int y) {
        posX = x;
        posY = y;
    }
    public String getImage() {
        return "main/games/MazeGame/person.jpg";
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}