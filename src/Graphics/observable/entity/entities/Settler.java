package Graphics.observable.entity.entities;

import Graphics.controller.GameManager;
import Graphics.Inventory;
import Graphics.controller.controllers.RobotController;
import Graphics.controller.controllers.SettlerController;
import Graphics.controller.controllers.SolarSystem;
import Graphics.observable.thing.Thing;
import Graphics.observable.entity.Entity;
import Graphics.observable.thing.things.TeleportGate;
import Graphics.material.Material;
import Graphics.simulator.*;

import java.util.ArrayList;

/**
 * The settler which is controlled by the player, it can do every activities.
 */
public class Settler extends Entity {

	/**
	 * The teleport gates which are owned by the settler.
	 */
	private ArrayList<TeleportGate> gates;
	/**
	 * The settlers inventory
	 */
	private Inventory myInventory;
	/**
	 * It's false, if settler haven't made his turn yet.
	 */
	private boolean active;

	/**
	 * The constructor of the Settler class.
	 * @param loc The location where the Settler is
	 * @param name The name of the Settler
	 */
	public Settler(Thing loc, String name) {
		super(loc, name);
		gates = new ArrayList<>(3);
		myInventory = new Inventory(10);
		active = true;
	}

	/**
	 * The settler finish its turn.
	 */
	@Override
	protected void done(){
		active = false;
		SettlerController.getInstance().done();
	}

	/**
	 * Settler builds a base with the right materials.
	 */
	public void buildBase() {
		boolean built = false;
		try {
			Inventory inv = (Inventory) myInventory.clone();
			for (Material mat : inv.getMaterials()) {
				if (location.buildBase(mat)) {
					myInventory.rmMaterial(mat);
					built = true;
				}
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if (built)
			done();
	}

	/**
	 * Adds two new TeleportGates to the Settler.
	 * @param name The name of the gates
	 */
	public void buildGate(String name) {
		boolean created = false;
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
				created = true;
				done();
			}
		}
		if (!created)
			Simulator.addMessage("Gates can't be created.");
	}

	/**
	 * Removes the Materials and creates a Robot.
	 * @param name The name of the robot.
	 */
	public void buildRobot(String name) {
		Inventory robotRecipe = GameManager.getInstance().recipes.get("Robot");
		if(myInventory.containsRecipe(robotRecipe)) {
			myInventory.rmAllFromRecipe(robotRecipe);
			Robot r = new Robot(name, location);
			RobotController.getInstance().addRobot(r);
			location.addEntity(r);
			done();
		} else
			Simulator.addMessage("Robot can't be created.");
	}

	/**
	 * Place a Material on the location of the Settler.
	 * @param m The Material which is placed.
	 */
	public void placeMaterial(Material m) {
		if(location.placeMaterial(m)) {
			rmMaterial(m);
			done();
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
		done();
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
		if(myInventory.hasSpace()) {
			myInventory.addMaterial(m);
			fullInventory = false;
		}
		else {
			location.placeMaterial(m);
			fullInventory = true;
		}
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

	/**
	 * Return material with the given name.
	 * @param arg the name
	 * @return the material
	 */
	public Material getMaterialByName(String arg) {
		for (Material mat : myInventory.getMaterials()) {
			if (mat.getName().equals(arg))
				return mat;
		}
		return null;
	}

	/**
	 * Return teleport gate with the given name.
	 * @param name the name
	 * @return the gate
	 */
	public TeleportGate getGateByName(String name) {
		for (TeleportGate gate : gates)
			if (gate.getName().equals(name))
				return gate;

		return null;
	}

	/**
	 * Sets the active attribute.
	 * @param active The boolean which it changes
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Returns the value of the active attribute.
	 * @return The boolean
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Returns the TeleportGates owned by the settler.
	 * @return The gates
	 */
	public ArrayList<TeleportGate> getGates() {
		return gates;
	}

	/**
	 * Returns the settlers inventory.
	 * @return the inventory
	 */
	public Inventory getMyInventory() {
		return this.myInventory;
	}

	@Override
	public Inventory getInventory() {
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
			result.append("null");
		else
			for (Material mat : myInventory.getMaterials())
				if (myInventory.getMaterials().getLast() != mat)
					result.append(mat.getName() + " ");
				else
					result.append(mat.getName());
		result.append("\ngates: ");
		if (gates.size() == 0)
			result.append("null");
		else
			for (TeleportGate tg : gates)
				if (gates.get(gates.size() - 1) != tg)
					result.append(tg.getName() + " ");
				else
					result.append(tg.getName());
		result.append("\nlocation: " + location.getName() + "\n");
		result.append("stepped: " + (active == true ? "false" : "true") + "\n");

		return result.toString();
	}
}
