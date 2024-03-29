package Skeleton.entities.children;

import Skeleton.Main;
import Skeleton.controllers.SolarSystem;
import Skeleton.materials.children.Coal;
import Skeleton.materials.children.Uran;
import Skeleton.simulator.SimulationObject;
import Skeleton.simulator.Step;
import Skeleton.things.Thing;
import Skeleton.things.gate.TeleportGate;
import Skeleton.entities.Entity;
import Skeleton.materials.Material;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Settler extends Entity {
	private TeleportGate[] gates;
	private SolarSystem mySystem;
	private ArrayList<Material> materials;

	/**
	 * Constructor of the Settler class. Gets name and location from the base-class.
	 * @param name Name of the object
	 */
	public Settler(String name) {
		super(name);
		gates = new TeleportGate[2];
		materials = new ArrayList<>();
		mySystem = Main.system;
	}

	@Override
	protected void addAllObject(Step step) {
		super.addAllObject(step);

		for (Material m : materials)
			step.addObject(m);
		step.addObject(gates[0]);
		step.addObject(gates[1]);
		step.addObject(mySystem);
	}

	/**
	 * Mines the location of the Settler.
	 */
	public void mine() {
		StringBuilder builder = new StringBuilder();
		builder.append(Main.printTabs() + Main.call++ + " " + name + " mine()");

		System.out.println("Can the settler store more? [Y/N]");
		try{
			Step step;
			String input = Main.scanner.nextLine().toUpperCase();
			if (input.equals("Y")) {
				builder.append("asteroid mined.");
				step = new Step(builder.toString());
				addAllObject(step);
				Main.activeSimulation.addStep(step);

				Main.increaseTab();
				Material m = location.excavate();

				Main.increaseTab();
				addMaterial(m);
			} else if (input.equals("N")) {
				builder.append("asteroid was not mined.");
				step = new Step(builder.toString());
				addAllObject(step);
				Main.activeSimulation.addStep(step);
			} else {
				throw new InputMismatchException("Wrong Input!");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		Main.decreaseTab();
	}

	/**
	 * Settler builds a base with the right materials.
	 */
	public void buildBase() {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " buildBase()");

		addAllObject(step);

		Main.activeSimulation.addStep(step);
		Main.increaseTab();

		for(int i = 0; i < materials.size(); ++i){
			if(materials.get(i) instanceof Uran)
				location.buildBase(materials.get(i));

			if(materials.get(i) instanceof Coal){
				location.buildBase(materials.get(i));
			}
		}

		Main.decreaseTab();
	}

	/**
	 * Adds two new TeleportGates to the Settler.
	 */
	public void buildGate() {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " buildGate()");
		Main.activeSimulation.addStep(step);
		Main.increaseTab();
		TeleportGate gate1 = new TeleportGate("firstGate");

		Main.increaseTab();
		TeleportGate gate2 = new TeleportGate( "secondGate");

		Main.increaseTab();
		gate1.setPair(gate2);

		Main.increaseTab();
		gate2.setPair(gate1);

		Main.increaseTab();
		addGate(gate1);

		Main.increaseTab();
		addGate(gate2);

		addAllObject(step);

		step.addObject(gate1);
		step.addObject(gate2);


		Main.decreaseTab();
	}

	/**
	 * Removes the Materials and creates a Robot.
	 */
	public void buildRobot() {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " buildRobot()");
		Main.activeSimulation.addStep(step);
		addAllObject(step);

		for (Material material : materials) {
			Main.increaseTab();
			rmMaterial(material);
		}
		Main.increaseTab();
		Robot r = new Robot("R0");
		mySystem.addRobot(r);

		Main.decreaseTab();
	}

	/**
	 * Place a Material on the location of the Settler.
	 * @param m The Material which is placed.
	 */
	public void placeMaterial(Material m) {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " placeMaterial(" +  m.getName() + ")");

		addAllObject(step);
		step.addObject(m);

		Main.activeSimulation.addStep(step);
		Main.increaseTab();

		if(location.placeMaterial(m)) {
			Main.increaseTab();
			rmMaterial(m);
		}
		Main.decreaseTab();
	}

	/**
	 * Place a TeleportGate on the location of the Settler.
	 * @param g The TeleportGate which is placed.
	 */
	public void putGateDown(TeleportGate g) {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " putDownGate(" +  g.getName() + ")");

		addAllObject(step);
		step.addObject(g);

		Main.activeSimulation.addStep(step);
		Main.increaseTab();

		g.addNeighbour(location);
		mySystem.addThing(g);
		g.activate();
		g.removeEntity(this);

		Main.decreaseTab();
	}

	/**
	 * Add a TeleportGate to the inventory.
	 * @param g The TeleportGate which is added.
	 */
	public void addGate(TeleportGate g) {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " addGate(" +  g.getName() + ")");

		addAllObject(step);
		step.addObject(g);

		Main.activeSimulation.addStep(step);

		if(gates[0] == null)
			gates[0] = g;
		else
			gates[1] = g;


		Main.decreaseTab();
	}

	/**
	 * Add a Material to the inventory.
	 * @param m The Material which is added.
	 */
	public void addMaterial(Material m) {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " addMaterial(" +  m.getName() + ")");
		Main.activeSimulation.addStep(step);
		Main.increaseTab();

		materials.add(m);

		Main.decreaseTab();
	}

	/**
	 * Remove a Material from the inventory.
	 * @param m The Material which is removed.
	 */
	public void rmMaterial(Material m) {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " rmMaterial(" + m.getName() + ")");
		Main.activeSimulation.addStep(step);

		Main.decreaseTab();
	}

	/**
	 * Settler object gets deleted.
	 */
	@Override
	public void die() {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name +  " die()");
		Main.activeSimulation.addStep(step);

		Main.decreaseTab();
	}

	/**
	 * Settler object explodes, therefore dies.
	 */
	@Override
	public void explode() {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " explode()");
		Main.activeSimulation.addStep(step);
		Main.increaseTab();

		die();

		Main.decreaseTab();;
	}


	@Override
	public void listParameters() {

	}

	@Override
	public String printName() {
		return name;
	}
}
