package Proto.entity.entities;

import Proto.Inventory;
import Proto.controller.controllers.UfoController;
import Proto.entity.Entity;
import Proto.material.Material;
import Proto.things.Thing;

/**
 * The ufo which is controlled by the system and can mine and move.
 */
public class Ufo extends Entity {

	/**
	 * The ufos inventory
	 */
	private Inventory myInventory;

	/**
	 * The constructor of the Ufo class.
	 * @param loc The location where the Ufo is
	 * @param name The name aof the Ufo
	 */
	public Ufo(Thing loc, String name) {
		super(loc, name);
		Inventory myInventory = new Inventory();
	}

	/**
	 * Ufo gets deleted.
	 */
	@Override
	public void die(){
		super.die();
		UfoController.getInstance().rmUfo(this);
	}

	/**
	 * Add a material to te inventory.
	 * @param m The material which is added
	 */
	@Override
	public void addMaterial(Material m){
		myInventory.addMaterial(m);
	}

	/**
	 * Override drill by doing nothing.
	 */
	@Override
	public void drill(){
	}

}
