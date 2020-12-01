package main.games.MazeGame;

import java.util.Random;

public class Perk {
    private int x;
    private int y;
    private String[] perkArr = {"FV", "RZ", "MZ"};

    public Perk(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getRandomPerk() {
        Random r = new Random();
        int random = r.nextInt(perkArr.length);
        return perkArr[random];
    }

    public String getImage() {
        return "main/games/MazeGame/perks.png";
    }


}
