package main.games.MazeGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;

public class Maze extends Application {
    private final int BOARD_SIZE = 12;

    @Override
    public void start(Stage stage) throws Exception {
        BoardData bd = new BoardData(BOARD_SIZE, BOARD_SIZE);
        GridPane maze_board = new GridPane();
        maze_board.setAlignment(Pos.CENTER);
        Label[][] labels = new Label[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                maze_board.add(labels[i][j] = new Label(), i, j);
                labels[i][j].setPrefSize(55, 55);
                labels[i][j].setStyle("-fx-border-color: black");
            }
        }
        Image zombie = new Image("main/games/MazeGame/zombie.jpg");
        ImageView iv = new ImageView(zombie);
        iv.setFitWidth(45);
        iv.setFitHeight(45);

        labels[0][0].setGraphic(iv);
        bd.add_player(0,0);

        Scene scene = new Scene(maze_board, 50 * BOARD_SIZE, 50 * BOARD_SIZE);
        stage.setTitle("Maze");
        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(
                (EventHandler<KeyEvent>) e -> {
                    String code = e.getCode().toString();
                    int[] arr = bd.getPlayer();
                    if (code.equals("DOWN")) {
                        labels[arr[0]][arr[1]].setGraphic(null);
                        labels[arr[0]][arr[1] + 1].setGraphic(iv);
                        bd.movePlayer(arr[0], arr[1] + 1);
                        bd.print_board();
                    }
                    if (code.equals("UP")) {
                        labels[arr[0]][arr[1]].setGraphic(null);
                        labels[arr[0]][arr[1] - 1].setGraphic(iv);
                        bd.movePlayer(arr[0], arr[1] - 1);
                        bd.print_board();
                    }
                    if (code.equals("LEFT")) {
                        labels[arr[0]][arr[1]].setGraphic(null);
                        labels[arr[0] - 1][arr[1]].setGraphic(iv);
                        bd.movePlayer(arr[0] - 1, arr[1]);
                        bd.print_board();
                    }
                    if (code.equals("RIGHT")) {
                        labels[arr[0]][arr[1]].setGraphic(null);
                        labels[arr[0] + 1][arr[1]].setGraphic(iv);
                        bd.movePlayer(arr[0] + 1, arr[1]);
                        bd.print_board();
                    }
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
                                labels[0][0].setGraphic(iv);
                                bd.add_player(0,0);
                                stage.setTitle("Maze");
                                stage.setScene(scene);
                                stage.show();
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
                    }
                });
    }
    public void addEnemies(int numEnemies) {
        for(int i = 0; i < numEnemies; i++) {

        }
    }
}
