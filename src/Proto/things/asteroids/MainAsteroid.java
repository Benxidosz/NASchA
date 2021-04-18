package Proto.things.asteroids;

import Proto.Main;
import Proto.material.Material;
import Proto.simulator.Step;

import java.util.ArrayList;

public class MainAsteroid extends Asteroid {
	private ArrayList<Material> requirements = new ArrayList<>();	//The Materials which the Settlers put on the Asteroid

	public MainAsteroid(String name) {
		super(name);
	}

	/**
	 * A player build base with a material.
	 * @param m the material.
	 */
	@Override
	public boolean buildBase(Material m) {
		if(requirements.size() == 0) return true;

		requirements.remove(m);
		return false;
	}

}
