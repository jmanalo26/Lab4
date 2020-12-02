package main.games.shooter;

public class Player {

    /**
     * X coordinate
     */
    private double x;
    /**
     * Y coordinate
     */
    private double y;
    /**
     * Player HP
     */
    private int HP;
    /**
     * Player Ammo
     */
    private int ammo;

    /**
     * Class constructor
     * @param x coordinate
     * @param y coordinate
     * @param HP Health
     * @param ammo Ammo
     */
    public Player(double x, double y, int HP, int ammo){
        this.HP = HP;
        this.ammo = ammo;
    }

    //Below are all getters and setters

    /**
     * Get X
     * @return X
     */
    public double getX() {
        return x;
    }

    /**
     * Setter for X
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Getter for Y
     * @return Y
     */
    public double getY() {
        return y;
    }

    /**
     * Setter for Y
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Get HP for Player
     * @return HP
     */
    public int getHP() {
        return HP;
    }

    /**
     * Setter for HP
     * @param HP
     */
    public void setHP(int HP) {
        this.HP = HP;
    }

    /**
     * Getter for Player Ammo
     * @return Ammo
     */
    public int getAmmo() {
        return ammo;
    }

    /**
     * Setter for Ammo
     * @param ammo
     */
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
}
