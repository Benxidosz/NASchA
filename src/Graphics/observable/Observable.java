package Graphics.observable;

import Graphics.ui.game.drawable.drawables.Obstacle;
import javafx.scene.canvas.Canvas;

public interface Observable {
    void observe(Canvas canvas, Obstacle obstacle);
}
