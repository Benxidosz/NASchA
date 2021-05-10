package Graphics.ui.game.drawable;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

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

    public abstract void draw(Canvas canvas);

    /**
     * Set the drawing state
     * @param state
     */
    public void setState(DrawState state) {
        this.state = state;
    }
}

