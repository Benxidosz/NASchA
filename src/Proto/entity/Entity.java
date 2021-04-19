
package Proto.entity;

import Proto.material.Material;
import Proto.simulator.listableObj;
import Proto.things.Thing;
import Proto.things.gate.TeleportGate;

/**
 * An abstract class, that represents the entities in the solar system, which can do activities.
 */
public abstract class Entity implements listableObj {

	/**
	 * The location of the entity
	 */
	protected Thing location;
	/**
	 * The name of the entity
	 */
	protected final String name;

	/**
	 * The constructor of the Entity class.
	 * @param loc The location where the Entity is
	 * @param name The name of the Entity
	 */
	public Entity(Thing loc, String name){
		this.name = name;
		location = loc;
	}

	/**
	 * The entity finish its turn.
	 */
	protected void done(){}

	/**
	 * Drills the location.
	 */
	public void drill() {
		location.drill();
		done();
	}

	/**
	 * Changes the location of the Entity to the destination.
	 * @param destination The next location of the object
	 */
	public void move(Thing destination) {
		destination.addEntity(this);
		if (location != null) {
			location.removeEntity(this);
		}
		location = destination;
		done();
	}

	/**
	 * Add a material to te inventory.
	 * @param m The material which is added
	 */
	public void addMaterial(Material m){
	}

	public void mine() {
		Material m = location.excavate();
		if (m != null)
			addMaterial(m);
		done();
	}

	/**
	 * The entity waits on turn.
	 */
	public void waitEntity() {
		done();
	}

	/**
	 * The entity dies.
	 */
	public void die() {

	}

	/**
	 * The entity explodes.
	 */
	public void explode(){
		die();
	}

	/**
	 * Returns the location where the entity is.
	 * @return The Thing location
	 */
	public Thing getLocation(){
		return location;
	}

	/**
	 * Returns the name of the Entity.
	 * @return The string name
	 */
	public String getName() {
		return name;
	}

}
