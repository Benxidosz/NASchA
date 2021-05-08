package Graphics.ui.game.views.boardView;

import Graphics.ui.game.View;
import Graphics.ui.game.UIController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class BoardViewController extends View {

    @FXML
    public Canvas myCanvas;
    @FXML
    public Pane canvasWrapper;
    @FXML
    public ListView settlerList;
    @FXML
    public ListView statusList;

    public BoardViewController() throws IOException {
        super("boardView.fxml");
        title = "Board View";
    }

    @FXML
    void initialize() {
        myCanvas.widthProperty().bind(canvasWrapper.widthProperty());
        myCanvas.heightProperty().bind(canvasWrapper.heightProperty());

        settlerList.getItems().add("asd1");
        settlerList.getItems().add("asd2");
        settlerList.getItems().add("asd3");
    }

    @FXML
    public void switchAsteroid() {
        UIController.getInstance().switchView();
    }

    public void rePaint() {
        myCanvas.getGraphicsContext2D().clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());

        myCanvas.getGraphicsContext2D().setFill(Color.BEIGE);
        myCanvas.getGraphicsContext2D().fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
    }

    @FXML
    public void canvasClick(MouseEvent mouseEvent) {
        rePaint();
    }

    @FXML
    public void move(ActionEvent actionEvent) {
        System.out.println(settlerList.getSelectionModel().getSelectedItem());
    }
}
