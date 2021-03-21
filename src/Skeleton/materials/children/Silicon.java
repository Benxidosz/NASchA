package Skeleton.materials.children;

import Skeleton.materials.Material;

public class Silicon extends Material {

	/**
	 * Constructor of the Silicon class. Sets the name and the asteroid where the material is.
	 * @param myAsteroid The asteroid that contains the material.
	 * @param name The name of the object.
	 */
	public Silicon(Asteroid myAsteroid, String name){
		super(myAsteroid, name);
	}

	@Override
	public String printName() {
		return null;
	}
}
