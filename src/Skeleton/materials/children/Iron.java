package Skeleton.materials.children;

import Skeleton.materials.Material;
import Skeleton.things.asteroids.Asteroid;

public class Iron extends Material {

	/**
	 * Constructor of the Iron class. Sets the name and the asteroid where the material is.
	 * @param name The name of the object.
	 */
	public Iron(String name){
		super(name);
	}

	@Override
	public String printName() {
		return name;
	}
}
