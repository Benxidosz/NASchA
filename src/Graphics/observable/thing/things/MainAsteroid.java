package Graphics.observable.thing.things;

import Graphics.Inventory;
import Graphics.material.Material;
import Graphics.controller.GameManager;
import Graphics.ui.game.drawable.drawables.Obstacle;
import javafx.scene.canvas.Canvas;

/**
 * Represents the main asteroid, where Settlers spawn and they have to build the base in.
 * Stores the required materials for building the base and win.
 */
public class MainAsteroid extends Asteroid {

	/**
	 * The Materials which the Settlers put on the Asteroid
	 */
	private Inventory requirements;

	/**
	 * The constructor of the class. Sets the recipe of the base.
	 * @param name the name of the object.
	 */
	public MainAsteroid(String name) throws CloneNotSupportedException {
		super(name);

		requirements = (Inventory) GameManager.getInstance().recipes.get("Base").clone();

		nearBySun = false;
		core = null;
		layer = 0;
	}

	public MainAsteroid(String name, int layer, Material core, boolean nearBySun) {
		super(name, layer, core, nearBySun);

		try {
			requirements = (Inventory) GameManager.getInstance().recipes.get("Base").clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		this.nearBySun = false;
		this.core = null;
		this.layer = 0;
	}
	/**
	 * A player build base with a material.
	 * @param m the material.
	 */
	@Override
	public boolean buildBase(Material m) {
		if (requirements.containsMaterial(m)) {
			requirements.rmMaterial(m);
			if(requirements.isEmpty())
				GameManager.getInstance().win();
			return true;
		}
		return false;
	}

	/**
	 * Call the given obstacle draw method, with this added as second argument.
	 * @param canvas The canvas what will be given back to the obstacle.
	 * @param obstacle The obstacle which will be gotten the information.
	 */
	@Override
	public void observe(Canvas canvas, Obstacle obstacle) {
		obstacle.draw(canvas, this);
	}
}
