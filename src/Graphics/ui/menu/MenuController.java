package Graphics.ui.menu;

import Graphics.ui.FXMLController;
import Graphics.ui.game.UIController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    private static MenuController ref;

    public static MenuController getInstance() {
        return ref;
    }

    private final Stage primaryStage;
    private final Scene myScene;

    public static void init(Stage stage) {
        try {
            ref = new MenuController(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MenuController(Stage stage) throws IOException {
        primaryStage = stage;
        // load fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        loader.setControllerFactory(c -> this);

        Parent main = loader.load();
        myScene = new Scene(main);
    }

    public void setActive() {
        primaryStage.close();

        primaryStage.setScene(myScene);
        primaryStage.setTitle("Menu");

        primaryStage.show();
    }

    @FXML
    public void start(ActionEvent actionEvent) {
        primaryStage.close();
        UIController.init();
        UIController.getInstance().newGame();
    }
}
