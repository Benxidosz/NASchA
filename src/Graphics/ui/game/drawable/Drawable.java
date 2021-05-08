package Graphics.ui.game.drawable;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public abstract class Drawable {
    protected Color fillColor;
    protected Color lineColor;

    public Drawable(Color fill, Color line) {
        fillColor = fill;
        lineColor = line;
    }

    public abstract void draw(Canvas canvas);

    public void setFillColor(Color color) {
        fillColor = color;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

}

