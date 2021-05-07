package Graphics.things;

import Graphics.controller.controllers.SolarSystem;
import Graphics.entity.Entity;
import Graphics.material.Material;
import Graphics.simulator.listableObj;

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

	/**
	 * true if we tries to drill under 0 layer
	 */
	public boolean nullLayer = false;



	/**
	 * The constructor of the Thing
	 * @param name The name of the Thing
	 */
	public Thing(String name) {
		this.name = name;
	}

	/**
	 * Adds the paramater to the entities array.
	 * @param entity the entity that is added.
	 */
	public Thing addEntity(Entity entity) {
		entities.add(entity);
		return this;
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
		if (nei != this)
			neighbour.remove(nei);
	}

	/**
	 * Abstract function of the sun eruption.
	 */
	public void MainlySunEruption() {
		for (var iter = entities.iterator(); iter.hasNext();) {
			Entity tmp = iter.next();
			tmp.die();
			iter.remove();
		}
	}

	/**
	 * It's overridden in the children classes.
	 */
	public void drill() {
	}

	/**
	 * It's overridden in the children classes.
	 * @return
	 */
	public Material excavate() {
		return null;
	}

	/**
	 * It's overridden in the children classes.
	 * @param m
	 * @return
	 */
	public boolean placeMaterial(Material m) {
		return false;
	}

	/**
	 * It's overridden in the children classes.
	 * @param m
	 * @return
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
	 * @return the name of the object.
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
	 * @return with the neighbour.
	 */
	public ArrayList<Thing> getNeighbour() {
		return neighbour;
	}

	/**
	 * Returns true if the Thing is drillable
	 * @return false, but the Asteroid Override it
	 */
	public boolean isDrillable() {
		return false;
	}

	/**
	 * Returns the neighbours name.
	 * @param name of the Thing
	 * @return the neighbours
	 */
	public Thing getNeiByName(String name) {
		for (Thing nei : neighbour) {
			if (nei.getName().equals(name))
				return nei;
		}
		return null;
	}

	/**
	 * Calls explode for all entities on it.
	 */
	public void explode() {
		ArrayList<Entity> tmp = new ArrayList<>(entities);
		tmp.forEach((Entity::explode));
		for (Thing nei1 : neighbour) {
			nei1.removeNeighbour(this);
			for (Thing nei2 : neighbour)
				if (nei1 != nei2) {
					nei1.addNeighbour(nei2);
					nei2.addNeighbour(nei1);
				}
		}
		SolarSystem.getInstance().removeThing(this);
	}

	/**
	 * Returns the core of the Thing
	 * @return false, but the Asteroid Override it
	 */
	public Material getCore() {
		return null;
	}
}