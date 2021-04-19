package Proto.entity.entities;

import Proto.GameManager;
import Proto.Inventory;
import Proto.Main;
import Proto.controller.controllers.RobotController;
import Proto.controller.controllers.SettlerController;
import Proto.controller.controllers.SolarSystem;
import Proto.material.materials.Coal;
import Proto.material.materials.Uran;
import Proto.simulator.Step;
import Proto.things.Thing;
import Proto.things.gate.TeleportGate;
import Proto.entity.Entity;
import Proto.material.Material;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Settler extends Entity {

	private ArrayList<TeleportGate> gates;
	private Inventory myInventory;
	private boolean active;

	public Settler(Thing loc) {
		super(loc);
		gates = new ArrayList<>(3);
		Inventory myInventory = new Inventory(10);
	}

	@Override
	protected void done(){
		active = false;
		SettlerController.ref.done();
	}

	/**
	 * Settler builds a base with the right materials.
	 */
	public void buildBase() {
		for (Material mat : Inventory.getMaterials()) {
			if (location.buildBase(mat)) {
				Inventory.rmMaterial(mat);
			}
		}
	}

	/**
	 * Adds two new TeleportGates to the Settler.
	 */
	public void buildGate() {
		if (gates.size()<2) {
			Inventory gateRecipe = GameManager.ref.recipes.get("TeleportGate");
			if(myInventory.containsRecipe(gateRecipe)) {
				myInventory.rmAllRecipe(gateRecipe);
				TeleportGate gate1 = new TeleportGate();
				TeleportGate gate2 = new TeleportGate();
				gate1.setPair(gate2);
				gate2.setPair(gate1);
				addGate(gate1);
				addGate(gate2);
			}
		}
	}

	/**
	 * Removes the Materials and creates a Robot.
	 */
	public void buildRobot() {
		Inventory robotRecipe = GameManager.ref.recipes.get("Robot");
		if(myInventory.containsRecipe(robotRecipe)) {
			myInventory.rmAllRecipe(robotRecipe);
			Robot r = new Robot(location);
			RobotController.ref.addRobot(r);
			location.addEntity(r);
		}
	}

	/**
	 * Place a Material on the location of the Settler.
	 * @param m The Material which is placed.
	 */
	public void placeMaterial(Material m) {
		if(location.placeMaterial(m)) {
			rmMaterial(m);
		}
	}

	/**
	 * Place a TeleportGate on the location of the Settler.
	 * @param g The TeleportGate which is placed.
	 */
	public void putGateDown(TeleportGate g) {
		g.addNeighbour(location);
		location.addNeighbour(g);
		SolarSystem.ref.addThing(g);
		g.activate();
		gates.remove(g);
	}

	/**
	 * Add a TeleportGate to the inventory.
	 * @param g The TeleportGate which is added.
	 */
	public void addGate(TeleportGate g) {
		gates.add(g);
	}

	/**
	 * Add a Material to the inventory.
	 * @param m The Material which is added.
	 */
	@Override
	public void addMaterial(Material m) {
		if(myInventory.hasSpace())
			myInventory.addMaterial(m);
		else
			location.placeMaterial(m);
	}

	/**
	 * Remove a Material from the inventory.
	 * @param m The Material which is removed.
	 */
	private void rmMaterial(Material m) {
		myInventory.rmMaterial(m);
	}

	/**
	 * Settler object gets deleted.
	 */
	@Override
	public void die(){
		super.die();
		SettlerController.ref.rmSettler(this);
	}

	/**
	 * Settler object explodes, therefore dies.
	 */

}
