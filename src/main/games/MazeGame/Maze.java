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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;

public class Maze extends Application {
    private final int LEVEL_1_BOARD_SIZE = 12;
    private final int LEVEL_2_BOARD_SIZE = 14;
    private final int LEVEL_3_BOARD_SIZE = 20;
    private final int TUTORIAL_BOARD_SIZE = 6;
    private Label[][] labels;
    private static Maze instance;
    private boolean fullVis = false;
    private BoardData bd;
    private Stage stage;
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
                                   {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                   {1,-2,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
                                   {1,1,1,1,1,1,0,0,0,0,0,0,0,1,1,1,1,1,1,1},
                                   {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1},
                                   {1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                   {1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1},
                                   {1,1,1,0,0,0,0,1,1,1,1,0,1,1,1,1,1,0,0,1},
                                   {1,1,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
                                   {1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                   {1,1,1,1,0,0,0,0,0,0,0,0,0,0,-2,0,0,0,0,1},
                                   {1,1,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1},
                                   {1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1},
                                   {1,1,1,1,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,1},
                                   {1,1,1,0,0,0,0,0,0,0,1,0,0,0,0,1,1,1,1,1},
                                   {1,1,1,0,0,-2,0,0,0,0,0,1,0,0,0,0,1,1,1,1},
                                   {1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1},
                                   {1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1},
                                   {1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1},
                                   {1,1,1,1,1,1,1,1,1,1,1,-1,1,1,1,1,1,1,1,1}};

    public Maze() {
        instance = this;
    }

    public static Maze getInstance() {
        return instance;
    }
    @Override
    public void start(Stage s) throws Exception {
        stage = s;
        bd = new BoardData(LEVEL_3_BOARD_SIZE, LEVEL_3_BOARD_SIZE);
        Scene scene = loadBoard(stage);




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
                                bd = new BoardData(LEVEL_3_BOARD_SIZE, LEVEL_3_BOARD_SIZE);
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
                    } else if(!fullVis && (code.equalsIgnoreCase("w") || code.equalsIgnoreCase("a") || code.equalsIgnoreCase("s") || code.equalsIgnoreCase("d"))) {
                        init_flashlight(bd.getPlayer(), code);
                    }
                    else {
                        bd.move_player(code);
                    }
                });
    }
    public void generatePopup() {
        Pane p = new Pane();
        Label label = new Label();
        label.setText("Congrats, you completed this level!");
        p.getChildren().add(label);
        Scene s = new Scene(p,300,50);
        //Button b = new Button("Continue to Level 2");
        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e)
                    {

                    }
                };

        // when button is pressed
        //b.setOnAction(event);
        //p.getChildren().add(b);
        stage.setScene(s);

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
            labels[posX][posY].getGraphic().setVisible(true);
            labels[posX][posY].setStyle("-fx-background-color: white");
        } catch (Exception e) {
            labels[posX][posY].setStyle("-fx-background-color: white");
        }
        }



    public Scene loadBoard(Stage stage) {
        GridPane maze_board = new GridPane();
        maze_board.setAlignment(Pos.CENTER);
        labels = new Label[LEVEL_3_BOARD_SIZE][LEVEL_3_BOARD_SIZE];
        for (int i = 0; i < LEVEL_3_BOARD_SIZE; i++) {
            for (int j = 0; j < LEVEL_3_BOARD_SIZE; j++) {
                maze_board.add(labels[i][j] = new Label(), i, j);
                labels[i][j].setPrefSize(55, 55);
                labels[i][j].setStyle("-fx-background-color: black");
            }
        }
        VBox tutorial = new VBox();
        Button yes = new Button("Yes");

        Button no = new Button("No");
        Label prompt = new Label("Would you like to be taken through a tutorial?");
        EventHandler<ActionEvent> eventYes =
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e)
                    {
                        Scene scene = new Scene(maze_board, 50 * LEVEL_3_BOARD_SIZE, 50 * LEVEL_3_BOARD_SIZE);
                        stage.setTitle("Maze");
                        stage.setScene(scene);
                        stage.show();
                    }
                };
        yes.setOnAction(eventYes);
        Scene scene = new Scene(maze_board, 50 * LEVEL_3_BOARD_SIZE, 50 * LEVEL_3_BOARD_SIZE);
        EventHandler<ActionEvent> eventNo =
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e)
                    {
                        stage.setTitle("Tutorial");
                        stage.setScene(scene);
                        stage.show();
                    }
                };
        no.setOnAction(eventNo);

        tutorial.getChildren().addAll(prompt, yes, no);
        Scene tut = new Scene(tutorial, 500,500);
        stage.setScene(tut);
        stage.show();

        bd.add_obstacles(LEVEL3);
        bd.add_player();
        bd.add_enemies(15);
        bd.add_perk(2);



        return scene;
    }

    private void addScene(GridPane maze_board) {

    }

    public void addGraphic(Player p) {
        Image player = new Image(p.getImage());
        ImageView iv = new ImageView(player);
        iv.setFitHeight(45);
        iv.setFitWidth(45);
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
        iv.setVisible(false);
        iv.setFitHeight(45);
        iv.setFitWidth(45);
        labels[x][y].setGraphic(iv);
    }

    public void addGraphic(Obstacle o, int x, int y) {
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
}
