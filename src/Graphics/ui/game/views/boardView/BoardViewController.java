package Graphics.ui.game.views.boardView;

import Graphics.Main;
import Graphics.controller.controllers.SettlerController;
import Graphics.controller.controllers.SolarSystem;
import Graphics.observable.entity.entities.Settler;
import Graphics.observable.thing.Thing;
import Graphics.observable.thing.things.TeleportGate;
import Graphics.ui.game.drawable.drawables.Obstacle;
import Graphics.ui.game.View;
import Graphics.ui.game.UIController;
import Graphics.ui.game.drawable.drawables.Root;
import Graphics.ui.game.drawable.DrawState;
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

    private int tmpSettlerNum;

    private LinkedHashSet<Obstacle> obstacles;
    private LinkedHashSet<Root> roots;

    public BoardViewController() throws IOException {
        super("boardView.fxml");
        title = "Board View";
        tmpSettlerNum = SettlerController.getInstance().getSettlers().size();
    }

    private Obstacle getObstacleByData(Thing data) {
        if (data == null)
            return null;

        Obstacle o1 = null;
        for (Obstacle o : obstacles)
            if (o.equalThingData(data)) {
                o1 = o;
                break;
            }

        return o1;
    }

    /**
     * Itt called when the the windows start to initialize.
     */
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
                    if (SettlerController.getInstance().getSettlers().contains(p)) {
                        setDisable(false);
                        setText(p.getName() + (p.isActive() ? " Free." : " Worked."));
                    } else {
                        setDisable(true);
                        setText(p.getName() + "died.");
                    }
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
        double rDiff = things.size() < 30 ? 110 : 105;
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
        int iter = 0;
        while (tmpPoint != pointBoarByAvgNei() && iter < 1000) {
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
            ++iter;
        }
    }

    @Override
    public void refresh() {
        settlerList.refresh();
        int settlerNum = SettlerController.getInstance().getSettlers().size();
        if (tmpSettlerNum != settlerNum) {
            settlerList.getItems().clear();
            settlerList.getItems().addAll(SettlerController.getInstance().getSettlers());
        }
        tmpSettlerNum = settlerNum;
    }

    private double pointBoarByAvgNei() {
        double sum = 0;
        for (Obstacle o : obstacles) {
            sum += o.getAvgDistance();
        }
        return sum / roots.size();
    }

    /**
     * It is called when the selected member of the list updated.
     */
    private void selectedRefreshed() {
        Thing selected = UIController.getInstance().getSelectedThing();
        Settler selectedSettler = (Settler) settlerList.getSelectionModel().getSelectedItem();
        statusText.setText((selected != null ? selected.List() : "") + (selectedSettler != null ? selectedSettler.List() : ""));
    }

    /**
     * Select a free
     * @param selectedSettler
     */
    private void selectAnotherSettler(Settler selectedSettler) {
        boolean selected = false;
        for (Settler settler : SettlerController.getInstance().getSettlers()) {
            if (settler.isActive() && SettlerController.getInstance().getSettlers().contains(settler)) {
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

    /**
     * Reload the obstacles from the backend.
     */
    private void reloadObstacles() {
        LinkedList<Thing> things = SolarSystem.getInstance().getThings();
        for (Thing t : things) {
            for (Iterator<Obstacle> iter = obstacles.iterator(); iter.hasNext();) {
                Obstacle obstacle = iter.next();
                if (!SolarSystem.getInstance().getThings().contains(obstacle.getThingData())) {
                    roots.removeIf(root -> root.contains(obstacle));
                    ArrayList<Thing> neighbours = obstacle.getThingData().getNeighbour();
                    for (Thing nei1 : neighbours) {
                        Obstacle oNei1 = getObstacleByData(nei1);
                        for (Thing nei2 : neighbours) {
                            Obstacle oNei2 = getObstacleByData(nei2);
                            if (oNei1 != oNei2 && oNei1 != null && oNei2 != null) {
                                roots.add(new Root(oNei1, oNei2));
                            }
                        }
                    }
                    iter.remove();
                }
            }

            boolean loaded = false;

            for (Obstacle o : obstacles)
                if (o.equalThingData(t)) {
                    loaded = true;
                    break;
                }

            if (!loaded) {
                Thing randomNei = t.getNeighbour().get(0);
                Obstacle o = getObstacleByData(randomNei);
                if (o != null) {
                    double phi = Main.rng.nextDouble() * 360;
                    int r = UIController.getObstacleRadius() * 2 + 20;
                    double x = o.getPosX() + Math.cos(phi) * r;
                    double y = o.getPosY() + Math.sin(phi) * r;
                    Obstacle newObstacle = new Obstacle((int) x, (int) y, t);
                    obstacles.add(newObstacle);
                    roots.add(new Root(o, newObstacle));
                }
            }

            for (Obstacle o1 : obstacles) {
                for (Obstacle o2 : obstacles) {
                    Thing data1 = o1.getThingData();
                    Thing data2 = o2.getThingData();
                    if (o1.getNeighbours().contains(o2) && o2.getNeighbours().contains(o1)
                            && !data1.getNeighbour().contains(data2) && !data2.getNeighbour().contains(data1)) {
                        roots.removeIf(root -> root.contains(o1, o2));
                        o1.rmNei(o2);
                        o2.rmNei(o1);

                        for (Obstacle oNeiCheck : obstacles) {
                            Thing dataNeiCheck = oNeiCheck.getThingData();
                            fixNei(o1, data1, oNeiCheck, dataNeiCheck);

                            fixNei(o2, data2, oNeiCheck, dataNeiCheck);

                            moveObstacle(data1);

                            moveObstacle(data2);
                        }
                    }
                }
            }
        }
    }

    /**
     * Try to move an obstacle.
     * @param thing
     */
    public void moveObstacle(Thing thing) {
        thing.move(this);
    }

    /**
     * Empty function, the moveMe function don't do anything, if the Thing isn't a TeleportGate
     * @param thing A thing
     */
    public void moveMe(Thing thing) {
        return;
    }

    /**
     * The teleport gate is located to a Thing
     * @param gate The teleport gate which is located
     */
    public void moveMe(TeleportGate gate) {
        Obstacle o = getObstacleByData(gate);
        Thing randNei = gate.randomNeighbour();
        Obstacle oNei = getObstacleByData(randNei);
        if (oNei != null) {
            double phi = Main.rng.nextDouble() * 360;
            int r = UIController.getObstacleRadius() * 2 + 20;
            double x = oNei.getPosX() + Math.cos(phi) * r;
            double y = oNei.getPosY() + Math.sin(phi) * r;
            o.setPosX((int)x);
            o.setPosY((int)y);
        }
    }

    /**
     * Check if the two obstacles data is neighbour, but the Obstacle not, if it happen, fix it, and make them neighbour.
     * Correct the frontend, with the backend data.
     * @param o1 first Obstacle.
     * @param data1 first Obstacle's data.
     * @param oNeiCheck the possible neighbour obstacle.
     * @param dataNeiCheck the possible neighbour obstacle's data.
     */
    private void fixNei(Obstacle o1, Thing data1, Obstacle oNeiCheck, Thing dataNeiCheck) {
        if (!o1.getNeighbours().contains(oNeiCheck) && !oNeiCheck.getNeighbours().contains(o1)
                && data1.getNeighbour().contains(dataNeiCheck) && dataNeiCheck.getNeighbour().contains(data1)) {
            roots.add(new Root(o1, oNeiCheck));
            o1.addNei(oNeiCheck);
            oNeiCheck.addNei(o1);
        }
    }

    /**
     * It is rePaint the canvas.
     */
    @Override
    public void rePaint() {
        reloadObstacles();
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        gc.drawImage(UIController.getSprite("board"), 0, 0, myCanvas.getWidth(), myCanvas.getHeight());

        roots.forEach(r -> r.draw(myCanvas));
        obstacles.forEach(o -> o.draw(myCanvas));

        Settler selectedSettler = (Settler) settlerList.getSelectionModel().getSelectedItem();
        if (selectedSettler != null) {
            Obstacle loc = getObstacleByData(selectedSettler.getLocation());
            gc.setFill(Color.GREEN);
            int r = UIController.getObstacleRadius() + 5;
            gc.strokeOval(loc.getPosX() - r, loc.getPosY() - r, 2 * r, 2 * r);
        }
        turnLabel.setText(UIController.getInstance().getTurnNum() + ". Turn");
        int untilEruption = SolarSystem.getInstance().getUntilEruption();
        if (untilEruption < 0) {
            eruptionTurnLabel.setText("No Danger!");
        } else {
            eruptionTurnLabel.setText("Eruption in\n" + untilEruption + " turn.");
        }
    }

    /**
     * Switches to asteroid view from board view after pressing To Asteroid View button
     */
    @FXML
    public void switchAsteroid() {
        UIController.getInstance().switchView();
    }

    /**
     * The canvas is clicked by the mouse
     * @param mouseEvent the mouseevent
     */
    @FXML
    public void canvasClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            Obstacle selected = getObstacleByData(UIController.getInstance().getSelectedThing());
            for (Obstacle o : obstacles) {
                if (o.distance((int) mouseEvent.getX(), (int) mouseEvent.getY()) < 15) {
                    if (selected != null) {
                        selected.setState(DrawState.idle);
                        Thing pair = selected.getThingData().getPair();
                        if (pair != null) {
                            Obstacle pairO = getObstacleByData(pair);
                            if (pairO != null)
                                pairO.setState(DrawState.idle);
                        }
                    }
                    o.setState(DrawState.selected);
                    Thing pair = o.getThingData().getPair();
                    if (pair != null) {
                        Obstacle pairO = getObstacleByData(pair);
                        if (pairO != null)
                            pairO.setState(DrawState.pairActive);
                    }
                    UIController.getInstance().setSelectedThing(o.getThingData());
                    selectedRefreshed();
                    break;
                }
            }
            rePaint();
        }
    }

    /**
     * The settler moves after pressing Move button.
     */
    @FXML
    public void move() {
        Settler selectedSettler = (Settler) settlerList.getSelectionModel().getSelectedItem();
        Obstacle selectedObstacle = getObstacleByData(UIController.getInstance().getSelectedThing());
        if (selectedSettler != null && selectedObstacle != null) {
            SettlerController.getInstance().handleCommand("Move " + selectedSettler.getName() + " " + selectedObstacle.getThingData().getName());
            rePaint();
            settlerList.refresh();
            selectAnotherSettler(selectedSettler);
            UIController.checkFree(selectedSettler.getName());
        }
    }

    /**
     * The mouse is moved in canvas
     * @param mouseEvent the mouseevent
     */
    @FXML
    public void canvasMouseMoved(MouseEvent mouseEvent) {
        Obstacle selected = getObstacleByData(UIController.getInstance().getSelectedThing());
        Obstacle pair = null;
        if (selected != null)
            pair = getObstacleByData(selected.getThingData().getPair());
        for (Obstacle o : obstacles) {
            if (o.distance((int) mouseEvent.getX(), (int) mouseEvent.getY()) < 15 && selected != o && pair != o) {
                o.setState(DrawState.hoover);
            } else if (selected != o && pair != o)
                o.setState(DrawState.idle);
        }
        rePaint();
    }

    /**
     * Ends the settlers turn after pressing End Turn button.
     */
    @FXML
    public void endTurnButton() {
        SettlerController.getInstance().handleCommand("endTurn");
        rePaint();
        Settler selectedSettler = (Settler) settlerList.getSelectionModel().getSelectedItem();
        settlerList.refresh();
        selectAnotherSettler(selectedSettler);
    }

    /**
     * It is called, when the user move the mouse on the whole window.
     * @param mouseEvent
     */
    @FXML
    public void boardViewMouseMoved(MouseEvent mouseEvent) {
        int settlerNum = SettlerController.getInstance().getSettlers().size();
        if (tmpSettlerNum != settlerNum) {
            settlerList.getItems().clear();
            settlerList.getItems().addAll(SettlerController.getInstance().getSettlers());
        }
        tmpSettlerNum = settlerNum;
    }
}
