package Proto.material.materials;

import Proto.Main;
import Proto.material.Material;

/**
 * It represents the WaterIce type material and helps to distinguish it from different materials.
 * If the material gets close to the sun, it will evaporate.
 */
public class WaterIce extends Material{

	/**
	 * Constructor of the WaterIce class. Sets the name and the asteroid where the material is.
	 * @param name The name of the object.
	 */
	public WaterIce(String name){
		super(name);
	}

	/**
	 * Evaporates (remove from the inventory) the given object.
	 */
	@Override
	public void nearSun() {
		myAsteroid.setCore(null);
	}
}
