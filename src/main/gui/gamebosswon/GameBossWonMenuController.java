package main.gui.gamebosswon;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import main.games.MazeGame.Mazelvl2;
import main.games.MazeGame.Mazelvl3;
import main.gui.startmenu.StartMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameBossWonMenuController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Method tied to button for exiting the game
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void exitGame(ActionEvent actionEvent) throws IOException {
        Platform.exit();
    }

    /**
     * Method tied to button for opening the start menu
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void openStartMenu(ActionEvent actionEvent) throws IOException {
        StartMenu startMenu = new StartMenu();
        Stage startMenuStage = new Stage();
        try {
            startMenu.start(startMenuStage);
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void openLevelTwo(ActionEvent actionEvent) throws IOException {
        Mazelvl2 game = new Mazelvl2();
        Stage shootingGameStage = new Stage();
        try {
            game.start(shootingGameStage);
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Method tied to button for opening the first level(Shooting Game)
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void openLevelThree(ActionEvent actionEvent) throws IOException {
        Mazelvl3 game = new Mazelvl3();
        Stage shootingGameStage = new Stage();
        try {
            game.start(shootingGameStage);
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
