package Proto.things;

import Proto.controller.controllers.SolarSystem;
import Proto.entity.Entity;
import Proto.material.Material;
import Proto.simulator.listableObj;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the places where the entities can move to.
 * It's the parent class of the Asteroid and TeleportGate classes.
 * Stores the neighbours of the thing, the entities on the thing
 * and the solar system which contains it.
 */
public abstract class Thing implements listableObj {
	/**
	 * The Thing's neighbour Thing objects
	 */
	protected ArrayList<Thing> neighbour = new ArrayList<>();
	/**
	 * The Entities on the Thing
	 */
	protected ArrayList<Entity> entities = new ArrayList<>();
	/**
	 * The SolarSystem which contains this Thing
	 */
	private SolarSystem mySystem;
	/**
	 * The name of the Thing
	 */
	protected String name;

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
	 * Get the number of the layer and returns.
	 * @return 0.
	 */
	public int getLayer() {
		return 0;
	}

	/**
	 * Returns the neighbours of the Thing
	 * @return
	 */
	public ArrayList<Thing> getNeighbour() {
		return neighbour;
	}

	public boolean isDrillable() {
		return false;
	}

	public Thing getNeiByName(String name) {
		for (Thing nei : neighbour) {
			if (nei.getName().equals(name))
				return nei;
		}
		return null;
	}

	public Material getCore() {
		return null;
	}
}