
package Proto.entity;

import Proto.material.Material;
import Proto.simulator.listableObj;
import Proto.things.Thing;
import Proto.things.gate.TeleportGate;

public abstract class Entity {

	protected Thing location;
	protected final String name;

	public Entity(Thing loc, String name){
		this.name = name;
		location = loc;
	}

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

	public void die(){
		location.removeEntity(this);
	}

	public void explode(){
		die();
	}

	public Thing getLocation(){
		return location;
	}

	public String getName() {
		return name;
	}

}
