
package Proto.entity;

import Proto.Main;
import Proto.material.Material;
import Proto.simulator.SimulationObject;
import Proto.simulator.Step;
import Proto.things.Thing;

import java.util.InputMismatchException;

public abstract class Entity {

	protected Thing location;

	public Entity(Thing loc){
		loc = location;
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

}
