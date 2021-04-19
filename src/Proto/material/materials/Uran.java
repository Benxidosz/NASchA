package Proto.material.materials;

import Proto.Main;
import Proto.material.Material;

public class Uran extends Material {

	private int countExposition;
	/**
	 * Constructor of the Uran class. Sets the name and the asteroid where the material is.
	 * @param name The name of the object.
	 */
	public Uran(String name){
		super(name);
	}

	@Override
	public void nearSun() {
		if (countExposition == 3) {
			myAsteroid.explode();
		}
		else
			countExposition++;
	}
}
