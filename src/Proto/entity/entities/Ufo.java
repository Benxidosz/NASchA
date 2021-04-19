package Proto.entity.entities;

import Proto.Inventory;
import Proto.controller.controllers.UfoController;
import Proto.entity.Entity;
import Proto.material.Material;
import Proto.things.Thing;

public class Ufo extends Entity {

	private Inventory myInventory;

	public Ufo(Thing loc, String name) {
		super(loc, name);
		Inventory myInventory = new Inventory();
	}

	@Override
	public void die(){
		super.die();
		UfoController.getInstance().rmUfo(this);
	}

	@Override
	public void addMaterial(Material m){
		myInventory.addMaterial(m);
	}

	@Override
	public void drill(){
	}

}
