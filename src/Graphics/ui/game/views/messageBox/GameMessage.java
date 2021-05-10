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

    public void showAndWait(String res) {
        result.setText(res);
        stage.showAndWait();
    }

    public void newGame() {
        data = "new";
        stage.close();
    }

    public void exit() {
        data = "exit";
        stage.close();
    }

    public String getData() {
        return data;
    }
}
