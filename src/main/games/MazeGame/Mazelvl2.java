package main.games.MazeGame;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.util.Duration;
import main.gui.mazefront.SpriteAnimation;
import main.gui.music.MusicPlayer;

public class Mazelvl2 extends Application {
    private final int LEVEL_1_BOARD_SIZE = 12;
    private final int LEVEL_2_BOARD_SIZE = 14;
    private final int LEVEL_3_BOARD_SIZE = 20;
    private final int TUTORIAL_BOARD_SIZE = 6;
    private Label[][] labels;
    private int flashlight_counter = 15;
    private static Mazelvl2 instance;
    private boolean fullVis = false;
    private BoardData2 bd;
    private Stage stage;
    private boolean partial_vis = false;
    private final int[][] TUTORIAL1 = {{1,1,1,1,1,1},
            {1,0,0,0,0,1},
            {1,0,0,0,0,1},
            {1,0,0,0,0,1},
            {1,0,0,0,0,1},
            {1,1,1,1,1,1}};
    private final int[][] LEVEL1= {{1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,-1},
            {1,1,1,1,1,1,1,1,1,1,1,1}};
    private final int[][] LEVEL2= {{1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,0,0,0,0,0,0,0,0,1},
            {1,1,1,0,0,0,0,0,1,1,1,0,0,1},
            {1,1,1,0,0,0,0,1,1,1,1,0,0,1},
            {1,1,1,0,0,0,0,0,0,0,1,0,0,1},
            {1,1,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,0,0,0,0,0,0,0,0,0,-1},
            {1,1,1,0,0,0,0,0,0,0,1,0,0,1},
            {1,1,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
    private final int[][] LEVEL3= {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-3,1},
            {1,-2,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
            {1,1,1,1,1,1,0,0,0,0,0,0,0,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1},
            {1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,0,0,0,0,1,1,1,1,0,1,1,1,1,1,0,0,1},
            {1,1,1,0,0,0,-4,0,0,0,1,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,0,0,0,0,0,0,0,0,0,0,-2,0,0,0,0,1},
            {1,1,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1},
            {1,1,1,1,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,1},
            {1,1,1,-4,0,0,0,0,0,0,1,0,0,0,0,1,1,1,1,1},
            {1,1,1,0,0,-2,0,0,0,0,0,1,0,0,0,0,1,1,1,1},
            {1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1},
            {1,1,1,0,0,0,0,0,0,0,-4,0,0,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,-1,1,1,1,1,1,1,1,1}};

    public Mazelvl2() {
        instance = this;
    }

    public Stage getStage() {
        return stage;
    }

    public static Mazelvl2 getInstance() {
        return instance;
    }

    @Override
    public void start(Stage s) throws Exception {
        stage = s;
        bd = new BoardData2(LEVEL_2_BOARD_SIZE, LEVEL_2_BOARD_SIZE);
        Scene scene = loadBoard(stage);
        MusicPlayer.setMusicMaze();
        MusicPlayer.playMusic();


        scene.setOnKeyPressed(
                (EventHandler<KeyEvent>) e -> {
                    String code = e.getCode().toString();
                    if (code.equals("ENTER")) {
                        VBox vb = new VBox();
                        vb.setSpacing(5);
                        vb.setPrefWidth(100);
                        vb.setAlignment(Pos.CENTER);
                        Button resume = new Button("Resume");
                        resume.setMinWidth(100);
                        resume.setOnAction(new EventHandler() {

                            @Override
                            public void handle(Event event) {
                                stage.setTitle("Maze");
                                stage.setScene(scene);
                                stage.show();
                            }
                        });
                        Button restart = new Button("Restart");
                        restart.setMinWidth(100);
                        restart.setOnAction(new EventHandler() {

                            @Override
                            public void handle(Event event) {
                                bd = new BoardData2(LEVEL_2_BOARD_SIZE, LEVEL_2_BOARD_SIZE);
                                loadBoard(stage);
                            }
                        });
                        Button quit = new Button("Quit");
                        quit.setMinWidth(100);
                        quit.setOnAction(new EventHandler() {

                            @Override
                            public void handle(Event event) {
                                Platform.exit();
                            }
                        });
                        vb.getChildren().addAll(resume, restart, quit);
                        Scene pause_scene = new Scene(vb, 250, 250);
                        stage.setTitle("Paused");
                        stage.setScene(pause_scene);
                        stage.show();
                    } else if (!fullVis && (code.equalsIgnoreCase("w") || code.equalsIgnoreCase("a") || code.equalsIgnoreCase("s") || code.equalsIgnoreCase("d"))) {
                        init_flashlight(bd.getPlayer(), code);
                        flashlight_counter--;
                    } else if(code.equalsIgnoreCase("i")) {
                        Stage dialog = new Stage();
                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initOwner(stage);
                        VBox dialogVbox = new VBox(20);
                        dialogVbox.setStyle("-fx-background-color: #000000");
                        Label coins = new Label("Coins: " + bd.getCoinCount());
                        coins.setAlignment(Pos.CENTER);
                        Label full = new Label("Full Visibility: " + fullVis);
                        Label partial = new Label("Partial Visibility: " + partial_vis);
                        DecimalFormat df2 = new DecimalFormat(" #,##0 '%'");
                        Label flashlight = new Label("Flashlight Battery: " + df2.format((flashlight_counter / 15.0) * 100));
                        coins.setStyle("-fx-text-fill: white");
                        full.setStyle("-fx-text-fill: white");
                        partial.setStyle("-fx-text-fill: white");
                        flashlight.setStyle("-fx-text-fill: white");
                        dialogVbox.getChildren().addAll(flashlight, coins, full, partial);
                        Scene dialogScene = new Scene(dialogVbox, 300, 200);
                        dialog.setScene(dialogScene);
                        dialog.show();
                    }
                    else {
                        bd.move_player(code);
                        if (partial_vis) {
                            partial_visibility(bd.getPlayer());
                        }
                    }
                });
    }


    public void generatePopup() {
        Pane p = new Pane();
        Label label = new Label();
        label.setText("Congrats, you completed this level!");
        p.getChildren().add(label);
        Scene s = new Scene(p, 300, 50);
        //Button b = new Button("Continue to Level 2");
        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e) {

                    }
                };

        // when button is pressed
        //b.setOnAction(event);
        //p.getChildren().add(b);
        stage.setScene(s);

    }

    private void partial_visibility(int[] player) {
        partial_vis = true;
        int posX = player[0];
        int posY = player[1];

        int[][] arr = {{player[0], player[1] + 1}, {player[0], player[1] - 1}, {player[0] + 1, player[1]}, {player[0] - 1, player[1]}};
        for(int[] a: arr) {
            try {
                labels[a[0]][a[1]].getGraphic().setVisible(true);
                labels[a[0]][a[1]].setStyle("-fx-background-color: white");
            }catch (Exception e){
                labels[a[0]][a[1]].setStyle("-fx-background-color: white");
            }
        }
    }


    private void init_flashlight(int[] player, String direction) {
        int posX = player[0];
        int posY = player[1];

        switch (direction) {
            case "W":
                posY = posY - 1;
                break;
            case "A":
                posX = posX - 1;
                break;
            case "S":
                posY = posY + 1;
                break;
            case "D":
                posX = posX + 1;
                break;
        }


        try {
            int[] p = bd.getPlayer();

            for (int i = 0; i < labels.length; i++) {
                for (int j = 0; j < labels[i].length; j++) {
                    labels[i][j].setStyle("-fx-background-color: black");
                    if (labels[i][j].getGraphic() != null && i != p[0] && j != p[1]) {
                        if (labels[i][j].getGraphic().isVisible()) {
                            labels[i][j].getGraphic().setVisible(false);
                        }
                    }
                }
            }
            if(flashlight_counter > 0) {
                labels[posX][posY].getGraphic().setVisible(true);
                labels[posX][posY].setStyle("-fx-background-color: white");
            }
        } catch (Exception e) {
            labels[posX][posY].setStyle("-fx-background-color: white");
        }
    }



    public Scene loadBoard(Stage stage) {
        GridPane maze_board = new GridPane();
        maze_board.setAlignment(Pos.CENTER);
        labels = new Label[LEVEL_2_BOARD_SIZE][LEVEL_2_BOARD_SIZE];
        for (int i = 0; i < LEVEL_2_BOARD_SIZE; i++) {
            for (int j = 0; j < LEVEL_2_BOARD_SIZE; j++) {
                maze_board.add(labels[i][j] = new Label(), i, j);
                labels[i][j].setPrefSize(55, 55);
                labels[i][j].setStyle("-fx-background-color: black");
            }
        }

        Scene scene = new Scene(maze_board, 50 * LEVEL_2_BOARD_SIZE, 50 * LEVEL_2_BOARD_SIZE);

        bd.add_obstacles(LEVEL2);
        bd.add_player();
        bd.add_enemies(10);
        bd.add_perk(2);
        stage.setScene(scene);

        stage.show();
        return scene;
    }

    private void addScene(GridPane maze_board) {

    }

    public void addGraphic(Player p) {
        ImageView iv = new ImageView(p.getImage());
        iv.setPreserveRatio(true);
        iv.setFitHeight(45);
        SpriteAnimation playerAnimation = new SpriteAnimation(iv, Duration.millis(700), 10, 10, 15, 8, 24, 33);
        playerAnimation.setCycleCount(Animation.INDEFINITE);
        playerAnimation.play();
        labels[p.getPosX()][p.getPosY()].setGraphic(iv);
        int[] pos = {p.getPosX(), p.getPosY()};
        if(!fullVis) {
            init_flashlight(pos, "d");
        }
    }

    public void removeGraphic(int x, int y) {
        labels[x][y].setGraphic(null);
    }

    public void addGraphic(Zombie z, int x, int y) {
        Image zombie = new Image(z.getImage());
        ImageView iv = new ImageView(zombie);
        if(fullVis) {
            iv.setVisible(true);
        } else {
            iv.setVisible(false);
        }
        SpriteAnimation zombieAnimation = new SpriteAnimation(iv, Duration.millis(700), 5, 5, 23, 192, 134, 171);
        zombieAnimation.setCycleCount(Animation.INDEFINITE);
        zombieAnimation.play();
        iv.setFitHeight(45);
        iv.setFitWidth(45);
        labels[x][y].setGraphic(iv);
    }

    public void addGraphic(Obstaclelvl2 o, int x, int y) {
        Image obstacle = new Image(o.getImage());
        ImageView iv = new ImageView(obstacle);
        iv.setVisible(false);

        iv.setFitHeight(45);
        iv.setFitWidth(45);
        labels[x][y].setGraphic(iv);
    }

    public void addGraphic(Perk p, int x, int y) {
        Image obstacle = new Image(p.getImage());
        ImageView iv = new ImageView(obstacle);
        iv.setVisible(false);

        iv.setFitHeight(45);
        iv.setFitWidth(45);
        labels[x][y].setGraphic(iv);
    }

    public void full_visibility() {
        fullVis = true;
        for (int i = 0; i < labels.length; i++) {
            for (int j = 0; j < labels[i].length; j++) {
                labels[i][j].setStyle("-fx-background-color: white");
                if (labels[i][j].getGraphic() != null) {
                    labels[i][j].getGraphic().setVisible(true);
                }
            }
        }
    }

    public void addGraphic(Computer c, int x, int y) {
        Image computer = new Image(c.getImage());
        ImageView iv = new ImageView(computer);
        iv.setVisible(false);

        iv.setFitHeight(45);
        iv.setFitWidth(45);
        labels[x][y].setGraphic(iv);
    }

    public void setGraphic(Person person, int x, int y) {
        Image p = new Image(person.getImage());
        ImageView iv = new ImageView(p);
        iv.setVisible(true);

        iv.setFitHeight(45);
        iv.setFitWidth(45);
        labels[x][y].setGraphic(iv);
    }

    public void show_computer_level_1() {
        Scene scene = stage.getScene();
        Timer t = new Timer("Timer");
        VBox vb = new VBox();
        vb.setStyle("-fx-background-color: #000000");
        vb.setSpacing(5);
        vb.setPrefWidth(500);
        vb.setPrefHeight(500);
        vb.setAlignment(Pos.CENTER);
        Label run_out_of_time = new Label("You have run out of time");
        run_out_of_time.setVisible(false);
        run_out_of_time.setStyle("-fx-text-fill: red");
        Label incorrect_solution = new Label("You have not corrected the errors");
        incorrect_solution.setVisible(false);
        incorrect_solution.setStyle("-fx-text-fill: red");
        Label task = new Label("There are three errors in this computer's code that you must correct. You have 30 seconds.");
        task.setStyle("-fx-text-fill:#006400");
        TextArea ta = new TextArea("System.out.print1n(\"Find Cure)\"");
        ta.setStyle("-fx-text-fill:#000000");
        ta.setPrefWidth(400);
        ta.setPrefHeight(300);
        Button link = new Button("Link Computer");
        link.setMinWidth(100);
        Button exit = new Button("Exit");
        exit.setMinWidth(100);
        EventHandler<ActionEvent> linkEvent =
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e)
                    {
                        if(ta.getText().equals("System.out.println(\"Find Cure\");")) {
                            t.cancel();
                            stage.setScene(scene);
                        } else {
                            incorrect_solution.setVisible(true);
                        }
                    }
                };
        link.setOnAction(linkEvent);
        EventHandler<ActionEvent> exitEvent =
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e)
                    {
                        stage.setScene(scene);
                        bd.remove_computers();
                    }
                };
        exit.setOnAction(exitEvent);
        exit.setAlignment(Pos.CENTER);



        vb.getChildren().addAll(task, ta, link, exit, incorrect_solution, run_out_of_time);
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                run_out_of_time.setVisible(true);
                link.setVisible(false);
                exit.setVisible(true);

            }
        };
        t.schedule(tt, 30000);

        Scene s = new Scene(vb, 500, 500);
        stage.setScene(s);
    }

    public void show_computer_level_2() {
        Scene scene = stage.getScene();
        VBox vb = new VBox();
        vb.setStyle("-fx-background-color: #000000");
        vb.setSpacing(5);
        vb.setPrefWidth(500);
        vb.setPrefHeight(500);
        vb.setAlignment(Pos.CENTER);
        Label run_out_of_time = new Label("You have run out of time");
        run_out_of_time.setVisible(false);
        run_out_of_time.setStyle("-fx-text-fill: red");
        Label incorrect_solution = new Label("You have not corrected the errors");
        incorrect_solution.setVisible(false);
        incorrect_solution.setStyle("-fx-text-fill: red");
        Label task = new Label("There is something wrong with the file you are reading. Find the two errors in 20 seconds.");
        task.setStyle("-fx-text-fill:#006400");
        TextArea ta = new TextArea("0001101011101101101l00010010110010\n1100101101101010000100100100111100\n0011011110001110001001110001011101\n1011101101000101011000110011010101\n11011101100O1010101000011101011100\n1100101101101010000100100100111100\n0001101011101101101100010010110010\n1101110110001010101000011101011100");
        ta.setStyle("-fx-text-fill:#000000");
        ta.setPrefWidth(400);
        ta.setPrefHeight(300);
        Button link = new Button("Link Computer");
        link.setMinWidth(100);
        Button exit = new Button("Exit");
        link.setMinWidth(100);
        EventHandler<ActionEvent> linkEvent =
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e)
                    {

                        if(ta.getText().equals("0001101011101101101100010010110010\n1100101101101010000100100100111100\n0011011110001110001001110001011101\n1011101101000101011000110011010101\n1101110110001010101000011101011100\n1100101101101010000100100100111100\n0001101011101101101100010010110010\n1101110110001010101000011101011100")) {
                            stage.setScene(scene);
                        } else {
                            incorrect_solution.setVisible(true);
                        }
                    }
                };
        link.setOnAction(linkEvent);
        EventHandler<ActionEvent> exitEvent =
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e)
                    {
                        stage.setScene(scene);
                        bd.remove_computers();
                    }
                };
        exit.setOnAction(exitEvent);
        exit.setAlignment(Pos.CENTER);



        vb.getChildren().addAll(task, ta, link, exit, incorrect_solution, run_out_of_time);
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Hello");
                run_out_of_time.setVisible(true);
                link.setVisible(false);
                exit.setVisible(true);
            }
        };
        Timer t = new Timer("Timer");
        t.schedule(tt, 60000);

        Scene s = new Scene(vb, 500, 500);
        stage.setScene(s);




    }
    public void show_computer_level_3() {
        Scene scene = stage.getScene();
        VBox vb = new VBox();
        vb.setStyle("-fx-background-color: #000000");
        vb.setSpacing(5);
        vb.setPrefWidth(500);
        vb.setPrefHeight(500);
        vb.setAlignment(Pos.CENTER);
        Label run_out_of_time = new Label("You have run out of time");
        run_out_of_time.setVisible(false);
        run_out_of_time.setStyle("-fx-text-fill: red");
        Label incorrect_solution = new Label("You have not corrected the errors");
        incorrect_solution.setVisible(false);
        incorrect_solution.setStyle("-fx-text-fill: red");
        Label task = new Label("Solve the traveling salesman problem. You have 20 seconds");
        task.setStyle("-fx-text-fill:#006400");
        TextArea ta = new TextArea();
        ta.setStyle("-fx-text-fill:#000000");
        ta.setPrefWidth(400);
        ta.setPrefHeight(300);
        Button small_button = new Button();
        EventHandler<ActionEvent> sb_event =
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        ta.setText("It's a bit above your paygrade");
                    }
                };
        small_button.setOnAction(sb_event);
        small_button.setMinHeight(1);
        small_button.setMinWidth(1);
        Button link = new Button("Link Computer");
        link.setMinWidth(100);
        Button exit = new Button("Exit");
        link.setMinWidth(100);
        EventHandler<ActionEvent> linkEvent =
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e)
                    {

                        if(ta.getText().equals("It's a bit above your paygrade")) {
                            stage.setScene(scene);
                        } else {
                            incorrect_solution.setVisible(true);
                        }
                    }
                };
        link.setOnAction(linkEvent);
        EventHandler<ActionEvent> exitEvent =
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e)
                    {
                        stage.setScene(scene);
                        bd.remove_computers();
                    }
                };
        exit.setOnAction(exitEvent);
        exit.setAlignment(Pos.CENTER);



        vb.getChildren().addAll(small_button,task, ta, link, exit, incorrect_solution, run_out_of_time);
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Hello");
                run_out_of_time.setVisible(true);
                link.setVisible(false);
                exit.setVisible(true);
            }
        };
        Timer t = new Timer("Timer");
        t.schedule(tt, 15000);

        Scene s = new Scene(vb, 500, 500);
        stage.setScene(s);



    }

    public void addGraphic(Box box, int x, int y) {
        Image b = new Image(box.getImage());
        ImageView iv = new ImageView(b);
        iv.setVisible(false);

        iv.setFitHeight(45);
        iv.setFitWidth(45);
        labels[x][y].setGraphic(iv);
    }

    public void addGraphic(Coin coin, int x, int y) {
        Image c = new Image(coin.getImage());
        ImageView iv = new ImageView(c);
        iv.setVisible(false);

        iv.setFitHeight(45);
        iv.setFitWidth(45);
        labels[x][y].setGraphic(iv);
    }
    public void addGraphic(String s, int x, int y) {
        Image sImage = new Image(s);
        ImageView iv = new ImageView(sImage);
        iv.setVisible(false);

        iv.setFitHeight(45);
        iv.setFitWidth(45);
        labels[x][y].setGraphic(iv);
    }

    public void add_perk_box_screen(int coinCount) {
        Scene scene = stage.getScene();
        VBox v = new VBox();
        v.setMinWidth(600);
        v.setMinHeight(60);
        HBox hbox = new HBox();
        Coin coin = new Coin(0, 0);
        Image c = new Image(coin.getImage());
        ImageView ivOneCoin = new ImageView(c);
        ivOneCoin.setFitWidth(25);
        ivOneCoin.setFitHeight(25);
        ImageView ivTwoCoin = new ImageView(c);
        ivTwoCoin.setFitWidth(25);
        ivTwoCoin.setFitHeight(25);
        ImageView ivThreeCoin = new ImageView(c);
        ivThreeCoin.setFitWidth(25);
        ivThreeCoin.setFitHeight(25);
        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(ivOneCoin,ivTwoCoin);

        hbox.getChildren().add(hbox1);

        Button remove_zombies = new Button("Remove Zombies");
        remove_zombies.setMinWidth(200);
        Button full_visibility = new Button("Full Visibility");
        full_visibility.setMinWidth(200);

        Button partial_visibility = new Button("Partial Visibility");
        partial_visibility.setMinWidth(200);
        EventHandler<ActionEvent> pv_event =
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(coinCount >= 1) {
                            partial_visibility(bd.getPlayer());
                            bd.setCoinCount(coinCount - 1);
                        } else {
                            Stage dialog = new Stage();
                            dialog.initModality(Modality.APPLICATION_MODAL);
                            dialog.initOwner(stage);
                            VBox dialogVbox = new VBox(20);
                            dialogVbox.setStyle("-fx-background-color: #000000");
                            Label cost = new Label("Perk Cost: 1 coin");
                            cost.setAlignment(Pos.CENTER);
                            Label coins = new Label("You have " + coinCount + " coin(s)");
                            coins.setStyle("-fx-text-fill: white");
                            cost.setStyle("-fx-text-fill: white");
                            dialogVbox.getChildren().addAll(cost, coins);
                            Scene dialogScene = new Scene(dialogVbox, 300, 200);
                            dialog.setScene(dialogScene);
                            dialog.show();
                        }
                    }
                };
        EventHandler<ActionEvent> rz_event =
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(coinCount >= 2) {
                            bd.remove_zombies();
                            bd.setCoinCount(coinCount - 2);
                        } else {
                            Stage dialog = new Stage();
                            dialog.initModality(Modality.APPLICATION_MODAL);
                            dialog.initOwner(stage);
                            VBox dialogVbox = new VBox(20);
                            dialogVbox.setStyle("-fx-background-color: #000000");
                            Label cost = new Label("Perk Cost: 2 coins");
                            cost.setAlignment(Pos.CENTER);
                            Label coins = new Label("You have " + coinCount + " coin(s)");
                            coins.setStyle("-fx-text-fill: white");
                            cost.setStyle("-fx-text-fill: white");
                            dialogVbox.getChildren().addAll(cost, coins);
                            Scene dialogScene = new Scene(dialogVbox, 300, 200);
                            dialog.setScene(dialogScene);
                            dialog.show();
                        }
                    }
                };
        EventHandler<ActionEvent> fv_event =
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(coinCount >= 3) {
                            full_visibility();
                            bd.setCoinCount(coinCount - 3);
                        } else {
                            Stage dialog = new Stage();
                            dialog.initModality(Modality.APPLICATION_MODAL);
                            dialog.initOwner(stage);
                            VBox dialogVbox = new VBox(20);
                            dialogVbox.setStyle("-fx-background-color: #000000");
                            Label cost = new Label("Perk Cost: 3 coins");
                            cost.setAlignment(Pos.CENTER);
                            Label coins = new Label("You have " + coinCount + " coin(s)");
                            coins.setStyle("-fx-text-fill: white");
                            cost.setStyle("-fx-text-fill: white");
                            dialogVbox.getChildren().addAll(cost, coins);
                            Scene dialogScene = new Scene(dialogVbox, 300, 200);
                            dialog.setScene(dialogScene);
                            dialog.show();
                        }
                    }
                };
        partial_visibility.setOnAction(pv_event);
        remove_zombies.setOnAction(rz_event);
        full_visibility.setOnAction(fv_event);
        HBox h = new HBox();
        h.getChildren().addAll(partial_visibility, remove_zombies, full_visibility);
        Button exit = new Button("Exit");
        exit.setMinWidth(200);
        EventHandler<ActionEvent> exit_event =
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        stage.setScene(scene);
                    }
                };
        exit.setOnAction(exit_event);
        HBox h_box = new HBox();
        h_box.setPrefWidth(600);
        Button filler1 = new Button("Remove Zombies");
        filler1.setMinWidth(200);
        filler1.setVisible(false);
        Button filler2 = new Button("Full Visibility");
        filler2.setMinWidth(200);
        filler2.setVisible(false);
        h_box.getChildren().addAll(filler1, exit, filler2);
        v.setStyle("-fx-background-color: black");
        v.getChildren().addAll(h, new HBox(), h_box);

        Scene s = new Scene(v, 600, 60);
        stage.setScene(s);
        stage.show();


    }
}
