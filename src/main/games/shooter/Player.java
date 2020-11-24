package main.games.shooter;

public class Player {

    private double x;
    private double y;
    private int HP;
    private int ammo;

    public Player(double x, double y, int HP, int ammo){
        this.HP = HP;
        this.ammo = ammo;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
}
