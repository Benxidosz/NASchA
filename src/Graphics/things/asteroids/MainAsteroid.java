package Graphics.things.asteroids;

import Graphics.controller.Inventory;
import Graphics.material.Material;
import Graphics.controller.GameManager;

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

		nearBySun = false;
		core = null;
		layer = 0;
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
}
