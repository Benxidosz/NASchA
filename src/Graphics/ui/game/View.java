package Graphics.ui.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The abstract parent class of the Views.
 */
public abstract class View {
    /**
     * The scene for the actual view.
     */
    private final Scene myScene;
    /**
     * The title of the scene belonging to this view.
     */
    protected String title = "No target!";

    /**
     * The constructor of the class. Sets the Scene for the view
     * and sets the parent.
     * @param fxmlFile The graphic view for this object.
     * @throws IOException
     */
    public View(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        loader.setControllerFactory(c -> this);

        Parent main = loader.load();
        myScene = new Scene(main);
    }

    /**
     * Sets the scene and the title for the actual values.
     */
    public void setActive() {
        Stage primaryStage = UIController.getInstance().getGameStage();

        primaryStage.setScene(myScene);
        primaryStage.setTitle(title);

        primaryStage.show();
    }

    /**
     * Repaints the view.
     */
    public abstract void rePaint();

    /**
     * Refreshes the view.
     */
    public abstract void refresh();
}
