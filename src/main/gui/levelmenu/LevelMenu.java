package main.gui.levelmenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class LevelMenu extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LevelMenuSB.fxml"));
        root.setId("pane");
        primaryStage.setTitle("Level Menu");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
