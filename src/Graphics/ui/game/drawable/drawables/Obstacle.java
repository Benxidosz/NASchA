package Graphics.ui.game.drawable.drawables;

import Graphics.App;
import Graphics.observable.thing.things.MainAsteroid;
import Graphics.observable.thing.Thing;
import Graphics.observable.thing.things.Asteroid;
import Graphics.observable.thing.things.TeleportGate;
import Graphics.ui.game.UIController;
import Graphics.ui.game.drawable.Drawable;
import Graphics.ui.game.views.DrawState;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedHashSet;

public class Obstacle extends Drawable {
    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    private int posX;
    private int posY;
    private final Thing data;
    private final LinkedHashSet<Obstacle> neighbours;

    public Obstacle(int posX, int posY, Thing data) {
        super(Color.WHITE, Color.BLACK);

        this.posX = posX;
        this.posY = posY;
        this.data = data;
        neighbours = new LinkedHashSet<>();
    }

    @Override
    public void setFillColor(Color color) {
        super.setFillColor(color);
        if (color == Color.WHITE && data.getEntities().size() > 0 && App.isTestMode())
            fillColor = Color.PINK;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void draw(Canvas canvas) {
        data.observe(canvas, this);
    }

    public void draw(Canvas canvas, Asteroid asteroid) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int r = UIController.getObstacleRadius();
        if (state.equals(DrawState.selected))
            gc.drawImage(UIController.getSprite("asteroid_selected"), posX - r, posY - r, 2 * r, 2 * r);
        else if (state.equals(DrawState.idle))
            gc.drawImage(UIController.getSprite("asteroid_idle"), posX - r, posY - r, 2 * r, 2 * r);
        else if (state.equals(DrawState.hoover))
            gc.drawImage(UIController.getSprite("asteroid_hoover"), posX - r, posY - r, 2 * r, 2 * r);
        else
            gc.drawImage(UIController.getSprite("asteroid_idle"), posX - r, posY - r, 2 * r, 2 * r);
    }

    public void draw(Canvas canvas, TeleportGate gate) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int r = UIController.getObstacleRadius();
        if (state.equals(DrawState.idle))
            gc.drawImage(UIController.getSprite("gate_idle"), posX - r, posY - r, 2 * r, 2 * r);
        else if (state.equals(DrawState.hoover))
            gc.drawImage(UIController.getSprite("gate_hoover"), posX - r, posY - r, 2 * r, 2 * r);
        else if (state.equals(DrawState.selected))
            gc.drawImage(UIController.getSprite("gate_selected"), posX - r, posY - r, 2 * r, 2 * r);
        else
            gc.drawImage(UIController.getSprite("gate_idle"), posX - r, posY - r, 2 * r, 2 * r);
    }

    public void draw(Canvas canvas, MainAsteroid main) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int r = UIController.getObstacleRadius();
        if (state.equals(DrawState.idle))
            gc.drawImage(UIController.getSprite("main_idle"), posX - r, posY - r, 2 * r, 2 * r);
        else if (state.equals(DrawState.hoover))
            gc.drawImage(UIController.getSprite("main_hoover"), posX - r, posY - r, 2 * r, 2 * r);
        else if (state.equals(DrawState.selected))
            gc.drawImage(UIController.getSprite("main_selected"), posX - r, posY - r, 2 * r, 2 * r);
        else
            gc.drawImage(UIController.getSprite("main_idle"), posX - r, posY - r, 2 * r, 2 * r);
    }

    public boolean equalData(Thing d) {
        return data == d;
    }

    public void addNei(Obstacle o) {
        neighbours.add(o);
    }

    public double getAvgDistance() {
        double sum = 0;
        for (Obstacle nei : neighbours)
            sum += distance(nei);
        return sum / neighbours.size();
    }

    public double distance(Obstacle o) {
        return Math.sqrt(Math.pow(posX - o.posX, 2) + Math.pow(posY - o.posY, 2));
    }

    public double distance(int x, int y) {
        return Math.sqrt(Math.pow(posX - x, 2) + Math.pow(posY - y, 2));
    }

    public void swapPos(Obstacle o) {
        int tmpX = posX;
        int tmpY = posY;
        posX = o.getPosX();
        posY = o.getPosY();
        o.setPosX(tmpX);
        o.setPosY(tmpY);
    }

    public Thing getData() {
        return data;
    }

    public LinkedHashSet<Obstacle> getNeighbours() {
        return neighbours;
    }
}
