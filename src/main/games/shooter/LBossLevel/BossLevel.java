package main.games.shooter.LBossLevel;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.games.shooter.Player;
import main.gui.gameovermenu.GameOverMenu;
import main.gui.gamewonmenu.GameWonMenu;


import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class BossLevel extends Application {

    private Pane root = new Pane();

    private BossLevel.Entity playerEntity = new BossLevel.Entity(275, 600, 20, 20, "player", Color.BLUE);

    private  Entity bossEnemy = new Entity(280, 120, 60, 60, "enemy", Color.RED);

    private Entity HPBar;

    private Entity BossHPBar;

    private Entity BossName;

    private Player player;

    private static Entity[] bullets = new Entity[6];

    private double time = 0;

    private Boss bigBoss = new Boss();

    private Obstacle obstacle  = new Obstacle();

    private Obstacle obstacle2  = new Obstacle();


    boolean yGoal = false;


    private static AtomicInteger enemyDead = new AtomicInteger();

    private static Stage stage;

    public AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            update();
        }
    };

    /**
     * Create pane and add player, enemies, and bullets
     *
     * @return Shooting game
     */
    private Parent createRoot() {
        playerEntity.setId("player");
        Image playerImage = new Image(getClass().getResource("images/player.png").toExternalForm());
        ImagePattern i = new ImagePattern(playerImage);
        playerEntity.setFill(i);
        root.setPrefSize(600, 775);
        root.getChildren().add(playerEntity);
        player = new Player(playerEntity.getX(), playerEntity.getY(), 5, 6);
        //Add animation timer to animate bullet
        timer.start();
        addBossName();
        addBossHealthBar();
        addHealthBar();
        displayAmmo();
        addObstacles();
        root.setId("pane");
        addBoss();
        return root;
    }

    /**
     * Creates a list of entities to help with updates
     *
     * @return List of entities
     */
    private List<Entity> entities() {
        return root.getChildren().stream().map(n -> (Entity) n).collect(Collectors.toList());
    }

    /**
     * Adds the single boss enemy
     */
    private void addBoss() {
        Image enemyImage = new Image(getClass().getResource("images/LBossPhase1.png").toExternalForm());
        ImagePattern en = new ImagePattern(enemyImage);
        bossEnemy.setFill(en);
        root.getChildren().add(bossEnemy);
    }

    /**
     * Adds random obstacles to game
     */
    private void addObstacles(){
        Image obsImage = new Image(getClass().getResource("images/dumpster.png").toExternalForm());
        ImagePattern obs = new ImagePattern(obsImage);

        Entity obstacle = new Entity(260, 300, 40, 30, "obstacle", Color.PURPLE);
        obstacle.setId("obstacle1");
        obstacle.setFill(obs);
        root.getChildren().add(obstacle);


        Entity obstacle2 = new Entity(260, 450, 40, 30, "obstacle2", Color.PURPLE);
        obstacle2.setId("obstacle2");
        obstacle2.setFill(obs);
        root.getChildren().add(obstacle2);

    }

    /**
     * Displays ammo count
     */
    private void displayAmmo() {
        for (int i = 0; i < player.getAmmo(); i++) {
            Entity ammo = new Entity(400 + (20 * i), 730, 3, 20, "ammo" + i, Color.BLACK);
            bullets[i] = ammo;
            root.getChildren().add(ammo);
        }
    }

    /**
     * Depletes ammo when the player fires
     */
    private void depleteAmmo() {
        if (player.getAmmo() < 6) {
            int ammoUsed = 5 - player.getAmmo();
            bullets[ammoUsed].dead = true;
        }
    }

    /**
     * Ammo reloads for player
     */
    private void addAmmo() {
        if (player.getAmmo() < 6) {
            int ammoUsed = 6 - player.getAmmo();
            for (int i = 0; i < ammoUsed; i++) {
                Entity ammo = new Entity(400 + (20 * i), 730, 3, 20, "ammo" + i, Color.BLACK);
                bullets[i] = ammo;
                root.getChildren().add(ammo);
            }
        }
    }

    /**
     * Creates a health bar entity at the bottom of the game window
     */
    private void addHealthBar() {
        HPBar = new Entity(10, 730, 200, 15, "HP", Color.RED);
        Entity HPBorder = new Entity(0, 700, 1000, 100, "outline", Color.BLACK);
        HPBorder.setOpacity(0.3);
        root.getChildren().addAll(HPBar, HPBorder);
        //root.getChildren().add(HPBar);
    }

    /**
     * Method to de-increment Player HP, player is dead if 0
     */
    private void deIncrementHP() {
        player.setHP(player.getHP() - 1);
        //System.out.println("Player Hit; " + player.getHP() + " HP remaining");
        HPBar.setWidth(HPBar.getWidth() - 40);
        if (player.getHP() <= 0) {
            playerEntity.dead = true;
            System.out.println("Player has died");
            stage.close();
            timer.stop();
            GameOverMenu gameOverMenu = new GameOverMenu();
            Stage gameOverMenuStage = new Stage();
            try {
                gameOverMenu.start(gameOverMenuStage);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a health bar entity at the bottom of the game window
     */
    private void addBossName() {
        Image obsImage = new Image(getClass().getResource("images/MechaZombieName.png").toExternalForm());
        ImagePattern obs = new ImagePattern(obsImage);

        BossName = new Entity(55, 15, 490, 40, "Name", Color.RED);
        BossName.setId("Name");
        BossName.setFill(obs);
        root.getChildren().addAll(BossName);
    }

    /**
     * Creates a health bar entity at the bottom of the game window
     */
    private void addBossHealthBar() {
        BossHPBar = new Entity(45, 65, 510, 15, "HP", Color.RED);
        Entity HPBorder = new Entity(0,  0, 1000, 100, "outline", Color.BLACK);
        HPBorder.setOpacity(0);
        root.getChildren().addAll(BossHPBar, HPBorder);
        //root.getChildren().add(HPBar);
    }

    /**
     * Method to de-increment Player HP, player is dead if 0
     */
    private void deIncrementBossHP() {
        BossHPBar.setWidth(BossHPBar.getWidth() - 10);
    }

    /**
     * Method to restore player HP when picking up health item
     */
    private void incrementHP() {
        player.setHP(player.getHP() + 1);
        HPBar.setWidth(HPBar.getWidth() + 40);
    }

    /**
     * Create bullet entity objects
     *
     * @param entity Bullet
     */
    private void createBullets(Entity entity) {
        Entity s = new Entity((int) entity.getTranslateX() + 20, (int) entity.getTranslateY(), 3, 20, entity.type + "bullet", Color.BLACK);
        root.getChildren().add(s);
    }


    /**
     * Start method for shooting game
     *
     * @param primaryStage Stage object for shooting game
     */
    @Override
    public void start(Stage primaryStage) {
        enemyDead.set(0);
        Scene scene = new Scene(createRoot());
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                    playerEntity.moveLeft();
                    player.setX(playerEntity.getTranslateX());
                    break;

                case D:
                    playerEntity.moveRight();
                    player.setX(playerEntity.getTranslateX());
                    break;

                case SPACE:
                    if (player.getAmmo() > 0) {
                        createBullets(playerEntity);
                        player.setAmmo(player.getAmmo() - 1);
                        //System.out.println("Current Ammo: " + player.getAmmo());
                        depleteAmmo();
                    }
                    break;
                case R:
                    addAmmo();
                    player.setAmmo(6);
                    //System.out.println("Player Reloaded; Current Ammo: " + player.getAmmo());
            }
        });

        stage = primaryStage;
        stage.setTitle("Project Z");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Main method to start application
     *
     * @param args Main arguments
     */
    public static void main(String[] args) {
        launch(args);
        //MainApplication.launch(ShootingGame.class, args);
    }

    /**
     * Class object for entity
     */
    private static class Entity extends Rectangle {
        boolean dead = false;
        final String type;

        Entity(int x, int y, int width, int height, String type, Color color) {
            super(width, height, color);
            this.type = type;
            setTranslateX(x);
            setTranslateY(y);
        }

        void moveUp() {
            setTranslateY(getTranslateY() - 7);
        }

        void moveDown() {
            setTranslateY(getTranslateY() + 3);
        }

        void moveLeft() {
            setTranslateX(getTranslateX() - 10);
        }

        void moveRight() {
            setTranslateX(getTranslateX() + 10);
        }

        void slowMoveLeft() {
            setTranslateX(getTranslateX() - 2);
        }

        void slowMoveRight() {
            setTranslateX(getTranslateX() + 2);
        }


        void movieDiagonalRightDown() {
            setTranslateX(getTranslateX() + 1);
            setTranslateY(getTranslateY() + 4);
        }

        void moveDiagonalLeftDown() {
            setTranslateX(getTranslateX() - 1);
            setTranslateY(getTranslateY() + 4);
        }

        void jumpLeft() {
            setTranslateX(80);
        }

        void jumpMid() {
            setTranslateX(280);
        }

        void jumpRight() {
            setTranslateX(460);
        }

        void jumpUp() {
            setTranslateY(120);
        }

    }

    /**
     * Player Attack
     */

    /**
     * Create bullet entity objects
     *
     * @param entity Bullet
     */
    private void playerShoot(BossLevel.Entity entity) {
        BossLevel.Entity s = new BossLevel.Entity((int) entity.getTranslateX() + 10, (int) entity.getTranslateY(), 3, 20, entity.type + "bullet", Color.BLACK);
        root.getChildren().add(s);
    }

    /**
     * Enemy Attacks
     */

    /**
     * Create bullet entity objects
     *
     * @param entity Bullet
     */
    private void bossShoot(BossLevel.Entity entity) {
        BossLevel.Entity s = new BossLevel.Entity((int) entity.getTranslateX() + 50, (int) entity.getTranslateY() + 40, 3, 20, entity.type + "bullet", Color.RED);
        root.getChildren().add(s);
    }

    /**
     * Create trishot bullet entity objects
     *
     * @param entity Bullet
     */
    private void triShot(BossLevel.Entity entity) {
        BossLevel.Entity s1 = new BossLevel.Entity((int) entity.getTranslateX() + 20, (int) entity.getTranslateY() + 20, 5, 5, entity.type + "tribulletL", Color.RED);
        root.getChildren().add(s1);
        BossLevel.Entity s2 = new BossLevel.Entity((int) entity.getTranslateX() + 30, (int) entity.getTranslateY() + 20, 5, 5, entity.type + "bullet", Color.RED);
        root.getChildren().add(s2);
        BossLevel.Entity s3 = new BossLevel.Entity((int) entity.getTranslateX() + 40, (int) entity.getTranslateY() + 20, 5, 5, entity.type + "tribulletR", Color.RED);
        root.getChildren().add(s3);

    }

    /**
     * Create bullet entity objects
     *
     * @param entity Bullet
     */
    private void throwBeam(BossLevel.Entity entity) {
        BossLevel.Entity s = new BossLevel.Entity((int) entity.getTranslateX() + -20, (int) entity.getTranslateY(), 80, 10, entity.type + "bullet", Color.BLACK);
        root.getChildren().add(s);
    }

    /**
     * dd
     * Update method, has functionality for bullet hits, enemy movement, entity deaths
     */
    private void update() {
        time += 0.015;
        entities().forEach(s -> {
            switch (s.type) {
                //Simulate Enemy movement and shooting
                case "enemy":
                    if (time > 1) {
                        if (Math.random() < 0.05) {
                            if (bigBoss.getBossPhase() == 1) {
                                if (bigBoss.getMovementGoal() == (int) s.getTranslateX()) {
                                    ThreadLocalRandom random = ThreadLocalRandom.current();
                                    int moveGoal = (random.nextInt(1, 6));
                                    System.out.println(moveGoal);
                                    bossShoot(s);
                                    bigBoss.movementGoal(moveGoal);
                                } else if (bigBoss.getMovementGoal() < (int) s.getTranslateX()) {
                                    s.moveLeft();
                                    bossShoot(s);
                                } else if (bigBoss.getMovementGoal() > (int) s.getTranslateX()) {
                                    s.moveRight();
                                    bossShoot(s);
                                }
                            } else {
                                if (Math.random() < 0.10) {
                                    yGoal = true;
                                } else {
                                    if (yGoal == true && ((int) s.getTranslateY() < 300)) {
                                        s.moveDown();
                                    }
                                    if (yGoal == true && (int) s.getTranslateY() == 300) {
                                        double tempX = s.getTranslateX();
                                        s.jumpUp();
                                        s.jumpLeft();
                                        throwBeam(s);
                                        s.jumpMid();
                                        throwBeam(s);
                                        s.jumpRight();
                                        throwBeam(s);
                                        s.setTranslateX(tempX);
                                        yGoal = false;
                                    } else {
                                        if (bigBoss.getMovementGoal() == (int) s.getTranslateX()) {
                                            ThreadLocalRandom random = ThreadLocalRandom.current();
                                            int moveGoal = (random.nextInt(1, 6));
                                            System.out.println(moveGoal);
                                            triShot(s);
                                            bigBoss.movementGoal(moveGoal);
                                        } else if (bigBoss.getMovementGoal() < (int) s.getTranslateX()) {
                                            s.moveLeft();
                                            triShot(s);
                                        } else if (bigBoss.getMovementGoal() > (int) s.getTranslateX()) {
                                            s.moveRight();
                                            triShot(s);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;

                //moving obstacle 1
                case "obstacle":
                    if (bigBoss.getBossPhase() == 2) {
                        s.dead = true;
                    }
                    if (time > 0) {
                        if (Math.random() < 1) {
                            if (obstacle.getMovementGoal() == (int) s.getTranslateX()) {
                                ThreadLocalRandom random = ThreadLocalRandom.current();
                                int moveGoal = (random.nextInt(1, 3));
                                System.out.println(moveGoal);
                                obstacle.movementGoal(moveGoal);
                            } else if (obstacle.getMovementGoal() < (int) s.getTranslateX()) {
                                s.slowMoveLeft();
                            } else if (obstacle.getMovementGoal() > (int) s.getTranslateX()) {
                                s.slowMoveRight();
                            }
                        }
                    }
                    break;

                //moving obstacle 2
                case "obstacle2":
                    if (bigBoss.getBossPhase() == 2) {
                        s.dead = true;
                    }
                    if (time > 0) {
                        if (Math.random() < 1) {
                            if (obstacle.getMovementGoal2() == (int) s.getTranslateX()) {
                                ThreadLocalRandom random = ThreadLocalRandom.current();
                                int moveGoal = (random.nextInt(1, 3));
                                System.out.println(moveGoal);
                                obstacle.movementGoal(moveGoal);
                            } else if (obstacle.getMovementGoal2() < (int) s.getTranslateX()) {
                                s.slowMoveLeft();
                            } else if (obstacle.getMovementGoal2() > (int) s.getTranslateX()) {
                                s.slowMoveRight();
                            }
                        }
                    }
                    break;

                //Simulate player bullet impact
                case "playerbullet":
                    s.moveUp();
                    entities().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {

                            System.out.println(bigBoss.getCurrentHealth());
                            if (bigBoss.getCurrentHealth() == 0) {
                                enemy.dead = true;
                                s.dead = true;
                                enemyDead.getAndIncrement();
                            } else {
                                s.dead = true;
                                bigBoss.setHealth(bigBoss.getCurrentHealth() - 1);
                                deIncrementBossHP();
                                if (bigBoss.getCurrentHealth() == bigBoss.getHalfHealth()) {
                                    bigBoss.setBossPhase(2);
                                }
                                if (bigBoss.getCurrentHealth() == bigBoss.getHalfHealth()) {
                                    Image enemyImage = new Image(getClass().getResource("images/LBossPhase2.png").toExternalForm());
                                    ImagePattern en = new ImagePattern(enemyImage);
                                    bossEnemy.setFill(en);

                                    Image obsImage = new Image(getClass().getResource("images/EnragedMechaZombieName.png").toExternalForm());
                                    ImagePattern obs = new ImagePattern(obsImage);
                                    BossName.setFill(obs);
                                }
                            }
                        }
                    });

                    entities().stream().filter(e -> e.type.equals("outline")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            s.dead = true;
                        }
                    });

                    //Bullet Obstacle Interaction
                    entities().stream().filter(e -> e.type.equals("obstacle")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            s.dead = true;
                        }
                    });

                    //Bullet Obstacle Interaction
                    entities().stream().filter(e -> e.type.equals("obstacle2")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            s.dead = true;
                        }
                    });
                    break;

                //Simulate enemy bullet impact, have game stop if hits player, adjust later for player health
                case "enemytribulletL":
                    s.moveDiagonalLeftDown();
                    if (s.getBoundsInParent().intersects(playerEntity.getBoundsInParent())) {
                        s.dead = true;
                        deIncrementHP();
                    }
                    entities().stream().filter(e -> e.type.equals("outline")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            s.dead = true;
                        }
                    });
                    break;

                //Simulate enemy bullet impact, have game stop if hits player, adjust later for player health
                case "enemytribulletR":
                    s.movieDiagonalRightDown();
                    if (s.getBoundsInParent().intersects(playerEntity.getBoundsInParent())) {
                        s.dead = true;
                        deIncrementHP();
                    }
                    entities().stream().filter(e -> e.type.equals("outline")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            s.dead = true;
                        }
                    });
                    break;

                //Simulate enemy bullet impact, have game stop if hits player, adjust later for player health
                case "enemybullet":
                    s.moveDown();
                    if (s.getBoundsInParent().intersects(playerEntity.getBoundsInParent())) {
                        s.dead = true;
                        deIncrementHP();
                    }
                    entities().stream().filter(e -> e.type.equals("outline")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            s.dead = true;
                        }
                    });
                    break;


            }

        });

        //Remove dead entities d
        root.getChildren().removeIf(n -> {
            BossLevel.Entity s = (BossLevel.Entity) n;
            return s.dead;
        });

        if (enemyDead.get() == 1) {
            System.out.println("The boss is dead");
            stage.close();

            timer.stop();

            //launches game over menu when game is lost
            GameWonMenu gameWonMenu = new GameWonMenu();
            Stage gameWonMenuStage = new Stage();
            try {
                gameWonMenu.start(gameWonMenuStage);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}





