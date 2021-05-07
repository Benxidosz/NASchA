package Graphics.ui;

import Graphics.ui.game.UIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class FXMLController {
    private final Scene myScene;
    protected String title = "No target!";

    public FXMLController(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        loader.setControllerFactory(c -> this);

        Parent main = loader.load();
        myScene = new Scene(main);
    }

    public void setActive() {
        Stage primaryStage = UIController.getInstance().getGameStage();
//        primaryStage.close();

        primaryStage.setScene(myScene);
        primaryStage.setTitle(title);

        primaryStage.show();
    }
}
