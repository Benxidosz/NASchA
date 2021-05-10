package Graphics.ui.game.drawable.drawables;

import Graphics.ui.game.drawable.Drawable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Its represent a road between two obstacle
 */
public class Root extends Drawable {
    /**
     * The list of the obstacles
     */
    private final ArrayList<Obstacle> obstacles;

    /**
     * The  constructor that set the two obtacle
     * @param o1 obstacle one
     * @param o2 obstacle two
     */
    public Root(Obstacle o1, Obstacle o2) {
        super();

        obstacles = new ArrayList<>(2);

        obstacles.add(o1);
        obstacles.add(o2);

        o1.addNei(o2);
        o2.addNei(o1);
    }

    /**
     * Draw the road between two obstacle
     * @param canvas the canvas
     */
    @Override
    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int x1 = obstacles.get(0).getPosX();
        int y1 = obstacles.get(0).getPosY();
        int x2 = obstacles.get(1).getPosX();
        int y2 = obstacles.get(1).getPosY();
        gc.strokeLine(x1, y1, x2, y2);
    }

    /**
     * return true if the two given obstacle have a road between each other
     * @param o1 obstacle one
     * @param o2 obstacle two
     * @return true if there is road between o1 and o2
     */
    public boolean contains(Obstacle o1, Obstacle o2) {
        return obstacles.contains(o1) && obstacles.contains(o2);
    }

    /**
     * Return true if the given obstacle have a road to another obstacle
     * @param o the obstacle
     * @return true if o have road to another obstacle
     */
    public boolean contains (Obstacle o) {
        return obstacles.contains(o);
    }

    /**
     * Return a distance between the two obstacle
     * @return the distance
     */
    public double getDistance() {
        return obstacles.get(0).distance(obstacles.get(1));
    }
}
