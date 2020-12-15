package main.gui.startmenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.gui.music.MusicPlayer;


/**
 * @author Liam
 * @version 1.0
 *
 * Creation of Start Menu
 */
public class StartMenu extends Application {


    public StartMenu(){

    }

    /**
     * Start Setup for Start Menu of Game
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        MusicPlayer.setMusicMain();
        MusicPlayer.playMusic();
        Parent root = FXMLLoader.load(getClass().getResource("StartMenuSB.fxml"));
        root.setId("pane");
        primaryStage.setTitle("Project Z");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 600, 534));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
