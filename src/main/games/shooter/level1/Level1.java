package main.games.shooter.level1;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.games.shooter.Player;
import main.gui.gameovermenu.GameOverMenu;
import main.gui.gamewonmenu.GameWonMenu;
import main.gui.music.MusicPlayer;

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

    private Entity objective;

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
    private Image bullet = new Image(getClass().getResource("images/bullet.png").toExternalForm());

//    private MusicPlayer musicPlayer = new MusicPlayer();

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
        addObjective();
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
    private void addObstacles() {
        Random random = new Random();
        Image obsImage = new Image(getClass().getResource("images/tree.png").toExternalForm());
        ImagePattern obs = new ImagePattern(obsImage);

        //Player shooting layer
        for (int i = 0; i < 30; i++) {
            int num = random.nextInt(500);
            int num2 = 400 - random.nextInt(300);
            Entity obstacle = new Entity(num, num2, 25, 30, "obstacle", Color.GREEN);
            obstacle.setId("tree");
            obstacle.setFill(obs);
            root.getChildren().add(obstacle);
        }

        //Deep Woods Layer
        for (int i = 0; i < 100; i++) {
            int num = random.nextInt(650);
            int num2 = 400 - (random.nextInt(100) + 250);
            Entity obstacle = new Entity(num, num2, 30, 30, "obstacle", Color.GREEN);
            obstacle.setId("tree");
            obstacle.setFill(obs);
            root.getChildren().add(obstacle);
        }
    }

    /**
     * Creates a health bar entity at the bottom of the game window
     */
    private void addHealthBar(){
        HPBar = new Entity(10, 530, 200, 15, "HP", Color.GREEN);
        Entity HPBorder = new Entity(0, 500, 1000, 100, "outline", Color.BLACK);
        HPBorder.setOpacity(0.3);
        root.getChildren().addAll(HPBar, HPBorder);
        //root.getChildren().add(HPBar);
    }

    private void addObjective(){
        Image obsImage = new Image(getClass().getResource("images/obj1.png").toExternalForm());
        ImagePattern obj = new ImagePattern(obsImage);
        objective = new Entity(230,465, 150,150,"Objective", Color.BLACK);
        objective.setFill(obj);
        root.getChildren().add(objective);
    }

    private void displayAmmo(){
        ImagePattern b = new ImagePattern(bullet);
        for (int i = 0; i < player.getAmmo(); i++) {
            Entity ammo = new Entity(400 + ( 20 * i), 530, 30, 40, "ammo" + i, Color.WHITE);
            ammo.setFill(b);
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
        ImagePattern b = new ImagePattern(bullet);
        if (player.getAmmo() < 6) {
            int ammoUsed = 6 - player.getAmmo();
            for (int i = 0; i < ammoUsed; i++){
                Entity ammo = new Entity(400 + ( 20 * i), 530, 30, 40, "ammo" + i, Color.WHITE);
                ammo.setFill(b);
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
        Image obsImage = new Image(getClass().getResource("images/health.png").toExternalForm());
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
        Image enemyImage = new Image(getClass().getResource("images/zombie.png").toExternalForm());
        ImagePattern en = new ImagePattern(enemyImage);
        for (int i = 0; i < 10; i++) {
            Entity enemy = new Entity(30 + i * 50, 50, 25, 25, "enemy", Color.RED);
            //Entity leftEnemy = new Entity(30, 90 + i * 30, 25, 25, "enemyLeft", Color.RED);
            //Entity rightEnemy = new Entity(30 + 9 * 50, 90 + i * 30, 25, 25, "enemyRight", Color.RED);
            Entity leftEnemy = new Entity(30 + i * 50, 70, 25, 25, "enemyLeft", Color.RED);
            Entity rightEnemy = new Entity(30 + i * 50, 90, 25, 25, "enemyRight", Color.RED);
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
     * Method to simulate Enemy AI movement
     * @param s Enemy entity
     */
    private void enemyTracking(Entity s) {
        if (s.getTranslateX() > playerEntity.getTranslateX()) {
            s.moveLeft();
        } else if (s.getTranslateX() < playerEntity.getTranslateX()) {
            s.moveRight();
        } else {
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
//        MusicPlayer.stopMusic();
//        MusicPlayer.setMusicShooter();
//        MusicPlayer.playMusic();

        enemyDead.set(0);
        Scene scene = new Scene(createRoot());
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
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
            setTranslateY(getTranslateY() + 7);
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
        root.getChildren().add(s);
    }

    /**
     * Method to de-increment Player HP, player is dead if 0
     */
    private void deIncrementHP() {
        player.setHP(player.getHP() - 1);
//        MusicPlayer.playMusicDamaged();
        System.out.println("Player Hit; " + player.getHP() + " HP remaining");
        HPBar.setWidth(HPBar.getWidth() - 40);
        if (player.getHP() <= 3 && player.getHP() > 1){
            HPBar.setFill(Color.ORANGE);
        }
        else if (player.getHP() <= 1){
            HPBar.setFill(Color.RED);
        }
        if (player.getHP() <= 0) {
            playerEntity.dead = true;
            System.out.println("Player has died");
//            MusicPlayer.playMusicDeath();
//            MusicPlayer.stopMusic();
//            MusicPlayer.setMusicMaze();
//            MusicPlayer.playMusic();
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
        if (player.getHP() > 3){
            HPBar.setFill(Color.LIMEGREEN);
        }
        else if (player.getHP() > 1){
            HPBar.setFill(Color.ORANGE);
        }
    }

    /**
     * Update method, has functionality for bullet hits, enemy movement, entity deaths
     */
    private void update() {
        time += 1;
        //Add new enemy wave every 10 seconds
        if (time % 1000 == 0){
            addEnemies();
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

                case "enemyRight":

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

                //Simulate player bullet impact
                case "playerbullet":
                    s.moveUp();

                    //Simulate bullet hitting enemies
                    entities().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                            enemyDead.getAndIncrement();
//                            MusicPlayer.playMusicEnemyHit();
                            System.out.println("Enemies Killed: " + enemyDead);
                        }
                    });

                    entities().stream().filter(e -> e.type.equals("enemyLeft")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                            enemyDead.getAndIncrement();
 //                           MusicPlayer.playMusicEnemyHit();
                            System.out.println("Enemies Killed: " + enemyDead);
                        }
                    });

                    entities().stream().filter(e -> e.type.equals("enemyRight")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                            enemyDead.getAndIncrement();
//                            MusicPlayer.playMusicEnemyHit();
                            System.out.println("Enemies Killed: " + enemyDead);
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
//            MusicPlayer.stopMusic();
//            MusicPlayer.setMusicMaze();
//            MusicPlayer.playMusic();
//            MusicPlayer.playMusicWinRound();

            stage.close();

            timer.stop();

//            //launches game over menu when game is lost
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




