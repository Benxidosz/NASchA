package Proto.materials.children;

import Proto.materials.Material;
import Proto.things.asteroids.Asteroid;

public class Silicon extends Material {

	/**
	 * Constructor of the Silicon class. Sets the name and the asteroid where the material is.
	 * @param name The name of the object.
	 */
	public Silicon(String name){
		super(name);
	}

	@Override
	public String printName() {
		return name;
	}
}
