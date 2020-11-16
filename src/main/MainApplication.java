package main;

import javafx.application.Application;
import javafx.stage.Stage;
import main.gui.startmenu.StartMenu;

/**
 * @author Liam
 * @version 1.0
 * <p>
 * Main Method for game window
 */
public class MainApplication extends Application {

    public static void main(String[] args) {

        MainApplication.launch(StartMenu.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
