package Graphics.ui.game.drawable;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

/**
 * Abstract class for the drawable items
 */
public abstract class Drawable {
    /**
     * The state of draw
     */
    protected DrawState state;

    /**
     * Set the state of draw to idle in constructor
     */
    public Drawable() {
        state = DrawState.idle;
    }

    /**
     * Draw to the canvas
     * @param canvas
     */
    public abstract void draw(Canvas canvas);

    /**
     * Set the drawing state
     * @param state
     */
    public void setState(DrawState state) {
        this.state = state;
    }
}

