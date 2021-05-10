package Graphics;

import Graphics.ui.menu.MenuController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main of the game with graphics
 */
public class App extends Application {

    /**
     * True, if the game is in test mode
     */
    private static boolean testMode = false;

    /**
     * Return true, if the gam is in test mode
     * @return true, if the gam is in test mode
     */
    public static boolean isTestMode() {
        return testMode;
    }

    /**
     * Start an application.
     * @param primaryStage The Stage what will be appeared.
     * @throws Exception MenuController can throwed.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.show();

        MenuController.init(primaryStage);
        MenuController.getInstance().setActive();
    }

    /**
     * The main method.
     * @param args Given args.
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("-t")) {
                testMode = true;
            }
        }
        launch(args);
    }
}
