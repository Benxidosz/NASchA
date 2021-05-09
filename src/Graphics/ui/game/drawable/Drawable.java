package Graphics.ui.game.drawable;

import Graphics.ui.game.views.DrawState;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public abstract class Drawable {
    protected Color fillColor;
    protected Color lineColor;
    protected DrawState state;

    public Drawable(Color fill, Color line) {
        fillColor = fill;
        lineColor = line;
        state = DrawState.idle;
    }

    public abstract void draw(Canvas canvas);

    public void setFillColor(Color color) {
        fillColor = color;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public void setState(DrawState state) {
        this.state = state;
    }
}

