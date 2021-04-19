package Proto.entity.entities;

import Proto.Inventory;
import Proto.Main;
import Proto.controller.controllers.SolarSystem;
import Proto.material.materials.Coal;
import Proto.material.materials.Uran;
import Proto.simulator.Step;
import Proto.things.gate.TeleportGate;
import Proto.entity.Entity;
import Proto.material.Material;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Settler extends Entity {
	private ArrayList<TeleportGate> gates;
	private SolarSystem mySystem;
	private ArrayList<Material> materials;
	private boolean active;
	private Inventory myInventory;

	/**
	 * Constructor of the Settler class. Gets name and location from the base-class.
	 * @param name Name of the object
	 */
	public Settler(String name) {
		super(name);
		gates = new ArrayList<>();
		materials = new ArrayList<>();
	}

	/**
	 * Mines the location of the Settler.
	 */
	public void mine() {

	}

	/**
	 * Settler builds a base with the right materials.
	 */
	public void buildBase() {

	}

	/**
	 * Adds two new TeleportGates to the Settler.
	 */
	public void buildGate(String name) {

	}

	/**
	 * Removes the Materials and creates a Robot.
	 */
	public void buildRobot(String name) {

	}

	/**
	 * Place a Material on the location of the Settler.
	 * @param m The Material which is placed.
	 */
	public void placeMaterial(Material m) {

	}

	/**
	 * Place a TeleportGate on the location of the Settler.
	 * @param g The TeleportGate which is placed.
	 */
	public void putGateDown(TeleportGate g) {

	}

	/**
	 * Add a TeleportGate to the inventory.
	 * @param g The TeleportGate which is added.
	 */
	public void addGate(TeleportGate g) {

	}

	/**
	 * Add a Material to the inventory.
	 * @param m The Material which is added.
	 */
	public void addMaterial(Material m) {

	}

	/**
	 * Remove a Material from the inventory.
	 * @param m The Material which is removed.
	 */
	public void rmMaterial(Material m) {

	}

	/**
	 * Settler object gets deleted.
	 */
	@Override
	public void die() {

	}

	/**
	 * Settler object explodes, therefore dies.
	 */
	@Override
	public void explode() {

	}

	@Override
	public String printName() {
		return name;
	}

	public Material getMaterialByName(String name) {
		for (Material mat : myInventory.getMaterials()) {
			if (mat.getName().equals(name)) {
				return mat;
			}
		}
		return null;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public TeleportGate getGateByName(String name) {
		for (TeleportGate tg : gates)
			if (tg.getName().equals(name))
				return tg;

		return null;
	}
}
