package Proto.materials.children;

import Proto.materials.Material;
import Proto.things.asteroids.Asteroid;

public class Coal extends Material {

	/**
	 * Constructor of the Coal class. Sets the name and the asteroid where the material is.
	 * @param name The name of the object.
	 */
	public Coal(String name){
		super(name);
	}

	@Override
	public String printName() {
		return name;
	}
}
