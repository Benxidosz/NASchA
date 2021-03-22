package Skeleton.materials.children;

import Skeleton.materials.Material;

public class Coal extends Material {

	/**
	 * Constructor of the Coal class. Sets the name and the asteroid where the material is.
	 * @param myAsteroid The asteroid that contains the material.
	 * @param name The name of the object.
	 */
	public Coal(String name){
		super(name);
	}

	@Override
	public String printName() {
		return null;
	}
}
