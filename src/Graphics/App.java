package Graphics;

import Graphics.ui.menu.MenuController;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.show();

        MenuController.init(primaryStage);
        MenuController.getInstance().setActive();
    }
}
