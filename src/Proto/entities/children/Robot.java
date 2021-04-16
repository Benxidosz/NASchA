package Proto.entities.children;

import Proto.Main;
import Proto.controllers.SolarSystem;
import Proto.entities.Entity;
import Proto.materials.Material;
import Proto.simulator.SimulationObject;
import Proto.simulator.Step;
import Proto.things.Thing;

public class Robot extends Entity{

	/**
	 * The constructor of the Robot class. Calls the constructor of the base-class.
	 * @param name The name of the object
	 */
	public Robot(String name) {
		super(name);
	}

	/**
	 * Robot gets deleted.
	 */
	@Override
	public void die() {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " die()");

		addAllObject(step);

		Main.activeSimulation.addStep(step);
		Main.increaseTab();

		Main.decreaseTab();
	}

	/**
	 * Robot explodes, therefore dies.
	 */
	@Override
	public void explode() {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " explode()");
		addAllObject(step);
		Main.activeSimulation.addStep(step);
		Main.increaseTab();
		Thing dest = location.randomNeighbour();

		Main.increaseTab();
		move(dest);

		Main.decreaseTab();
	}

	@Override
	public void listParameters() {
		System.out.println("--------");
		System.out.println(name + ":");
		System.out.println("location: " + location);
		System.out.println("--------");
	}

	@Override
	public String printName() {
		return name;
	}
}
