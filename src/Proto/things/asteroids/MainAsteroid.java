package Proto.things.asteroids;

import Proto.material.Material;

import java.util.ArrayList;

public class MainAsteroid extends Asteroid {
	private ArrayList<Material> requirements = new ArrayList<>();	//The Materials which the Settlers put on the Asteroid

	public MainAsteroid(String name) {
		super(name);
		//requirements = GameManager.ref.recipes.get("Base");
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
