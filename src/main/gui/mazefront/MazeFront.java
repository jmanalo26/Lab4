package main.gui.mazefront;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.gui.mazefront.util.Block;

import java.io.File;
import java.nio.file.Paths;

public class MazeFront extends Application {

    private static final Image IMAGE = new Image("resources/images/spritesheet/battlesprites.png");
    private SpriteSheet tileSheet = new SpriteSheet(new ImageView("resources/images/tilesheet/dungeon_tiles.png"), 16, 16);

    private int COLUMNS = 10;
    private int COUNT = 10;
    private int OFFSET_X = 15;
    private int OFFSET_Y = 8;
    private int WIDTH = 24;
    private int HEIGHT = 33;
    private double newY, newX = 0;

    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean firing = false;
    private boolean idle = true;
    private boolean headingRight = true;
    private boolean headingLeft = false;
    private boolean dead = false;

    private boolean decelerateLeft;
    private boolean decelerateRight;
    private boolean decelerateUp;
    private boolean decelerateDown;

    private int playerMaxSpeed = 6;
    private int playerVelocity = 0;

    private final ImageView imageView = new ImageView(IMAGE);
    private Group playerImageGroup = new Group(imageView);
    private Group bossMage;
    private Group greaterMage;
    private Group battleMage;
    private boolean chaseEnabled;


    private SpriteAnimation playerAnimation = new SpriteAnimation(
            imageView,
            Duration.millis(1500),
            COUNT, COLUMNS,
            OFFSET_X, OFFSET_Y,
            WIDTH, HEIGHT
    );
    private Animation animation = playerAnimation;
    //    private PathTransition bossTransition = new PathTransition();
    private TranslateTransition transition = new TranslateTransition();


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
//        System.out.println(Paths.get("resources/images/spritesheet/zombie_lvl_1.png").toUri().toString());
//        File f = new File("resources/images/spritesheet/zombie_lvl_1.png");
//        if (f.exists()) {
//            System.out.println("Ye");
//        }

        imageView.setCache(true);
//        player.play();
//        StackPane root = new StackPane();

//        ImageView greaterMagePicture = new ImageView("main/gui/resources/spritesheet/mage-1-85x94.png");
//        greaterMage = new Group(greaterMagePicture);
//        SpriteAnimation greaterMageAnimation = new SpriteAnimation(greaterMagePicture, Duration.millis(3000), 8, 4, 0, 0, 85, 94);
//        greaterMageAnimation.setCycleCount(Animation.INDEFINITE);
//        greaterMageAnimation.play();
//        greaterMage.setTranslateX(475);
//        greaterMage.setTranslateY(300);
//
//        ImageView lesserMagePicture = new ImageView(new Image("main/gui/resources/spritesheet/disciple-45x51.png"));
//        Group lesserMage = new Group(lesserMagePicture);
//        SpriteAnimation lesserMageAnimation = new SpriteAnimation(lesserMagePicture, Duration.millis(3000), 4, 4, 0, 0, 45, 51);
//        lesserMageAnimation.setCycleCount(Animation.INDEFINITE);
//        lesserMageAnimation.play();
//        lesserMage.setTranslateX(400);
//        lesserMage.setTranslateY(100);
//
//        transition.setDuration(Duration.millis(2000));
//        transition.setNode(greaterMage);
//        transition.setToX(375);
//        transition.setAutoReverse(true);
//        transition.setCycleCount(Animation.INDEFINITE);
//        transition.play();
//
//
//        ImageView battleMagePicture = new ImageView(new Image("main/gui/resources/spritesheet/mage-2-122x110.png"));
//        battleMage = new Group(battleMagePicture);
//        SpriteAnimation battleMageAnimation = new SpriteAnimation(battleMagePicture, Duration.millis(3000), 8, 4, 0, 0, 122, 110);
//        battleMageAnimation.setCycleCount(Animation.INDEFINITE);
//        battleMageAnimation.play();
//        battleMage.setTranslateX(150);
//        battleMage.setTranslateY(525);
//
        ImageView bossMagePicture = new ImageView(new Image("resources/images/spritesheet/zombie_lvl_1.png"));
        bossMagePicture.setPreserveRatio(true);
        bossMagePicture.setFitWidth(40);
        bossMage = new Group(bossMagePicture);
        SpriteAnimation bossMageAnimation = new SpriteAnimation(bossMagePicture, Duration.millis(700), 5, 5, 23, 192, 134, 171);
        bossMageAnimation.setCycleCount(Animation.INDEFINITE);
        bossMageAnimation.play();
        bossMage.setTranslateX(395);
        bossMage.setTranslateY(650);

//        bossTransition.setNode(bossMage);


        Pane root = new Pane();
        primaryStage.setTitle("Project Z");
        imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));
        playerImageGroup.setTranslateX(175);
        playerImageGroup.setTranslateY(120);
//        bossTransition.setToX(playerImageGroup.getTranslateX());
//        bossTransition.setToY(playerImageGroup.getTranslateY());
//        bossTransition.setDuration(Duration.seconds(4));
//        bossTransition.setCycleCount(1);
//        moveTo = new MoveTo(playerImageGroup.getTranslateX(), playerImageGroup.getTranslateY());
//        path.getElements().add(moveTo);
//        bossTransition.setPath(path);
//        bossTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//        bossTransition.play();
//        bossTransition.play();

        animation.setCycleCount(Animation.INDEFINITE);
//        animation.setCycleCount(1);
        animation.play();

        final Canvas canvas = new Canvas(800, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.valueOf("#2a2a2a"));
        gc.fillRect(0, 0, 800, 800);

        root.getChildren().add(canvas);

//        SpriteSheet tileSheet = new SpriteSheet(new ImageView("resources/images/tilesheet/level2texresource.png"), 16, 16);
//        TileManager t = new TileManager(root, tileSheet, "resources/level/Winter.xml");
//        t.buildMap();

        TileManager t = new TileManager(tileSheet, "main/resources/level/Winter.xml");
        Block[][] temp = t.getBlocks();
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                if (temp[i][j].getImg() != null) {
                    System.out.println("Block image true!");
                } else {
                    System.out.println("Block image false!");
                }

                System.out.println("Block row: " + temp[i][j].getRow());
                System.out.println("Block col: " + temp[i][j].getColumn());
                System.out.println("Block value: " + temp[i][j].getValue());
            }
        }


        // player Object
        Pane spritePane = new Pane();
        spritePane.getChildren().addAll(bossMage, playerImageGroup);
//        spritePane.getChildren().add(playerImageGroup);
        root.getChildren().add(spritePane);


        AnimationTimer timer = new Timer();
        timer.start();
        playerImageGroup.requestFocus();
        Scene scene = new Scene(root, 800, 800);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.RIGHT) {
                movingRight = true;
                if (playerVelocity < playerMaxSpeed) {
                    playerVelocity += 2;
                }
                playerImageGroup.setTranslateX(playerImageGroup.getTranslateX() + playerVelocity);
            } else if (e.getCode() == KeyCode.LEFT) {
                movingLeft = true;
                if (playerVelocity > -playerMaxSpeed) {
                    playerVelocity -= 2;
                }
                newX = playerImageGroup.getTranslateX() + playerVelocity;
                playerImageGroup.setTranslateX(newX);
            } else if (e.getCode() == KeyCode.UP) {
                movingUp = true;
                if (playerVelocity > -playerMaxSpeed) {
                    playerVelocity -= 2;
                }
                playerImageGroup.setTranslateY(playerImageGroup.getTranslateY() + playerVelocity);
            } else if (e.getCode() == KeyCode.DOWN) {
                movingDown = true;
                if (playerVelocity < playerMaxSpeed) {
                    playerVelocity += 2;
                }
                playerImageGroup.setTranslateY(playerImageGroup.getTranslateY() + playerVelocity);

            }
            if (e.getCode() == KeyCode.SPACE) {
                firing = true;
            } else if (e.getCode() == KeyCode.Q) {
                dead = true;
            }
            idle = false;
        });

        scene.setOnKeyReleased(e -> {


            if (e.getCode() == KeyCode.RIGHT) {
                movingRight = false;
                headingRight = true;
                headingLeft = false;
                decelerateRight = true;
            } else if (e.getCode() == KeyCode.LEFT) {
                movingLeft = false;
                headingRight = false;
                headingLeft = true;
                decelerateLeft = true;
            } else if (e.getCode() == KeyCode.UP) {
                movingUp = false;
                decelerateUp = true;
            } else if (e.getCode() == KeyCode.DOWN) {
                movingDown = false;
                decelerateDown = true;
            } else if (e.getCode() == KeyCode.SPACE) {
                firing = false;
            }
            idle = true;
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    class Timer extends AnimationTimer {

        @Override
        public void handle(long l) {
            update(l);
        }

        private void update(long l) {
            if (!dead) {
                if (decelerateLeft) {
                    deceleratePlayer(2);
                } else if (decelerateRight) {
                    deceleratePlayer(1);
                } else if (decelerateUp) {
                    deceleratePlayer(4);
                } else if (decelerateDown) {
                    deceleratePlayer(3);
                }
                // enemy ai logic
//                chasePlayer(bossMage, 2);
//
//                if (Math.abs(playerImageGroup.getTranslateY() - battleMage.getTranslateY()) < 200 && Math.abs(playerImageGroup.getTranslateX() - battleMage.getTranslateX()) < 100) {
//                    chaseEnabled = true;
//                }
//                if (chaseEnabled) {
//                    chasePlayer(battleMage, 1.5);
//                }

                if (movingDown && headingRight || movingUp && headingRight) {
                    COLUMNS = 15;
                    COUNT = 15;
                    OFFSET_X = 14;
                    OFFSET_Y = 151;
                } else if (movingDown && headingLeft || movingUp && headingLeft) {
                    COLUMNS = 15;
                    COUNT = 15;
                    OFFSET_X = 10;
                    OFFSET_Y = 103;
                }
                if (movingLeft) {
//                animation.stop();
                    COLUMNS = 15;
                    COUNT = 15;
                    OFFSET_X = 10;
                    OFFSET_Y = 103;
                }
                if (movingRight) {
                    COLUMNS = 15;
                    COUNT = 15;
                    OFFSET_X = 14;
                    OFFSET_Y = 151;
                }
                if (firing && headingLeft) {
//                animation.setCycleCount(1);
                    COLUMNS = 5;
                    COUNT = 5;
                    OFFSET_X = 4;
                    OFFSET_Y = 200;
                    WIDTH = 35;
                    HEIGHT = 31;
                } else if (firing && headingRight) {
                    COLUMNS = 5;
                    COUNT = 5;
                    OFFSET_X = 13;
                    OFFSET_Y = 248;
                    WIDTH = 35;
                    HEIGHT = 31;
                }
                if (idle && headingRight) {
                    COLUMNS = 10;
                    COUNT = 10;
                    OFFSET_X = 9;
                    OFFSET_Y = 56;
                    WIDTH = 24;
                    HEIGHT = 33;
                } else if (idle && headingLeft) {
                    COLUMNS = 10;
                    COUNT = 10;
                    OFFSET_X = 15;
                    OFFSET_Y = 8;
                    WIDTH = 24;
                    HEIGHT = 33;
                }
                playerAnimation.setColumns(COLUMNS);
                playerAnimation.setCount(COUNT);
                playerAnimation.setOffsetX(OFFSET_X);
                playerAnimation.setOffsetY(OFFSET_Y);
                playerAnimation.setWidth(WIDTH);
                playerAnimation.setHeight(HEIGHT);
//            animation.setDelay(Duration.millis(5500));
                //24 33
            } else {
                if (headingLeft) {
                    COLUMNS = 8;
                    COUNT = 8;
                    OFFSET_X = 15;
                    OFFSET_Y = 297;
                    WIDTH = 21;
                    HEIGHT = 31;
                } else {
                    COLUMNS = 8;
                    COUNT = 8;
                    OFFSET_X = 12;
                    OFFSET_Y = 345;
                    WIDTH = 22;
                    HEIGHT = 31;
                }
                animation.stop();
                playerAnimation.setColumns(COLUMNS);
                playerAnimation.setCount(COUNT);
                playerAnimation.setOffsetX(OFFSET_X);
                playerAnimation.setOffsetY(OFFSET_Y);
                playerAnimation.setWidth(WIDTH);
                playerAnimation.setHeight(HEIGHT);
                stop();
                animation = new SpriteAnimation(
                        imageView,
                        Duration.millis(1500),
                        COUNT, COLUMNS,
                        OFFSET_X, OFFSET_Y,
                        WIDTH, HEIGHT
                );

                animation.setCycleCount(1);
                // Game Over Scene?
                animation.setOnFinished(finished -> {
//                    playerImageGroup.setVisible(false);
                    playerImageGroup.getChildren().clear();
                    playerImageGroup.setDisable(true);
                });
                animation.play();
            }

        }

        private void chasePlayer(Group enemy, double speed) {
//            var enemyXCenter = Math.floor(enemy.getTranslateX() / 2);
//            var enemyYCenter = Math.floor(enemy.getTranslateY() / 2);
            if (playerImageGroup.getTranslateX() - enemy.getTranslateX() > speed || playerImageGroup.getTranslateX() - enemy.getTranslateX() < -speed) {
                if (playerImageGroup.getTranslateX() > enemy.getTranslateX()) {
                    enemy.setTranslateX(enemy.getTranslateX() + speed);
                } else if (playerImageGroup.getTranslateX() < enemy.getTranslateX()) {
                    enemy.setTranslateX(enemy.getTranslateX() - speed);
                }
            } else {
                enemy.setTranslateX(enemy.getTranslateX());
            }
            if (playerImageGroup.getTranslateY() - enemy.getTranslateY() > speed || playerImageGroup.getTranslateY() - enemy.getTranslateY() < -speed) {
                if (playerImageGroup.getTranslateY() > enemy.getTranslateY()) {
                    enemy.setTranslateY(enemy.getTranslateY() + speed);
                } else if (playerImageGroup.getTranslateY() < enemy.getTranslateY()) {
                    enemy.setTranslateY(enemy.getTranslateY() - speed);
                }
            } else {
                enemy.setTranslateY(enemy.getTranslateY());
            }
        }

        private void deceleratePlayer(int type) {

            if (type == 1 || type == 3) {
                if (playerVelocity != 0) {
                    playerVelocity -= 1;
//                    System.out.println("Decaying Right: " + playerVelocity);
                    if (type == 1) {
                        playerImageGroup.setTranslateX(playerImageGroup.getTranslateX() + playerVelocity);
                    } else {
                        playerImageGroup.setTranslateY(playerImageGroup.getTranslateY() + playerVelocity);
                    }
                }
            } else {

                if (playerVelocity != 0) {
                    playerVelocity += 1;
//                    System.out.println("Decaying Right: " + playerVelocity);
                    if (type == 2) {
                        playerImageGroup.setTranslateX(playerImageGroup.getTranslateX() + playerVelocity);
                    } else {
                        playerImageGroup.setTranslateY(playerImageGroup.getTranslateY() + playerVelocity);
                    }
                }

            }
            if (playerVelocity < -10 || playerVelocity > 10) {
                playerVelocity = 0;
            }
            if (playerVelocity == 0) {
                decelerateUp = false;
                decelerateLeft = false;
                decelerateRight = false;
                decelerateDown = false;
            }
        }


    }


}