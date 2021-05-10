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

/**
 * Drawable item that store the item position and drawing mode
 */
public class Obstacle extends Drawable {

    /**
     * the x coordinate of the position
     */
    private int posX;
    /**
     * the y coordinate of the position
     */
    private int posY;
    /**
     * The thing data in the obstacle
     */
    private final Thing thingData;
    /**
     * The entity data in the obstacle
     */
    private final Entity entityData;
    /**
     * The neighbours of the obstacle
     */
    private final LinkedHashSet<Obstacle> neighbours;
    /**
     * The rotation of the obstacle
     */
    private Rotate rotate;

    /**
     * The constructor that can me an Obstacle by that position on the board and the thing witch we want to draw
     * @param posX the x coordinate of the position
     * @param posY the y coordinate of the position
     * @param data entityData
     */
    public Obstacle(int posX, int posY, Thing data) {
        super();
        this.posX = posX;
        this.posY = posY;
        thingData = data;
        entityData = null;
        neighbours = new LinkedHashSet<>();
        rotate = null;
    }

    /**
     * The constructor that can me an Obstacle by that position on the board and the entity witch we want to draw
     * @param posX the x coordinate of the position
     * @param posY the y coordinate of the position
     * @param data entityData
     */
    public Obstacle(int posX, int posY, Entity data) {
        super();
        this.posX = posX;
        this.posY = posY;
        entityData = data;
        thingData = null;
        neighbours = new LinkedHashSet<>();
        rotate = null;
    }

    /**
     * return the X position
     * @return the X position
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Return the Y position
     * @return the Y position
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Set the X position
     * @param posX the x coordinate of the position
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Set the Y position
     * @param posY the y coordinate of the position
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Set the rotation
     * @param rotate the rotation
     */
    public void setRotate(Rotate rotate) {
        this.rotate = rotate;
    }

    /**
     * Draw the stored data on to the canvas that given in parameter
     * @param canvas where we want to draw
     */
    public void draw(Canvas canvas) {
        if (thingData != null)
            thingData.observe(canvas, this);
        if (entityData != null)
            entityData.observe(canvas, this);
    }

    /**
     * Draw an Asteroid to the given canvas
     * @param canvas the canvas
     * @param asteroid the asteroid
     */
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

    /**
     * Draw a Teleport gate to the given canvas
     * @param canvas the canvas
     * @param gate the teleport gate
     */
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

    /**
     * Draw the main asteroid to the given canvas
     * @param canvas the canvas
     * @param main the main asteroid
     */
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

    /**
     * Draw a settler to the given canvas
     * @param canvas the canvas
     * @param settler the settler
     */
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

    /**
     * Draw a robot to the given canvas
     * @param canvas the canvas
     * @param robot the robot
     */
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

    /**
     * Draw an UFO to the given canvas
     * @param canvas the canvas
     * @param ufo the ufo
     */
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

    /**
     * Check the two thing reference to equals each others
     * @param d The thing
     * @return true if the they're equals to each others
     */
    public boolean equalThingData(Thing d) {
        return thingData == d;
    }

    /**
     * Add an obstacle to the neighbours list
     * @param o the obstacle
     */
    public void addNei(Obstacle o) {
        neighbours.add(o);
    }

    /**
     * Remove a neighbour from the neighbours list
     * @param o The neighbour
     */
    public void rmNei(Obstacle o) {
        neighbours.remove(o);
    }

    /**
     * Return the average distance between the neighbours
     * @return the average distance
     */
    public double getAvgDistance() {
        double sum = 0;
        for (Obstacle nei : neighbours)
            sum += distance(nei);
        return sum / neighbours.size();
    }

    /**
     * Return the distance between the given obstacle
     * @param o the obstacle
     * @return the distance
     */
    public double distance(Obstacle o) {
        return Math.sqrt(Math.pow(posX - o.posX, 2) + Math.pow(posY - o.posY, 2));
    }

    /**
     * Return the distance between the given point on the canvas
     * @param x coordinate of the point
     * @param y coordinate of the point
     * @return the distance
     */
    public double distance(int x, int y) {
        return Math.sqrt(Math.pow(posX - x, 2) + Math.pow(posY - y, 2));
    }

    /**
     * Replace a position with on Obstacle witch given in parameter
     * @param o the Obstacle
     */
    public void swapPos(Obstacle o) {
        int tmpX = posX;
        int tmpY = posY;
        posX = o.getPosX();
        posY = o.getPosY();
        o.setPosX(tmpX);
        o.setPosY(tmpY);
    }

    /**
     * Return the thing data
     * @return thingData
     */
    public Thing getThingData() {
        return thingData;
    }

    /**
     * Return the neighbours list
     * @return the neighbours
     */
    public LinkedHashSet<Obstacle> getNeighbours() {
        return neighbours;
    }

    /**
     * return the rotation
     * @return rotate
     */
    public Rotate getRotate() {
        return rotate;
    }

    /**
     * return the entity data
     * @return the entityData
     */
    public Entity getEntityData() {
        return entityData;
    }
}
