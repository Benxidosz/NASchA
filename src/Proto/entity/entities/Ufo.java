package Proto.entity.entities;

import Proto.entity.Entity;

public class Ufo extends Entity {
	/**
	 * Constructor of the Entity class. Sets the name and location.
	 *
	 * @param name The name of the object
	 */
	public Ufo(String name) {
		super(name);
	}

	@Override
	public void die() {

	}

	@Override
	public void explode() {

	}

	public void mine(){}

	@Override
	public String printName() {
		return null;
	}
}
