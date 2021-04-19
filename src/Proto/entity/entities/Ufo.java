package Proto.entity.entities;

import Proto.Inventory;
import Proto.controller.controllers.UfoController;
import Proto.entity.Entity;
import Proto.material.Material;
import Proto.things.Thing;
import Proto.things.gate.TeleportGate;

public class Ufo extends Entity {

	private Inventory myInventory;

	public Ufo(Thing loc) {
		super(loc);
		Inventory myInventory = new Inventory();
	}

	@Override
	public void die(){
		super.die();
		UfoController.ref.rmUfo(this);
	}

	@Override
	public void addMaterial(Material m){
		myInventory.addMaterial(m);
	}

	@Override
	public void drill(){
	}

}
