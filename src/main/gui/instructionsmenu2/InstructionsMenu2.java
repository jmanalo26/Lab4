package main.gui.instructionsmenu2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InstructionsMenu2 extends Application {


    public InstructionsMenu2() {

    }

    /**
     * Start Setup for Start Menu of Game
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("InstructionsMenu2SB.fxml"));
        root.setId("pane");
        primaryStage.setTitle("Instruction Menu");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 500, 580));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

