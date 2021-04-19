package Proto.things.asteroids;

import Proto.Inventory;
import Proto.material.Material;
import Proto.GameManager;

import java.util.LinkedList;

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
	public MainAsteroid(String name) {
		super(name);

		try {
			requirements = (Inventory) GameManager.getInstance().recipes.get("Base").clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		nearBySun = false;
		core = null;
		layer = 0;
	}

	public MainAsteroid(String name, int layer, Material core, boolean nearBySun) {
		super(name, layer, core, nearBySun);
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
