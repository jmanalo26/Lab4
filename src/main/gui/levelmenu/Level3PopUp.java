package main.gui.levelmenu;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * @author Liam
 * @version 1.0
 *
 * Creation of Start Menu
 */
public class Level3PopUp extends Application {

    public Level3PopUp(){

    }


    /**
     * Start Setup for Start Menu of Game
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Level3PopUp.fxml"));
        root.setId("pane");
        primaryStage.setTitle("Level 3");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
