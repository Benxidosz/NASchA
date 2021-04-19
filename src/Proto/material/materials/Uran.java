package Proto.material.materials;

import Proto.Main;
import Proto.material.Material;

/**
 * It represents a Uran type material and helps to distinguish it from other different materials.
 * It counts how many times the object was near the sun and if so for the third time near the sun, it will explode.
 */
public class Uran extends Material {

	private int countExposition;
	/**
	 * Constructor of the Uran class. Sets the name and the asteroid where the material is.
	 * @param name The name of the object.
	 */
	public Uran(String name){
		super(name);
	}

	public Uran(String name, int sunNum){
		super(name);
		this.countExposition = sunNum;
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
