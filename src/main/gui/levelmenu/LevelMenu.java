package main.gui.levelmenu;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;


public class LevelMenu extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LevelMenuSB.fxml"));
        primaryStage.setTitle("creating buttons");

        //Creating an image
        Image image = new Image(new FileInputStream("src/main/gui/images/ProjectZ.png"));

        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(50);
        imageView.setY(25);

        //setting the fit height and width of the image view
        imageView.setFitHeight(100);
        imageView.setFitWidth(200);

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

        //Creating a Group object
        Group boot = new Group(imageView);

        // create a button
        Button lvl1 = new Button("Level 1");
        Button lvl2 = new Button("Level 2");
        Button lvl3 = new Button("Level 3");



        lvl1.setStyle("-fx-font-size: 2em;-fx-background-color: red");
        lvl2.setStyle("-fx-font-size: 2em;-fx-background-color: red");
        lvl3.setStyle("-fx-font-size: 2em;-fx-background-color: red");

        // create a stack pane
        TilePane r = new TilePane();


        Label l = new Label();




        // action event
        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                try {
                    LevelMenuController l1 = new LevelMenuController();
                    l1.openLevelOne(e);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                l.setText("Level 1");
                l.setAlignment(Pos.BASELINE_CENTER);

            }
        };

        // action event
        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                l.setText("Level 2");
                l.setAlignment(Pos.BASELINE_CENTER);
            }
        };

        // action event
        EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                l.setText("Level 3");
                l.setAlignment(Pos.BASELINE_CENTER);
            }
        };

        lvl1.setOnAction(event1);
        lvl2.setOnAction(event2);
        lvl3.setOnAction(event3);

        // add button
        r.getChildren().add(imageView);
        r.getChildren().add(lvl1);
        r.getChildren().add(lvl2);
        r.getChildren().add(lvl3);

        r.getChildren().add(l);




        // create a scene
        Scene sc = new Scene(r, 500, 500);

        BackgroundFill background_fill = new BackgroundFill(Color.BLACK,
                CornerRadii.EMPTY, Insets.EMPTY);

        // create Background
        Background background = new Background(background_fill);

        // set background
        r.setBackground(background);

        // set the scene
        primaryStage.setScene(sc);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
