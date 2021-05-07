package Graphics.ui.game.views.boardView;

import Graphics.ui.FXMLController;
import Graphics.ui.game.UIController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BoardViewController extends FXMLController {

    public BoardViewController() throws IOException {
        super("boardView.fxml");
        title = "Board View";
    }

    @FXML
    public void switchAsteroid(ActionEvent actionEvent) {
        UIController.getInstance().switchView();
    }
}
