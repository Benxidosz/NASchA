package Graphics.ui.game.drawable.drawables;

import Graphics.ui.game.drawable.Drawable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Root extends Drawable {
    private final ArrayList<Obstacle> obstacles;

    public Root(Obstacle o1, Obstacle o2) {
        super(Color.BLACK, Color.BLACK);

        obstacles = new ArrayList<>(2);

        obstacles.add(o1);
        obstacles.add(o2);

        o1.addNei(o2);
        o2.addNei(o1);
    }

    @Override
    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(fillColor);
        int x1 = obstacles.get(0).getPosX();
        int y1 = obstacles.get(0).getPosY();
        int x2 = obstacles.get(1).getPosX();
        int y2 = obstacles.get(1).getPosY();
        gc.strokeLine(x1, y1, x2, y2);
    }

    public boolean containsAll(Obstacle o1, Obstacle o2) {
        return obstacles.contains(o1) && obstacles.contains(o2);
    }

    public double getDistance() {
        return obstacles.get(0).distance(obstacles.get(1));
    }
}
