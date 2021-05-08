package Graphics.ui.game;

import Graphics.ui.game.UIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class View {
    private final Scene myScene;
    protected String title = "No target!";

    public View(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        loader.setControllerFactory(c -> this);

        Parent main = loader.load();
        myScene = new Scene(main);
    }

    public void setActive() {
        Stage primaryStage = UIController.getInstance().getGameStage();

        primaryStage.setScene(myScene);
        primaryStage.setTitle(title);

        primaryStage.show();
    }

    public abstract void rePaint();
}
