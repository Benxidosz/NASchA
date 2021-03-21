package Skeleton.materials.children;

import Skeleton.materials.Material;

public class Iron extends Material {

	/**
	 * Constructor of the Iron class. Sets the name and the asteroid where the material is.
	 * @param myAsteroid The asteroid that contains the material.
	 * @param name The name of the object.
	 */
	public Iron(Asteroid myAsteroid, String name){
		super(myAsteroid, name);
	}

	@Override
	public String printName() {
		return null;
	}
}
