package Proto.entity.entities;

import Proto.GameManager;
import Proto.Inventory;
import Proto.controller.controllers.RobotController;
import Proto.controller.controllers.SettlerController;
import Proto.controller.controllers.SolarSystem;
import Proto.things.Thing;
import Proto.things.gate.TeleportGate;
import Proto.entity.Entity;
import Proto.material.Material;

import java.util.ArrayList;

public class Settler extends Entity {

	private ArrayList<TeleportGate> gates;
	private Inventory myInventory;
	private boolean active;

	public Settler(Thing loc, String name) {
		super(loc, name);
		gates = new ArrayList<>(3);
		myInventory = new Inventory(10);
		active = true;
	}

	@Override
	protected void done(){
		active = false;
		SettlerController.getInstance().done();
	}

	/**
	 * Settler builds a base with the right materials.
	 */
	public void buildBase() {
		for (Material mat : myInventory.getMaterials()) {
			if (location.buildBase(mat)) {
				myInventory.rmMaterial(mat);
			}
		}
	}

	/**
	 * Adds two new TeleportGates to the Settler.
	 */
	public void buildGate(String name) {
		if (gates.size()<2) {
			Inventory gateRecipe = GameManager.getInstance().recipes.get("TeleportGate");
			if(myInventory.containsRecipe(gateRecipe)) {
				myInventory.rmAllFromRecipe(gateRecipe);
				TeleportGate gate1 = new TeleportGate(name + "a");
				TeleportGate gate2 = new TeleportGate(name + "b");
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
	public void buildRobot(String name) {
		Inventory robotRecipe = GameManager.getInstance().recipes.get("Robot");
		if(myInventory.containsRecipe(robotRecipe)) {
			myInventory.rmAllFromRecipe(robotRecipe);
			Robot r = new Robot(name, location);
			RobotController.getInstance().addRobot(r);
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
		SolarSystem.getInstance().addThing(g);
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
		SettlerController.getInstance().rmSettler(this);
	}

	public Material getMaterialByName(String arg) {
		for (Material mat : myInventory.getMaterials()) {
			if (mat.getName().equals(arg))
				return mat;
		}
		return null;
	}

	public TeleportGate getGateByName(String name) {
		for (TeleportGate gate : gates)
			if (gate.getName().equals(name))
				return gate;

		return null;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public ArrayList<TeleportGate> getGates() {
		return gates;
	}

	public Inventory getMyInventory() {
		return this.myInventory;
	}

	/**
	 * Lists the attributes of the object.
	 * @return the attributes as a string.
	 */
	@Override
	public String List() {
		StringBuilder result = new StringBuilder("+------------------+\n");
		result.append("name: " + getName() + "\n");
		result.append("materials: ");
		if (myInventory.getMaterials().size() == 0)
			result.append("null \n");
		else
			for (Material mat : myInventory.getMaterials())
				result.append(mat.getName() + " ");
		result.append("\ngates: ");
		if (gates.size() == 0)
			result.append("null \n");
		else
			for (TeleportGate tg : gates)
				result.append(tg.getName() + " ");
		result.append("\nlocation: " + location.getName() + "\n");
		result.append("stepped: " + (active == true ? "false" : "true") + "\n");

		return result.toString();
	}
}
