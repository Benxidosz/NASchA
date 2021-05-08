package Graphics.ui.game.views.asteroidView;

import Graphics.ui.game.View;
import Graphics.ui.game.UIController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class AsteroidViewController extends View {
    @FXML
    public Canvas myCanvas;
    @FXML
    public Pane canvasWrapper;

    public AsteroidViewController() throws IOException {
        super("asteroidView.fxml");
        title = "Asteroid View";
    }

    @FXML
    void initialize() {
        myCanvas.widthProperty().bind(canvasWrapper.widthProperty());
        myCanvas.heightProperty().bind(canvasWrapper.heightProperty());
    }

    @FXML
    public void switchBoard(ActionEvent actionEvent) {
        UIController.getInstance().switchView();
    }

    @FXML
    public void move(ActionEvent actionEvent) {
    }

    public void toBoard() {
        UIController.getInstance().switchView();
    }

    @FXML
    public void canvasClick(MouseEvent mouseEvent) {
        rePaint();
    }

    public void rePaint() {
        myCanvas.getGraphicsContext2D().clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());

        myCanvas.getGraphicsContext2D().setFill(Color.BEIGE);
        myCanvas.getGraphicsContext2D().fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
    }
}
