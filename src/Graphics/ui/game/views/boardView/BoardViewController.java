package Graphics.ui.game.views.boardView;

import Graphics.controller.controllers.SettlerController;
import Graphics.controller.controllers.SolarSystem;
import Graphics.entity.entities.Settler;
import Graphics.thing.Thing;
import Graphics.ui.game.drawable.drawables.Obstacle;
import Graphics.ui.game.View;
import Graphics.ui.game.UIController;
import Graphics.ui.game.drawable.drawables.Root;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;

public class BoardViewController extends View {

    @FXML
    public Canvas myCanvas;
    @FXML
    public Pane canvasWrapper;
    @FXML
    public ListView settlerList;
    @FXML
    public ListView statusList;

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
            protected void updateItem(Settler p, boolean empty){
                super.updateItem(p, empty);
                if(empty || p == null || p.getName() == null){
                    setText("");
                }
                else{
                    setText(p.getName() + (p.isActive() ? " Free." : " Worked."));
                }
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
                obstacles.add(new Obstacle( (int) Math.ceil(cX + layerR * Math.cos(phi)), (int) Math.ceil(cY + layerR * Math.sin(phi)), things.get(processedNodes++)));
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
                    if (r.containsAll(o1, o2)) {
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
            int corrected = 0;
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

    private double pointBoarByAvgNei() {
        double sum = 0;
        for (Root r : roots) {
            sum += r.getDistance();
        }
        return sum / roots.size();
    }

    @FXML
    public void switchAsteroid() {
        UIController.getInstance().switchView();
    }

    public void rePaint() {
        myCanvas.getGraphicsContext2D().clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());

        myCanvas.getGraphicsContext2D().setFill(Color.BEIGE);
        myCanvas.getGraphicsContext2D().fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());

        roots.forEach(r -> r.draw(myCanvas));
        obstacles.forEach(o -> o.draw(myCanvas));
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
                    break;
                }
            }
            rePaint();
        }
    }

    @FXML
    public void move() {
        System.out.println(settlerList.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void canvasMouseMoved(MouseEvent mouseEvent) {
        Obstacle selected = getObstacleByData(UIController.getInstance().getSelectedThing());
        for (Obstacle o : obstacles) {
            if (o.distance((int) mouseEvent.getX(), (int)mouseEvent.getY()) < 15) {
                o.setFillColor(Color.LAVENDER);
            } else {
                if (selected != o)
                    o.setFillColor(Color.WHITE);
            }
        }
        rePaint();
    }
}
