package main.games.shooter.LBossLevel3;

public class Obstacle3 {

    private int movementCordGoal = 590;
    private int movementCordGoal2 = 10;

    public Obstacle3(){
    }

    public void movementGoal(int direction){
        if (direction == 1){
            movementCordGoal = 10;
            movementCordGoal2 = 590;
        }
        if (direction == 2){
            movementCordGoal = 510;
            movementCordGoal2 = 10;
        }
    }

    public int getMovementGoal(){
        return movementCordGoal;
    }

    public int getMovementGoal2(){
        return movementCordGoal2;
    }

}
