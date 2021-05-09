package Graphics.ui.game.drawable.drawables;

import Graphics.App;
import Graphics.observable.thing.Thing;
import Graphics.observable.thing.Asteroid;
import Graphics.observable.thing.TeleportGate;
import Graphics.ui.game.drawable.Drawable;
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
    private boolean selected;

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
        if (selected)
            gc.setFill(Color.RED);
        else
            gc.setFill(fillColor);
        gc.fillOval(posX - 15, posY - 15, 30, 30);

        gc.setFill(lineColor);
        gc.strokeOval(posX - 15, posY - 15, 30, 30);
    }

    public void draw(Canvas canvas, TeleportGate gate) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (selected)
            gc.setFill(Color.RED);
        else if (data.getEntities().size() > 0 && App.isTestMode()) {
            gc.setFill(Color.PINK);
        } else
            gc.setFill(fillColor);
        gc.fillRect(posX - 15, posY - 15, 30, 30);

        gc.setFill(lineColor);
        gc.strokeRect(posX - 15, posY - 15, 30, 30);
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

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
