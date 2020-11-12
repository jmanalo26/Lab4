package main.gui.startmenu;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import main.gui.instructionsmenu.InstructionsMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Liam
 * @version 1.0
 *
 * Start menu controller
 */
public class StartMenuController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Method tied to button for exiting the game
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void exitGame(ActionEvent actionEvent) throws IOException {
        Platform.exit();
    }

    /**
     * Method tied to button for opening instructions menu
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void openInstructionsMenu(ActionEvent actionEvent) throws IOException {
        InstructionsMenu instructionsMenu = new InstructionsMenu();
        Stage instructionsMenuStage = new Stage();
        try{
            instructionsMenu.start(instructionsMenuStage);
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
