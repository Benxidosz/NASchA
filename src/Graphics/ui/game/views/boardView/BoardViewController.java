package Graphics.ui.game.views.boardView;

import Graphics.Main;
import Graphics.controller.controllers.SettlerController;
import Graphics.controller.controllers.SolarSystem;
import Graphics.observable.entity.entities.Settler;
import Graphics.observable.thing.Thing;
import Graphics.ui.game.drawable.drawables.Obstacle;
import Graphics.ui.game.View;
import Graphics.ui.game.UIController;
import Graphics.ui.game.drawable.drawables.Root;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.*;

public class BoardViewController extends View {

    @FXML
    public Canvas myCanvas;
    @FXML
    public Pane canvasWrapper;
    @FXML
    public ListView settlerList;
    @FXML
    public TextArea statusText;
    @FXML
    public Label turnLabel;
    @FXML
    public Label eruptionTurnLabel;

    private LinkedHashSet<Obstacle> obstacles;
    private LinkedHashSet<Root> roots;

    public BoardViewController() throws IOException {
        super("boardView.fxml");
        title = "Board View";
    }

    private Obstacle getObstacleByData(Thing data) {
        if (data == null)
            return null;

        Obstacle o1 = null;
        for (Obstacle o : obstacles)
            if (o.equalData(data)) {
                o1 = o;
                break;
            }

        return o1;
    }

    @FXML
    void initialize() {
        obstacles = new LinkedHashSet<>();
        roots = new LinkedHashSet<>();

        myCanvas.widthProperty().bind(canvasWrapper.widthProperty());
        myCanvas.heightProperty().bind(canvasWrapper.heightProperty());

        settlerList.setCellFactory(param -> new ListCell<Settler>() {
            @Override
            protected void updateItem(Settler p, boolean empty) {
                super.updateItem(p, empty);
                if (empty || p == null || p.getName() == null) {
                    setText("");
                } else {
                    setText(p.getName() + (p.isActive() ? " Free." : " Worked."));
                }
                rePaint();
                selectedRefreshed();
            }
        });

        for (Settler settler : SettlerController.getInstance().getSettlers()) {
            settlerList.getItems().add(settler);
        }

        ArrayList<Thing> things = new ArrayList<>(SolarSystem.getInstance().getThings());
        things.sort(Comparator.comparingInt(t -> -t.getNeighbour().size()));

        //init vars
        int diffLayers = 2;
        int nodeInLayer = 5;
        int processedNodes = 0;
        double rDiff = things.size() < 25 ? 110 : 100;
        double layerR = rDiff;
        double cX = canvasWrapper.getPrefWidth() / 2 + 75;
        double cY = canvasWrapper.getPrefHeight() / 2;

        while (processedNodes < things.size()) {
            int tmpNum = Math.min(nodeInLayer, things.size() - processedNodes);

            for (int i = 0; i < tmpNum; ++i) {
                double phi = ((i * 2.0f * Math.PI) / tmpNum);
                obstacles.add(new Obstacle((int) Math.ceil(cX + layerR * Math.cos(phi)), (int) Math.ceil(cY + layerR * Math.sin(phi)), things.get(processedNodes++)));
            }

            nodeInLayer *= diffLayers;
            layerR += rDiff;
        }

        for (Thing t : things) {
            Obstacle o1 = getObstacleByData(t);

            if (o1 == null)
                return;

            for (Thing nei : t.getNeighbour()) {
                Obstacle o2 = getObstacleByData(nei);
                if (o2 == null)
                    break;

                boolean has = false;
                for (Root r : roots)
                    if (r.contains(o1, o2)) {
                        has = true;
                        break;
                    }

                if (!has)
                    roots.add(new Root(o1, o2));
            }
        }

        double tmpPoint = 0;
        while (tmpPoint != pointBoarByAvgNei()) {
            tmpPoint = pointBoarByAvgNei();
            for (Obstacle o1 : obstacles) {
                double bestDisDelta = 0;
                Obstacle best = null;

                for (Obstacle o2 : obstacles) {
                    if (o1 != o2) {
                        double disAvg1 = o1.getAvgDistance();
                        double disAvg2 = o2.getAvgDistance();

                        o1.swapPos(o2);

                        double tmpDisAvg1 = o1.getAvgDistance();
                        double tmpDisAvg2 = o2.getAvgDistance();

                        double disDelta = (tmpDisAvg1 - disAvg1) + (tmpDisAvg2 - disAvg2);

                        if (disDelta < bestDisDelta) {
                            bestDisDelta = disDelta;
                            best = o2;
                        }

                        o1.swapPos(o2);
                    }
                }

                if (bestDisDelta < 0 && best != null) {
                    o1.swapPos(best);
                }
            }
        }
    }

    @Override
    public void refresh() {
        for (var item : settlerList.getItems()) {
            settlerList.getSelectionModel().select(item);
        }
        selectAnotherSettler(null);
    }

    private double pointBoarByAvgNei() {
        double sum = 0;
        for (Root r : roots) {
            sum += r.getDistance();
        }
        return sum / roots.size();
    }

    private void selectedRefreshed() {
        Thing selected = UIController.getInstance().getSelectedThing();
        Settler selectedSettler = (Settler) settlerList.getSelectionModel().getSelectedItem();
        statusText.setText((selected != null ? selected.List() : "") + (selectedSettler != null ? selectedSettler.List() : ""));
    }

    private void selectAnotherSettler(Settler selectedSettler) {
        boolean selected = false;
        for (Settler settler : SettlerController.getInstance().getSettlers()) {
            if (settler.isActive()) {
                settlerList.getSelectionModel().select(settler);
                selected = true;
                break;
            }
        }
        if (!selected) {
            for (Settler settler : SettlerController.getInstance().getSettlers()) {
                if (settler != selectedSettler) {
                    settlerList.getSelectionModel().select(settler);
                    break;
                }
            }
        }
    }

    private void reloadObstacles() {
        LinkedList<Thing> things = SolarSystem.getInstance().getThings();
        for (Thing t : things) {
            boolean loaded = false;

            for (Iterator<Obstacle> iter = obstacles.iterator(); iter.hasNext();) {
                Obstacle obstacle = iter.next();
                if (!SolarSystem.getInstance().getThings().contains(obstacle.getData())) {
                    roots.removeIf(root -> root.contains(obstacle));
                    ArrayList<Thing> neighbours = obstacle.getData().getNeighbour();
                    for (Thing nei1 : neighbours) {
                        Obstacle oNei1 = getObstacleByData(nei1);
                        for (Thing nei2 : neighbours) {
                            Obstacle oNei2 = getObstacleByData(nei2);
                            if (oNei1 != oNei2) {
                                roots.add(new Root(oNei1, oNei2));
                            }
                        }
                    }
                    iter.remove();
                }
            }

            for (Obstacle o : obstacles)
                if (o.equalData(t)) {
                    loaded = true;
                    break;
                }

            if (!loaded) {
                Thing randomNei = t.getNeighbour().get(0);
                Obstacle o = getObstacleByData(randomNei);
                if (o != null) {
                    double phi = Main.rng.nextDouble() * 360;
                    double x = o.getPosX() + Math.cos(phi) * 60;
                    double y = o.getPosY() + Math.sin(phi) * 60;
                    Obstacle newObstacle = new Obstacle((int) x, (int) y, t);
                    obstacles.add(newObstacle);
                    roots.add(new Root(o, newObstacle));
                }
            }
        }
    }

    private void fixList() {
        settlerList.getItems().removeIf(s -> !SettlerController.getInstance().getSettlers().contains(s));
    }

    @Override
    public void rePaint() {
        reloadObstacles();
        fixList();
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());

        gc.setFill(Color.BEIGE);
        gc.fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());

        roots.forEach(r -> r.draw(myCanvas));
        obstacles.forEach(o -> o.draw(myCanvas));

        Settler selectedSettler = (Settler) settlerList.getSelectionModel().getSelectedItem();
        if (selectedSettler != null) {
            Obstacle loc = getObstacleByData(selectedSettler.getLocation());
            gc.setFill(Color.GREEN);
            gc.strokeOval(loc.getPosX() - 20, loc.getPosY() - 20, 40, 40);
        }
        turnLabel.setText(UIController.getInstance().getTurnNum() + ". Turn");
        int untilEruption = SolarSystem.getInstance().getUntilEruption();
        if (untilEruption < 0) {
            eruptionTurnLabel.setText("No Danger!");
        } else {
            eruptionTurnLabel.setText("Eruption in\n" + untilEruption + " turn.");
        }
    }

    @FXML
    public void switchAsteroid() {
        UIController.getInstance().switchView();
    }

    @FXML
    public void canvasClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            Obstacle selected = getObstacleByData(UIController.getInstance().getSelectedThing());
            for (Obstacle o : obstacles) {
                if (o.distance((int) mouseEvent.getX(), (int) mouseEvent.getY()) < 15) {
                    if (selected != null)
                        selected.setSelected(false);
                    o.setSelected(true);
                    UIController.getInstance().setSelectedThing(o.getData());
                    selectedRefreshed();
                    break;
                }
            }
            rePaint();
        }
    }

    @FXML
    public void move() {
        Settler selectedSettler = (Settler) settlerList.getSelectionModel().getSelectedItem();
        Obstacle selectedObstacle = getObstacleByData(UIController.getInstance().getSelectedThing());
        if (selectedSettler != null && selectedObstacle != null) {
            SettlerController.getInstance().handleCommand("Move " + selectedSettler.getName() + " " + selectedObstacle.getData().getName());
            rePaint();
            selectAnotherSettler(selectedSettler);
        }
    }

    @FXML
    public void canvasMouseMoved(MouseEvent mouseEvent) {
        Obstacle selected = getObstacleByData(UIController.getInstance().getSelectedThing());
        for (Obstacle o : obstacles) {
            if (o.distance((int) mouseEvent.getX(), (int) mouseEvent.getY()) < 15) {
                o.setFillColor(Color.LAVENDER);
            } else {
                if (selected != o)
                    o.setFillColor(Color.WHITE);
            }
        }
        rePaint();
    }

    @FXML
    public void endTurnButton() {
        SettlerController.getInstance().handleCommand("endTurn");
        rePaint();
        Settler selectedSettler = (Settler) settlerList.getSelectionModel().getSelectedItem();
        refresh();
        selectAnotherSettler(selectedSettler);
    }
}
