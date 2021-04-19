package Proto.things;

import Proto.Main;
import Proto.controller.controllers.SolarSystem;
import Proto.entity.Entity;
import Proto.material.Material;
import Proto.simulator.SimulationObject;
import Proto.simulator.Step;

import java.util.ArrayList;
import java.util.Random;

public abstract class Thing {
	protected ArrayList<Thing> neighbour = new ArrayList<>();	//The Thing's neighbour Thing objects
	protected ArrayList<Entity> entities = new ArrayList<>();	//The Entities on the Thing
	private SolarSystem mySystem;								//The SolarSystem which contains this Thing
	protected String name;										//The name of the Thing

	public Thing(String name) {
		this.name = name;
	}

	/**
	 * Adds the paramater to the entities array.
	 * @param entity the entity that is added.
	 */
	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	/**
	 * Removes the Entity got as a parameter from the entities array.
	 * @param entity the Entity which is removed.
	 */
	public void removeEntity(Entity entity) {
		entities.remove(entity);
	}

	/**
	 * Adds a Thing, got as a parameter, as the neighbour of this Thing.
	 * @param nei the Thing which is added.
	 */
	public void addNeighbour(Thing nei) {
		if (!(nei == this || neighbour.contains(nei)))
			neighbour.add(nei);
	}

	/**
	 * Removes a Thing, got as a paramater, from the neighbours array.
	 * @param nei the Thing which is removed.
	 */
	public void removeNeighbour(Thing nei) {
		if (!(nei == this || neighbour.contains(nei)))
			neighbour.remove(nei);
	}

	/**
	 * Abstract function of the sun eruption.
	 */
	public abstract void applySunEruption();

	/**
	 * Its overridden in the children classes.
	 */
	public void drill() {
	}

	/**
	 * Its overridden in the children classes.
	 * @return
	 */
	public Material excavate() {
		return null;
	}

	/**
	 * Its overridden in the children classes.
	 * @param m
	 * @return
	 */
	public boolean placeMaterial(Material m) {
		return false;
	}

	/**
	 * Its overridden in the children classes.
	 * @param m
	 */
	public boolean buildBase(Material m) {
		return false;
	}

	/**
	 * Finds a random neighbour of the Thing.
	 * @return the random neighbour.
	 */
	public Thing randomNeighbour() {
		Random rand = new Random();
		int random = rand.nextInt(neighbour.size());

		return neighbour.get(random);
	}

	/**
	 * Returns the name of the object.
	 * @return the of the object.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the neighbours of the Thing
	 * @return
	 */
	public ArrayList<Thing> getNeighbour() {
		return neighbour;
	}
}