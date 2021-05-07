package Graphics.ui.game.views.asteroidView;

import Graphics.ui.FXMLController;
import Graphics.ui.game.UIController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AsteroidViewController extends FXMLController {

    public AsteroidViewController() throws IOException {
        super("asteroidView.fxml");
        title = "Asteroid View";
    }

    @FXML
    public void switchBoard(ActionEvent actionEvent) {
        UIController.getInstance().switchView();
    }
}
