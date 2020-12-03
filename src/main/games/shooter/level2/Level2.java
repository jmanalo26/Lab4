package main.games.shooter.level2;

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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Shooting game class, this will represent the basic functionality of our shooting mini-game.
 */
public class Level2 extends Application {

    private Pane root = new Pane();

    private Entity playerEntity = new Entity(275, 450, 25, 25, "player", Color.CYAN);

    private Entity HPBar;

    private Player player;

    private double time = 0;

    private static AtomicInteger enemyDead = new AtomicInteger();

    private static Stage stage;

    private static Entity[] bullets = new Entity[6];

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
        Image playerImage = new Image(getClass().getResource("images2/player.png").toExternalForm());
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
        dropHealthItem();
        displayAmmo();
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

    /**
     * Adds random obstacles to game
     */
    private void addObstacles(){
        Random random = new Random();
        Image obsImage = new Image(getClass().getResource("images2/tree2.png").toExternalForm());
        ImagePattern obs = new ImagePattern(obsImage);

        //Player shooting layer
        for (int i = 0; i < 30; i++) {
            int num = random.nextInt(500) + 20;
            int num2 = 400 - random.nextInt(300);
            Entity obstacle = new Entity(num, num2, 20, 35, "obstacle", Color.GREEN);
            obstacle.setId("tree");
            obstacle.setFill(obs);
            root.getChildren().add(obstacle);
        }

        //Deep Woods Layer
        for (int i = 0; i < 100; i++) {
            int num = random.nextInt(650);
            int num2 = 400 - (random.nextInt(100) + 250);
            Entity obstacle = new Entity(num, num2, 30, 35, "obstacle", Color.GREEN);
            obstacle.setId("tree");
            obstacle.setFill(obs);
            root.getChildren().add(obstacle);
        }
    }

    /**
     * Creates a health bar entity at the bottom of the game window
     */
    private void addHealthBar(){
        HPBar = new Entity(10, 530, 200, 15, "HP", Color.RED);
        Entity HPBorder = new Entity(0, 500, 1000, 100, "outline", Color.BLACK);
        HPBorder.setOpacity(0.3);
        root.getChildren().addAll(HPBar, HPBorder);
        //root.getChildren().add(HPBar);
    }

    private void displayAmmo(){
        for (int i = 0; i < player.getAmmo(); i++) {
            Entity ammo = new Entity(400 + ( 20 * i), 530, 3, 20, "ammo" + i, Color.WHITE);
            bullets[i] = ammo;
            root.getChildren().add(ammo);
        }
    }

    private void depleteAmmo(){
        if (player.getAmmo() < 6) {
            int ammoUsed = 5 - player.getAmmo();
            bullets[ammoUsed].dead = true;
        }
    }

    private void addAmmo(){
        if (player.getAmmo() < 6) {
            int ammoUsed = 6 - player.getAmmo();
            for (int i = 0; i < ammoUsed; i++){
                Entity ammo = new Entity(400 + ( 20 * i), 530, 3, 20, "ammo" + i, Color.WHITE);
                bullets[i] = ammo;
                root.getChildren().add(ammo);
            }
        }
    }

    private void dropHealthItem(){
        Random random = new Random();
        int x = random.nextInt(550);
        if (x == player.getX()){
            x += 100;
        }
        Image obsImage = new Image(getClass().getResource("images2/health.png").toExternalForm());
        ImagePattern obs = new ImagePattern(obsImage);
        Entity itemHP = new Entity(x, 450, 20, 20, "restore", Color.PINK);
        itemHP.setFill(obs);
        root.getChildren().add(itemHP);
        System.out.println("Health Item Dropped");
    }


    /**
     * Add enemies, currently set to 10
     */
    private void addEnemies() {
        Image enemyImage = new Image(getClass().getResource("images2/zombie2.png").toExternalForm());
        ImagePattern en = new ImagePattern(enemyImage);
        for (int i = 0; i < 10; i++) {
            Entity enemy = new Entity(30 + i * 50, 50, 30, 30, "enemy", Color.RED);
            //Entity leftEnemy = new Entity(30, 90 + i * 30, 25, 25, "enemyLeft", Color.RED);
            //Entity rightEnemy = new Entity(30 + 9 * 50, 90 + i * 30, 25, 25, "enemyRight", Color.RED);
            Entity leftEnemy = new Entity(30 + i * 50, 70, 30, 30, "enemyLeft", Color.RED);
            Entity rightEnemy = new Entity(30 + i * 50, 90, 30, 30, "enemyRight", Color.PURPLE);
            enemy.setId("enemy");
            leftEnemy.setId("enemy");
            rightEnemy.setId("rightenemy");
            enemy.setFill(en);
            leftEnemy.setFill(en);
            rightEnemy.setFill(en);
            root.getChildren().add(enemy);
            root.getChildren().add(leftEnemy);
            root.getChildren().add(rightEnemy);
        }
    }

    private void waveSpawn(){
        Image enemyImage = new Image(getClass().getResource("images2/zombie2.png").toExternalForm());
        ImagePattern en = new ImagePattern(enemyImage);
        for (int i = 0; i < 10; i++) {
            Entity enemy = new Entity(30 + i * 50, 50, 30, 30, "enemy", Color.RED);
            Entity leftEnemy = new Entity(30 + i * 50, 70, 30, 30, "enemyLeft", Color.RED);
            enemy.setId("enemy");
            leftEnemy.setId("enemy");
            enemy.setFill(en);
            leftEnemy.setFill(en);
            root.getChildren().add(enemy);
            root.getChildren().add(leftEnemy);
        }
    }

    /**
     * Method to simulate Enemy AI movement
     * @param s Enemy entity
     */
    private void enemyTracking(Entity s){
        if (s.getTranslateX() > playerEntity.getTranslateX()){
            s.moveLeft();
        }
        else if (s.getTranslateX() < playerEntity.getTranslateX()){
            s.moveRight();
        }
        else{
            s.moveDown();
        }
        s.moveDown();
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
        scene.getStylesheets().addAll(this.getClass().getResource("style2.css").toExternalForm());
        System.out.println("Player Starting HP: " + player.getHP());
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
                        System.out.println("Current Ammo: " + player.getAmmo());
                        depleteAmmo();
                    }
                    break;
                case R:
                    addAmmo();
                    player.setAmmo(6);
                    System.out.println("Player Reloaded; Current Ammo: " + player.getAmmo());
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
        int hp = 3;

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
            setTranslateY(getTranslateY() + 4);
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
    private void createBullets(Entity entity) {
        Entity s = new Entity((int) entity.getTranslateX() + 20, (int) entity.getTranslateY(), 2, 20, entity.type + "bullet", Color.WHITE);
        if (s.type.contains("enemy")){
            s.setFill(Color.ORANGE);
            s.setId("rightenemybullet");
        }
        root.getChildren().add(s);
    }

    /**
     * Method to de-increment Player HP, player is dead if 0
     */
    private void deIncrementHP() {
        player.setHP(player.getHP() - 1);
        System.out.println("Player Hit; " + player.getHP() + " HP remaining");
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
     * Method to restore player HP when picking up health item
     */
    private void incrementHP() {
        player.setHP(player.getHP() + 1);
        HPBar.setWidth(HPBar.getWidth() + 40);
    }

    /**
     * Update method, has functionality for bullet hits, enemy movement, entity deaths
     */
    private void update() {
        time += 1;
        if (time % 1500 == 0){
            //waveSpawn();
            //Drop HP item if player is below HP, drop rate should be 30%
            if (player.getHP() < 5){
                Random r = new Random();
                int rNum = r.nextInt(10);
                if (rNum <= 3){
                    dropHealthItem();
                }
            }
        }
        entities().forEach(s -> {
            switch (s.type) {
                //Simulate Enemy movement
                case "enemy":

                case "enemyLeft":
                    if (time > 200) {
                        if (Math.random() < 0.015) {
                            if (s.getTranslateY() < 220){
                                s.moveDown();
                            } else {
                                enemyTracking(s);
                            }
                            entities().stream().filter(e -> e.type.equals("player")).forEach(p -> {
                                if (s.getBoundsInParent().intersects(p.getBoundsInParent())) {
                                    deIncrementHP();
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
                            if (s.getTranslateY() < 220) {
                                s.moveDown();
                                entities().stream().filter(e -> e.type.equals("obstacle")).forEach(e -> {
                                    if (s.getBoundsInParent().intersects(e.getBoundsInParent()) && s.getTranslateY() > 180) {
                                        e.dead = true;
                                    }
                                });
                            } else {
                                if (Math.random() < 0.4){
                                    createBullets(s);
                                    if (Math.random() < 0.5){
                                        s.moveLeft();
                                    } else{
                                        s.moveRight();
                                    }
                                }
                            }

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
                            System.out.println("Enemies Killed: " + enemyDead);
                        }
                    });

                    entities().stream().filter(e -> e.type.equals("enemyLeft")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                            enemyDead.getAndIncrement();
                            System.out.println("Enemies Killed: " + enemyDead);
                        }
                    });

                    entities().stream().filter(e -> e.type.equals("enemyRight")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            s.dead = true;
                            enemy.hp -= 1;
                            System.out.println(enemy.hp);
                            if (enemy.hp == 0) {
                                enemy.dead = true;
                                enemyDead.getAndIncrement();
                                System.out.println("Enemies Killed: " + enemyDead);
                            }
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

                case "enemyLeftbullet":

                case "enemyRightbullet":
                    s.moveDown();
                    if (s.getBoundsInParent().intersects(playerEntity.getBoundsInParent())) {
                        s.dead = true;
                        deIncrementHP();
                        }
                    entities().stream().filter(e -> e.type.equals("obstacle")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            s.dead = true;
                            enemy.dead = true;
                        }
                    });
                    entities().stream().filter(e -> e.type.equals("outline")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            s.dead = true;
                        }
                    });
                    break;

                    // HP item interaction
                case "restore":
                    if (s.getBoundsInParent().intersects(playerEntity.getBoundsInParent())) {
                        if (player.getHP() < 5) {
                            incrementHP();
                            System.out.println("+1 HP restored");
                        } else{
                            System.out.println("HP Full");
                        }
                        s.dead = true;
                    }
                    break;

            }

        });

        //Remove dead entities d
        root.getChildren().removeIf(n -> {
            Entity s = (Entity) n;
            return s.dead;
        });

        if (enemyDead.get() >= 30) {
            System.out.println("All enemies dead");
            stage.close();

            timer.stop();

            //launches game over menu when game is lost
//            GameWonMenu gameWonMenu = new GameWonMenu();
//            Stage gameWonMenuStage = new Stage();
//            try {
//                gameWonMenu.start(gameWonMenuStage);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }

    }

}




