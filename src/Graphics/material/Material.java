package Graphics.material;

import Graphics.material.materials.*;
import Graphics.observable.thing.Asteroid;


/**
 * It represents the various materials on the field by the classes derived from it.
 */
public abstract class Material {
	/**
	 * An Asteroid attribute that stores which asteroid he is on.
	 */
	protected Asteroid myAsteroid;
	/**
	 * Name of the Material.
	 */
	protected String name;

	/**
	 * Constructor of the Material class. Sets the name and the asteroid where the material is.
	 * @param name The name of the object.
	 */
	public Material(String name){
		this.name = name;
	}

	/**
	 * The asteroid that contains the material is near sun and its last layer was drilled.
	 */
	public void nearSun() {

	}

	/**
	 * Returns the name of the Material.
	 * @return The string name
	 */
	public String getName(){
		return name;
	}

	/**
	 * Sets the asteroid which contains the material.
	 * @param myAsteroid The materials asteroid
	 */
	public void setMyAsteroid(Asteroid myAsteroid) {
		this.myAsteroid = myAsteroid;
	}

	/**
	 * An abstract method which is compare two material in the children classes.
	 * @param o a material
	 * @return if the material has the same type as the child class, it returns true.
	 */
	public abstract boolean compare(Material o);

	/**
	 * An abstract method which is compare two material in the children classes.
	 * @param o an iron
	 * @return if the material has the same type as the child class, it returns true.
	 */
	public abstract boolean compare(Iron o);

	/**
	 * An abstract method which is compare two material in the children classes.
	 * @param o a waterice
	 * @return if the material has the same type as the child class, it returns true.
	 */
	public abstract boolean compare(WaterIce o);

	/**
	 * An abstract method which is compare two material in the children classes.
	 * @param o an uran
	 * @return if the material has the same type as the child class, it returns true.
	 */
	public abstract boolean compare(Uran o);

	/**
	 * An abstract method which is compare two material in the children classes.
	 * @param o a silicon
	 * @return if the material has the same type as the child class, it returns true.
	 */
	public abstract boolean compare(Silicon o);

	/**
	 * An abstract method which is compare two material in the children classes.
	 * @param o a coal
	 * @return if the material has the same type as the child class, it returns true.
	 */
	public abstract boolean compare(Coal o);
}
