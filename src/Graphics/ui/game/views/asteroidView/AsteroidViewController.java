package Graphics.ui.game.views.asteroidView;

import Graphics.Inventory;
import Graphics.controller.controllers.RobotController;
import Graphics.controller.controllers.SettlerController;
import Graphics.controller.controllers.SolarSystem;
import Graphics.controller.controllers.UfoController;
import Graphics.material.Material;
import Graphics.observable.entity.Entity;
import Graphics.observable.entity.entities.Robot;
import Graphics.observable.entity.entities.Settler;
import Graphics.observable.entity.entities.Ufo;
import Graphics.observable.thing.Thing;
import Graphics.observable.thing.things.TeleportGate;
import Graphics.ui.game.View;
import Graphics.ui.game.UIController;
import Graphics.ui.game.drawable.drawables.Obstacle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class AsteroidViewController extends View {
    /**
     * The canvas where the Asteroid view be drawn.
     */
    @FXML
    public Canvas myCanvas;

    /**
     * The panel what contains the canvas.
     */
    @FXML
    public Pane canvasWrapper;

    /**
     * The tree views, what shows the entities, and their possible inventory.
     */
    @FXML
    public TreeView entitiesTree;

    /**
     * The TextArea where the status message showed up.
     */
    @FXML
    public TextArea statusText;

    /**
     * The obstacles what represents the Entities around the asteroids.
     */
    private LinkedList<Obstacle> obstacles;

    /**
     * The constructor, set the title to the right title.
     * @throws IOException The super constructor can thrown it
     */
    public AsteroidViewController() throws IOException {
        super("asteroidView.fxml");
        title = "Asteroid View";
        obstacles = new LinkedList<>();
    }

    /**
     * Itt called when the the windows start to initialize.
     */
    @FXML
    void initialize() {
        myCanvas.widthProperty().bind(canvasWrapper.widthProperty());
        myCanvas.heightProperty().bind(canvasWrapper.heightProperty());

        entitiesTree.setCellFactory(param -> new TreeCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty || "".equals(item)) {
                    setText("");
                } else {
                    setText(item);
                }
                selectedChanged();
            }
        });
    }

    private void selectedChanged() {
        TreeItem item = (TreeItem) entitiesTree.getSelectionModel().getSelectedItem();
        String name = null;
        TreeItem parent = null;

        if (item != null) {
            name = (String) item.getValue();
            parent = item.getParent();
        }
        Settler selectedSettler = SettlerController.getInstance().getSettlerByName(name);
        Robot selectedRobot = RobotController.getInstance().getSettlerByName(name);
        Ufo selectedUfo = UfoController.getInstance().getSettlerByName(name);
        Thing selected = UIController.getInstance().getSelectedThing();

        TeleportGate selectedGate = null;

        if (parent != null && parent != entitiesTree.getRoot()) {
            String parentName = (String) parent.getValue();
            selectedSettler = SettlerController.getInstance().getSettlerByName(parentName);
            if (selectedSettler != null)
                selectedGate = selectedSettler.getGateByName(name);
        }

        statusText.setText((selected != null ? selected.List() : "") +
                (selectedSettler != null ? selectedSettler.List() : "") +
                (selectedUfo != null ? selectedUfo.List() : "") +
                (selectedRobot != null ? selectedRobot.List() : "") +
                (selectedGate != null ? selectedGate.List() : ""));
        rePaint();
    }

    public void refresh() {
        Thing selected = UIController.getInstance().getSelectedThing();
        if (selected == null) return;
        TreeItem<String> rootEntitiItem = new TreeItem<>("");
        rootEntitiItem.setExpanded(true);
        selected.getEntities().forEach((e) -> {
            TreeItem<String> item = new TreeItem<>(e.getName());
            Inventory tmpInventory = e.getInventory();
            if (tmpInventory != null) {
                tmpInventory.getMaterials().forEach((m) -> item.getChildren().add(new TreeItem<>(m.getName())));
            }

            ArrayList<TeleportGate> gates = e.getGates();
            if (gates != null)
                gates.forEach(g -> item.getChildren().add(new TreeItem<>(g.getName())));

            rootEntitiItem.getChildren().add(item);
        });
        entitiesTree.setRoot(rootEntitiItem);

        obstacles = new LinkedList<>();
        ArrayList<Entity> entities = selected.getEntities();
        double dAlfa = (2 * Math.PI) / entities.size();
        double alfa = 0;
        double r = 200;
        double cX = canvasWrapper.getWidth() / 2;
        double cY = canvasWrapper.getHeight() / 2;
        for (Entity entity : entities) {
            double phi = Math.toDegrees(alfa) + 90;
            double x = cX + r * Math.cos(alfa) + 50 * Math.cos(alfa);
            double y = cY + r * Math.sin(alfa) + 50 * Math.sin(alfa);
            Obstacle obstacle = new Obstacle((int) x, (int) y, entity);
            obstacle.setRotate(new Rotate(phi, x, y));
            obstacles.add(obstacle);
            alfa += dAlfa;
        }
    }

    @Override
    public void rePaint() {
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());

        gc.setFill(Color.BEIGE);
        gc.fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());

        Thing selectedThing = UIController.getInstance().getSelectedThing();

        if (selectedThing == null)
            return;

        double r = 200;
        double centerX = canvasWrapper.getWidth() / 2;
        double centerY = canvasWrapper.getHeight() / 2;
        gc.strokeOval(centerX - r,centerY - r, 2 * r, 2 *r );
        gc.strokeOval(centerX - r / 3,centerY - r / 3, 2 * (r / 3), 2 * (r / 3));
        gc.strokeText(selectedThing.getLayer() + " layer left", centerX - 30, centerY - 150);
        gc.setFont(new Font(15));
        Material mat = selectedThing.getCore();
        if (mat != null)
            gc.strokeText(mat.getName(), centerX-7, centerY+7);
        else
            gc.strokeText("empty", centerX-7, centerY+7);

        obstacles.forEach(obstacle -> {
            TreeItem<String> item = (TreeItem<String>) entitiesTree.getSelectionModel().getSelectedItem();
            obstacle.draw(myCanvas);
            if (item != null) {
                Settler selected = SettlerController.getInstance().getSettlerByName(item.getValue());
                if (selected != null && obstacle.getEntityData() == selected) {
                    gc.save();
                    Rotate rotate = obstacle.getRotate();
                    gc.setTransform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(), rotate.getTy());
                    gc.setFill(Color.GREEN);
                    gc.strokeOval(obstacle.getPosX() - 25, obstacle.getPosY() - 50, 50, 100);
                    gc.restore();
                }
            }
        });
    }

    @FXML
    public void toBoard() {
        UIController.getInstance().switchView();
    }

    @FXML
    public void canvasClick(MouseEvent mouseEvent) {
        rePaint();
    }

    @FXML
    public void mine() {
        TreeItem<String> selectedTreeItem = (TreeItem<String>) entitiesTree.getSelectionModel().getSelectedItem();
        if (selectedTreeItem == null)
            return;
        String selectedEntity = selectedTreeItem.getValue();

        if (selectedEntity != null) {
            SettlerController.getInstance().handleCommand("Mine " + selectedEntity);
            UIController.checkFree(selectedEntity);
        }
        refresh();
    }

    @FXML
    public void buildRobot() {
        TreeItem<String> selectedTreeItem = (TreeItem<String>) entitiesTree.getSelectionModel().getSelectedItem();
        if (selectedTreeItem == null)
            return;
        String selectedEntity = selectedTreeItem.getValue();

        if (selectedEntity != null) {
            SettlerController.getInstance().handleCommand("Buildrobot " + selectedEntity + " " + RobotController.getRobotId());
            UIController.checkFree(selectedEntity);
        }
        refresh();
    }

    @FXML
    public void buildGate() {
        TreeItem<String> selectedTreeItem = (TreeItem<String>) entitiesTree.getSelectionModel().getSelectedItem();
        if (selectedTreeItem == null)
            return;
        String selectedEntity = selectedTreeItem.getValue();

        if (selectedEntity != null) {
            SettlerController.getInstance().handleCommand("Buildgate " + selectedEntity + " " + SolarSystem.getTeleportGateId());
            UIController.checkFree(selectedEntity);
        }
        refresh();
    }

    @FXML
    public void place() {
        TreeItem<String> selectedTreeItem = (TreeItem<String>) entitiesTree.getSelectionModel().getSelectedItem();
        if (selectedTreeItem == null)
            return;

        String selectedItem = selectedTreeItem.getValue();
        TreeItem<String> root = selectedTreeItem.getParent();
        String selectedEntity = root.getValue();

        if (selectedEntity != null) {
            SettlerController.getInstance().handleCommand("Putdown " + selectedEntity + " " + selectedItem);
            UIController.checkFree(selectedEntity);
        }
        refresh();
    }

    @FXML
    public void drill() {
        TreeItem<String> selectedTreeItem = (TreeItem<String>) entitiesTree.getSelectionModel().getSelectedItem();
        if (selectedTreeItem == null)
            return;
        String selectedEntity = selectedTreeItem.getValue();

        if (selectedEntity != null) {
            SettlerController.getInstance().handleCommand("Drill " + selectedEntity);
            UIController.checkFree(selectedEntity);
        }
        refresh();
    }

    @FXML
    public void waitBoard() {
        TreeItem<String> selectedTreeItem = (TreeItem<String>) entitiesTree.getSelectionModel().getSelectedItem();
        if (selectedTreeItem == null)
            return;
        String selectedEntity = selectedTreeItem.getValue();

        if (selectedEntity != null) {
            SettlerController.getInstance().handleCommand("Wait " + selectedEntity);
            UIController.checkFree(selectedEntity);
        }
        refresh();
    }

    public void buildBase() {
        TreeItem<String> selectedTreeItem = (TreeItem<String>) entitiesTree.getSelectionModel().getSelectedItem();
        if (selectedTreeItem == null)
            return;
        String selectedEntity = selectedTreeItem.getValue();

        if (selectedEntity != null) {
            SettlerController.getInstance().handleCommand("Buildbase " + selectedEntity);
            UIController.checkFree(selectedEntity);
        }
        refresh();
    }
}
