package main.games.shooter.LBossLevel2;

public class Boss2 {
    private int totalHealth = 50;
    private int halfHealth;
    private int currentHealth;
    private int attackDamage = 1;
    private int bossPhase = 1;
    private boolean beamAttack = false;

    private int movementCordGoal = 280;
    private int xCord;
    private int yCord;


    public Boss2(){
        currentHealth = totalHealth;
        halfHealth = totalHealth/2;
    }

    /**
     *
     * @param direction
     */
    public void movementGoal(int direction){
        if (direction == 1){
            movementCordGoal = 80;
        }
        if (direction == 2){
            movementCordGoal = 180;
        }
        if (direction == 3){
            movementCordGoal = 280;
        }
        if (direction == 4){
            movementCordGoal = 370;
        }
        if (direction == 5){
            movementCordGoal = 460;
        }
        if (direction == 6){
            beamAttack = true;
        }
    }

    public int getMovementGoal(){
        return movementCordGoal;
    }

    /**
     * Returns bosses current health
     * @return
     */
    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * Returns bosses current attackDamage
     * @return
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Returns bosses current phase
     * @return
     */
    public int getBossPhase(){
        return bossPhase;
    }

    /**
     * Returns total health of the boss
     * @return
     */
    public int getTotalHealth() {
        return totalHealth;
    }

    /**
     * Returns half health of the boss
     * @return
     */
    public int getHalfHealth(){
        return halfHealth;
    }

    /**
     * Sets bosses current health
     * @param currentHealth
     */
    public void setHealth(int currentHealth){
        this.currentHealth = currentHealth;
    }

    /**
     * Sets bosses current attackDamage
     * @param attackDamage
     */
    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * Sets bosses current phase
     * @param bossPhase
     */
    public void setBossPhase(int bossPhase){
        this.bossPhase = bossPhase;
    }

    /**
     * Sets bosses total health
     * @param totalHealth
     */
    public void setTotalHealth(int totalHealth){
        this.totalHealth = totalHealth;
    }

}
