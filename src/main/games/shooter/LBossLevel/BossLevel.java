package main.games.shooter.LBossLevel;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.MainApplication;
import main.gui.gameovermenu.GameOverMenu;
import main.gui.gamewonmenu.GameWonMenu;
import main.gui.startmenu.StartMenu;

import java.util.Random;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class BossLevel extends Application {

    private Pane root = new Pane();

    private BossLevel.Entity playerEntity = new BossLevel.Entity(275, 450, 15, 15, "player", Color.BLUE);

    private double time = 0;

    private Boss bigBoss = new Boss();

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
        root.setPrefSize(600, 575);
        root.getChildren().add(playerEntity);
        //Add animation timer to animate bullet
        timer.start();
        addBoss();
        return root;
    }

    /**
     * Creates a list of entities to help with updates
     *
     * @return List of entities
     */
    private List<BossLevel.Entity> entities() {
        return root.getChildren().stream().map(n -> (BossLevel.Entity) n).collect(Collectors.toList());
    }

    /**
     * Adds the single boss enemy
     */
    private void addBoss() {
        BossLevel.Entity enemy = new BossLevel.Entity(280, 50, 60, 30, "enemy", Color.RED);
        root.getChildren().add(enemy);
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
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                    playerEntity.moveLeft();
                    break;
                case D:
                    playerEntity.moveRight();
                    break;
                case SPACE:
                    playerShoot(playerEntity);
                    break;
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
            setTranslateY(50);
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
        BossLevel.Entity s = new BossLevel.Entity((int) entity.getTranslateX() + 30, (int) entity.getTranslateY() + 20, 3, 20, entity.type + "bullet", Color.BLACK);
        root.getChildren().add(s);
    }

    /**
     * Create trishot bullet entity objects
     *
     * @param entity Bullet
     */
    private void triShot(BossLevel.Entity entity) {
        BossLevel.Entity s1 = new BossLevel.Entity((int) entity.getTranslateX() + 20, (int) entity.getTranslateY(), 5, 5, entity.type + "tribulletL", Color.BLACK);
        root.getChildren().add(s1);
        BossLevel.Entity s2 = new BossLevel.Entity((int) entity.getTranslateX() + 30, (int) entity.getTranslateY(), 5, 5, entity.type + "bullet", Color.BLACK);
        root.getChildren().add(s2);
        BossLevel.Entity s3 = new BossLevel.Entity((int) entity.getTranslateX() + 40, (int) entity.getTranslateY(), 5, 5, entity.type + "tribulletR", Color.BLACK);
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
                    if (time > 2) {
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
                                    if (yGoal == true && ((int) s.getTranslateY() < 200)) {
                                        s.moveDown();
                                    }
                                    if (yGoal == true && (int) s.getTranslateY() == 200) {
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


                            System.out.println(time);
                            System.out.println(s.getTranslateX());
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
                                if (bigBoss.getCurrentHealth() == bigBoss.getHalfHealth()) {
                                    bigBoss.setBossPhase(2);
                                }
                            }
                        }
                    });
                    break;

                //Simulate enemy bullet impact, have game stop if hits player, adjust later for player health
                case "enemytribulletL":
                    s.moveDiagonalLeftDown();
                    if (s.getBoundsInParent().intersects(playerEntity.getBoundsInParent())) {
                        playerEntity.dead = true;
                        s.dead = true;
                        stage.close();


                        timer.stop();

                        //launches game over menu when game is lost
                        GameOverMenu gameOverMenu = new GameOverMenu();
                        Stage gameOverMenuStage = new Stage();
                        try {
                            gameOverMenu.start(gameOverMenuStage);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                //Simulate enemy bullet impact, have game stop if hits player, adjust later for player health
                case "enemytribulletR":
                    s.movieDiagonalRightDown();
                    if (s.getBoundsInParent().intersects(playerEntity.getBoundsInParent())) {
                        playerEntity.dead = true;
                        s.dead = true;
                        stage.close();

                        timer.stop();

                        //launches game over menu when game is lost
                        GameOverMenu gameOverMenu = new GameOverMenu();
                        Stage gameOverMenuStage = new Stage();
                        try {
                            gameOverMenu.start(gameOverMenuStage);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                //Simulate enemy bullet impact, have game stop if hits player, adjust later for player health
                case "enemybullet":
                    s.moveDown();
                    if (s.getBoundsInParent().intersects(playerEntity.getBoundsInParent())) {
                        playerEntity.dead = true;
                        s.dead = true;
                        stage.close();

                        timer.stop();

                        //launches game over menu when game is lost
                        GameOverMenu gameOverMenu = new GameOverMenu();
                        Stage gameOverMenuStage = new Stage();
                        try {
                            gameOverMenu.start(gameOverMenuStage);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
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





