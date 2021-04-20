package Proto.material.materials;

import Proto.Main;
import Proto.material.Material;
import Proto.material.compare.MaterialCompare;

/**
 * It represents a Uran type material and helps to distinguish it from other different materials.
 * It counts how many times the object was near the sun and if so for the third time near the sun, it will explode.
 */
public class Uran extends Material {
	/**
	 * It counts how many times the object was near the sun.
	 */
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

	/**
	 * It decides the material explodes or only increases the countExposition attribute.
	 */
	@Override
	public void nearSun() {
		if (countExposition == 3) {
			myAsteroid.explode();
		}
		else {
			countExposition++;
			if (countExposition == 3)
				myAsteroid.explode();
		}
	}

	/**
	 * An override method which is compares the parameter to the type
	 * of this class' material.
	 * @param o a material
	 * @return if it's the same type, it returns true
	 */
	@Override
	public boolean compare(Material o) {
		return MaterialCompare.getInstance().compare(this, o);
	}

	/**
	 * An override method which is compares the parameter to the type
	 * of this class' material.
	 * @param o an iron
	 * @return false
	 */
	@Override
	public boolean compare(Iron o) {
		return false;
	}

	/**
	 * An override method which is compares the parameter to the type
	 * of this class' material.
	 * @param o a waterice
	 * @return false
	 */
	@Override
	public boolean compare(WaterIce o) {
		return false;
	}

	/**
	 * An override method which is compares the parameter to the type
	 * of this class' material.
	 * @param o an uran
	 * @return true
	 */
	@Override
	public boolean compare(Uran o) {
		return true;
	}

	/**
	 * An override method which is compares the parameter to the type
	 * of this class' material.
	 * @param o a silicon
	 * @return false
	 */
	@Override
	public boolean compare(Silicon o) {
		return false;
	}

	/**
	 * An override method which is compares the parameter to the type
	 * of this class' material.
	 * @param o a coal
	 * @return false
	 */
	@Override
	public boolean compare(Coal o) {
		return false;
	}


}
