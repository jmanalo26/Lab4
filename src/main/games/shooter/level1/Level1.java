package main.games.shooter.level1;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.games.shooter.Player;
import main.gui.gameovermenu.GameOverMenu;
import main.gui.gamewonmenu.GameWonMenu;

import javafx.scene.image.*;
import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Shooting game class, this will represent the basic functionality of our shooting mini-game.
 */
public class Level1 extends Application {

    private Pane root = new Pane();

    private Entity playerEntity = new Entity(275, 450, 25, 25, "player", Color.CYAN);

    private Entity HPBar;

    private Player player;

    private double time = 0;

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
        root.setPrefSize(600, 575);
        root.getChildren().add(playerEntity);
        player = new Player(playerEntity.getX(), playerEntity.getY(), 5, 6);
        //Add animation timer to animate bullet
        timer.start();
        addEnemies();
        addObstacles();
        addHealthBar();
        root.setId("pane");
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

    private void addObstacles(){
        Random random = new Random();
        Image obsImage = new Image(getClass().getResource("images/tree.png").toExternalForm());
        ImagePattern obs = new ImagePattern(obsImage);
        for (int i = 0; i < 20; i++) {
            int num = random.nextInt(400) + 50;
            int num2 = 400 - random.nextInt(300);
            Entity obstacle = new Entity(num, num2, 30, 30, "obstacle", Color.GREEN);
            obstacle.setId("tree");
            obstacle.setFill(obs);
            root.getChildren().add(obstacle);
        }
    }

    private void addHealthBar(){
        HPBar = new Entity(10, 530, 200, 15, "HP", Color.CYAN);
        Entity HPBorder = new Entity(0, 500, 1000, 100, "outline", Color.BLACK);
        HPBorder.setOpacity(0.3);
        root.getChildren().addAll(HPBar, HPBorder);
        //root.getChildren().add(HPBar);
    }

    /**
     * Add enemies, currently set to 10
     */
    private void addEnemies() {
        Image enemyImage = new Image(getClass().getResource("images/zombie.png").toExternalForm());
        ImagePattern en = new ImagePattern(enemyImage);
        for (int i = 0; i < 10; i++) {
            Entity enemy = new Entity(30 + i * 50, 50, 25, 25, "enemy", Color.RED);
            Entity leftEnemy = new Entity(30, 90 + i * 30, 25, 25, "enemyLeft", Color.RED);
            Entity rightEnemy = new Entity(30 + 9 * 50, 90 + i * 30, 25, 25, "enemyRight", Color.RED);
            enemy.setId("enemy");
            leftEnemy.setId("enemy");
            rightEnemy.setId("enemy");
            enemy.setFill(en);
            leftEnemy.setFill(en);
            rightEnemy.setFill(en);
            root.getChildren().add(enemy);
            root.getChildren().add(leftEnemy);
            root.getChildren().add(rightEnemy);
        }
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
        System.out.println("Player Starting HP: " + player.getHP());
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                    playerEntity.moveLeft();
                    player.setX(playerEntity.getTranslateX());
                    System.out.println(player.getX());
                    break;

                case D:
                    playerEntity.moveRight();
                    player.setX(playerEntity.getTranslateX());
                    System.out.println(player.getX());
                    break;

                case SPACE:
                    if (player.getAmmo() > 0) {
                        shoot(playerEntity);
                        player.setAmmo(player.getAmmo() - 1);
                        System.out.println(player.getAmmo());
                    }
                    break;
                case R:
                    player.setAmmo(6);
                    System.out.println("Player Reloaded: " + player.getAmmo());
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
            setTranslateX(getTranslateX() - 7);
        }

        void moveRight() {
            setTranslateX(getTranslateX() + 7);
        }


    }

    /**
     * Create bullet entity objects
     *
     * @param entity Bullet
     */
    private void shoot(Entity entity) {
        Entity s = new Entity((int) entity.getTranslateX() + 20, (int) entity.getTranslateY(), 2, 20, entity.type + "bullet", Color.WHITE);
        root.getChildren().add(s);
    }

    /**
     * Method to increment Player HP, player is dead if 0
     */
    private void incrementHP() {
        player.setHP(player.getHP() - 1);
        System.out.println(player.getHP() + " HP remaining");
        HPBar.setWidth(HPBar.getWidth() - 40);
        if (player.getHP() <= 0) {
            playerEntity.dead = true;
            stage.close();
        }
    }

    /**
     * Update method, has functionality for bullet hits, enemy movement, entity deaths
     */
    private void update() {
        time += 1;
        if (time % 1000 == 0){
            addEnemies();
        }
        entities().forEach(s -> {
            switch (s.type) {
                //Simulate Enemy movement and shooting
                case "enemy":
                    if (time > 200) {
                        if (Math.random() < 0.015) {
                            s.moveDown();
                            entities().stream().filter(e -> e.type.equals("player")).forEach(p -> {
                                if (s.getBoundsInParent().intersects(p.getBoundsInParent())) {
                                    incrementHP();
                                    s.dead = true;
                                }
                            });
                            entities().stream().filter(e -> e.type.equals("outline")).forEach(e -> {
                                if (s.getBoundsInParent().intersects(e.getBoundsInParent())) {
                                    s.dead = true;
                                }
                            });
                        }
                    }
                    break;

                case "enemyLeft":
                    if (time > 200) {
                        if (Math.random() < 0.015) {
                            s.moveRight();
                            s.moveDown();
                            entities().stream().filter(e -> e.type.equals("player")).forEach(p -> {
                                if (s.getBoundsInParent().intersects(p.getBoundsInParent())) {
                                    incrementHP();
                                    s.dead = true;
                                }
                            });
                            entities().stream().filter(e -> e.type.equals("outline")).forEach(e -> {
                                if (s.getBoundsInParent().intersects(e.getBoundsInParent())) {
                                    s.dead = true;
                                }
                            });
                        }
                    }
                    break;

                case "enemyRight":
                    if (time > 200) {
                        if (Math.random() < 0.015) {
                            s.moveLeft();
                            s.moveDown();
                            entities().stream().filter(e -> e.type.equals("player")).forEach(p -> {
                                if (s.getBoundsInParent().intersects(p.getBoundsInParent())) {
                                    incrementHP();
                                    s.dead = true;
                                }
                            });
                            entities().stream().filter(e -> e.type.equals("outline")).forEach(e -> {
                                if (s.getBoundsInParent().intersects(e.getBoundsInParent())) {
                                    s.dead = true;
                                }
                            });
                        }
                    }
                    break;

                //Simulate player bullet impact
                case "playerbullet":
                    s.moveUp();

                    //Simulate bullet hitting enemies
                    entities().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                            enemyDead.getAndIncrement();
                        }
                    });

                    entities().stream().filter(e -> e.type.equals("enemyLeft")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                            enemyDead.getAndIncrement();
                        }
                    });

                    entities().stream().filter(e -> e.type.equals("enemyRight")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                            enemyDead.getAndIncrement();
                        }
                    });

                    //Bullet Obstacle Interaction
                    entities().stream().filter(e -> e.type.equals("obstacle")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            s.dead = true;
                        }
                    });
                    break;

                //Simulate enemy bullet impact, have game stop if hits player, adjust later for player health
                case "enemybullet":
                    s.moveDown();
                    if (s.getBoundsInParent().intersects(playerEntity.getBoundsInParent())) {
                        player.setHP(player.getHP() - 1);
                        System.out.println(player.getHP() + " HP remaining");
                        if (player.getHP() <= 0) {
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
                    }
                    break;


            }

        });

        //Remove dead entities d
        root.getChildren().removeIf(n -> {
            Entity s = (Entity) n;
            return s.dead;
        });

        if (enemyDead.get() == 25) {
            System.out.println("All enemies dead");
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




