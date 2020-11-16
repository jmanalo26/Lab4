package main.gui.gameovermenu;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import main.gui.levelmenu.LevelMenu;
import main.gui.startmenu.StartMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameOverMenuController implements Initializable {

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

}
