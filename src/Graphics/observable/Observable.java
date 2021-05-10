package Graphics.observable;

import Graphics.ui.game.drawable.drawables.Obstacle;
import javafx.scene.canvas.Canvas;

/**
 * Interface for classes, who can be observed by a obstacle.
 */
public interface Observable {
    /**
     * The method, which responsible, for the observation.
     * @param canvas The canvas what will be given back to the obstacle.
     * @param obstacle The obstacle which will be gotten the information.
     */
    void observe(Canvas canvas, Obstacle obstacle);
}
