package main.gui.levelmenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import main.games.shooter.ShootingGame;
import main.gui.instructionsmenu.InstructionsMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LevelMenuController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Method tied to button for opening the first level(Shooting Game)
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void openLevelOne(ActionEvent actionEvent) throws IOException {
        ShootingGame shootingGame = new ShootingGame();
        Stage shootingGameStage = new Stage();
        try {
            shootingGame.start(shootingGameStage);
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method tied to button for opening the level menu
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void openLevelOnePopUp(ActionEvent actionEvent) throws IOException {
        LevelMenu levelMenu = new LevelMenu();
        Stage instructionsMenuStage = new Stage();
        try {
            levelMenu.start(instructionsMenuStage);
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
