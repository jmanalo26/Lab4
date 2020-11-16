package main.gui.gameovermenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameOverMenu extends Application{

    public GameOverMenu()  {

    }

    /**
     * Start Setup for the game over screen of the Game
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GameOverMenuSB.fxml"));
        root.setId("pane");
        primaryStage.setTitle("Game Over Menu");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 500, 580));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
