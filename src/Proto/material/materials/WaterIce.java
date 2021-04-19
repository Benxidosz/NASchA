package Proto.material.materials;

import Proto.Main;
import Proto.material.Material;

public class WaterIce extends Material{

	/**
	 * Constructor of the WaterIce class. Sets the name and the asteroid where the material is.
	 * @param name The name of the object.
	 */
	public WaterIce(String name){
		super(name);
	}

	@Override
	public void nearSun() {
		myAsteroid.setCore(null);
	}
}
