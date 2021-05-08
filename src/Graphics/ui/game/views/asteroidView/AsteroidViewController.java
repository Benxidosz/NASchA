package Graphics.ui.game.views.asteroidView;

import Graphics.Inventory;
import Graphics.thing.Thing;
import Graphics.ui.game.View;
import Graphics.ui.game.UIController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class AsteroidViewController extends View {
    @FXML
    public Canvas myCanvas;
    @FXML
    public Pane canvasWrapper;
    @FXML
    public TreeView entitiesTree;

    public AsteroidViewController() throws IOException {
        super("asteroidView.fxml");
        title = "Asteroid View";
    }

    @FXML
    void initialize() {
        myCanvas.widthProperty().bind(canvasWrapper.widthProperty());
        myCanvas.heightProperty().bind(canvasWrapper.heightProperty());
    }

    public void refreshTree() {
        Thing selected = UIController.getInstance().getSelectedThing();
        if (selected == null) return;
        TreeItem<String> rootEntitiItem = new TreeItem<>("");
        rootEntitiItem.setExpanded(true);
        selected.getEntities().forEach((e) -> {
            TreeItem<String> item = new TreeItem<>(e.getName());
            Inventory tmpInventory = e.getInventory();
            if (tmpInventory != null) {
                tmpInventory.getMaterials().forEach((m) -> {
                    item.getChildren().add(new TreeItem<String>(m.getName()));
                });
            }
            rootEntitiItem.getChildren().add(item);
        });
        entitiesTree.setRoot(rootEntitiItem);
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
