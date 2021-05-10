package Graphics.ui.game.drawable.drawables;

import Graphics.App;
import Graphics.observable.Observable;
import Graphics.observable.entity.Entity;
import Graphics.observable.entity.entities.Robot;
import Graphics.observable.entity.entities.Settler;
import Graphics.observable.entity.entities.Ufo;
import Graphics.observable.thing.things.MainAsteroid;
import Graphics.observable.thing.Thing;
import Graphics.observable.thing.things.Asteroid;
import Graphics.observable.thing.things.TeleportGate;
import Graphics.ui.game.UIController;
import Graphics.ui.game.drawable.Drawable;
import Graphics.ui.game.drawable.DrawState;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import java.util.LinkedHashSet;

public class Obstacle extends Drawable {
    private int posX;
    private int posY;
    private final Thing thingData;
    private final Entity entityData;
    private final LinkedHashSet<Obstacle> neighbours;
    private Rotate rotate;

    public Obstacle(int posX, int posY, Thing data) {
        super();
        this.posX = posX;
        this.posY = posY;
        thingData = data;
        entityData = null;
        neighbours = new LinkedHashSet<>();
        rotate = null;
    }

    public Obstacle(int posX, int posY, Entity data) {
        super();
        this.posX = posX;
        this.posY = posY;
        entityData = data;
        thingData = null;
        neighbours = new LinkedHashSet<>();
        rotate = null;
    }

    @Override
    public void setFillColor(Color color) {
        super.setFillColor(color);
        if (color == Color.WHITE && thingData.getEntities().size() > 0 && App.isTestMode())
            fillColor = Color.PINK;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setRotate(Rotate rotate) {
        this.rotate = rotate;
    }

    public void draw(Canvas canvas) {
        if (thingData != null)
            thingData.observe(canvas, this);
        if (entityData != null)
            entityData.observe(canvas, this);
    }

    public void draw(Canvas canvas, Observable observable) {
        throw new UnsupportedOperationException();
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
        else if (state.equals(DrawState.pairActive))
            gc.drawImage(UIController.getSprite("gate_pairActive"), posX - r, posY - r, 2 * r, 2 * r);
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

    public void draw(Canvas canvas, Settler settler) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.save();
        if (rotate != null) {
            gc.setTransform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(), rotate.getTy());
        }
        Image img = UIController.getSprite("settler");
        gc.drawImage(img, posX - 25, posY - 50, 50, 100);
        gc.restore();
    }

    public void draw(Canvas canvas, Robot robot) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.save();
        if (rotate != null) {
            gc.setTransform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(), rotate.getTy());
        }
        Image img = UIController.getSprite("robot");
        gc.drawImage(img, posX - 25, posY - 50, 50, 100);
        gc.restore();
    }

    public void draw(Canvas canvas, Ufo ufo) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.save();
        if (rotate != null) {
            gc.setTransform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(), rotate.getTy());
        }
        Image img = UIController.getSprite("ufo");
        gc.drawImage(img, posX - 25, posY - 50, 50, 100);
        gc.restore();
    }

    public boolean equalThingData(Thing d) {
        return thingData == d;
    }

    public void addNei(Obstacle o) {
        neighbours.add(o);
    }

    public void rmNei(Obstacle o) {
        neighbours.remove(o);
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

    public Thing getThingData() {
        return thingData;
    }

    public LinkedHashSet<Obstacle> getNeighbours() {
        return neighbours;
    }

    public Rotate getRotate() {
        return rotate;
    }

    public Entity getEntityData() {
        return entityData;
    }
}
