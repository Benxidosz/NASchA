package Graphics.ui.game.views.messageBox;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Represents the messages the game shows.
 */
public class GameMessage {
    /**
     * The result of the message.
     */
    @FXML
    public Label result;

    private String data;
    /**
     * The stage of the message. This is what is showed to the user.
     */
    private final Stage stage;

    /**
     * The constructor of the class. Sets the stage.
     * @throws IOException
     */
    public GameMessage() throws IOException {
        stage = new Stage();
        this.data = "";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(String.valueOf(new File("gameMessage.fxml"))));

        loader.setControllerFactory(c -> this);

        Parent main = loader.load();
        Scene mainScene = new Scene(main);
        stage.setScene(mainScene);
        stage.setTitle("Game Ended.");
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    /**
     * Sets the text of the result label
     * @param res the text of the label.
     */
    public void showAndWait(String res) {
        result.setText(res);
        stage.showAndWait();
    }

    /**
     * Sets the date to new.
     */
    public void newGame() {
        data = "new";
        stage.close();
    }

    /**
     * Sets the data to exit.
     */
    public void exit() {
        data = "exit";
        stage.close();
    }

    /**
     * Returns the data.
     */
    public String getData() {
        return data;
    }
}
