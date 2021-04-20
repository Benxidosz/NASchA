package Proto.material;

import Proto.Main;
import Proto.material.materials.*;
import Proto.simulator.Simulator;
import Proto.things.asteroids.Asteroid;


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

	public abstract boolean compare(Material o);
	public abstract boolean compare(Iron o);
	public abstract boolean compare(WaterIce o);
	public abstract boolean compare(Uran o);
	public abstract boolean compare(Silicon o);
	public abstract boolean compare(Coal o);
}
