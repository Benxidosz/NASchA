package Proto.entity;

import Proto.Main;
import Proto.simulator.SimulationObject;
import Proto.simulator.Step;
import Proto.things.Thing;

import java.util.InputMismatchException;

public abstract class Entity implements SimulationObject {
	protected Thing location;
	protected String name;

	/**
	 * Constructor of the Entity class. Sets the name and location.
	 * @param name The name of the object
	 */
	public Entity(String name) {
		this.name = name;

		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " created.");
		step.addObject(this);

		Main.activeSimulation.addStep(step);
	}

	protected void addAllObject(Step step) {
		step.addObject(this);
		step.addObject(location);
	}

	/**
	 * Drills the location.
	 */
	public void drill() {
		StringBuilder builder = new StringBuilder();
		builder.append(Main.printTabs() + Main.call++ + " " + name + " drill()");

		System.out.println("Does location have layer left? [Y/N]");
		try{
			Step step;
			String input = Main.scanner.nextLine().toUpperCase();
			if (input.equals("Y")) {
				builder.append(" layer drilled.");
				step = new Step(builder.toString());
				Main.activeSimulation.addStep(step);
				Main.increaseTab();
				if(location != null)
					location.drill();
				addAllObject(step);
			} else if (input.equals("N")) {
				step = new Step(builder.toString());
				builder.append(" no layer left.");
				Main.activeSimulation.addStep(step);
				addAllObject(step);
			} else {
				throw new InputMismatchException("Wrong Input!");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		Main.decreaseTab();
	}

	/**
	 * Changes the location of the Entity to the destination.
	 * @param destination The next location of the object
	 */
	public void move(Thing destination) {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " move(" + destination.getName() + ")");

		addAllObject(step);
		step.addObject(destination);

		Main.activeSimulation.addStep(step);
		Main.increaseTab();
		destination.addEntity(this);

		if (location != null) {
			Main.increaseTab();
			location.removeEntity(this);
		}

		location = destination;
		Main.decreaseTab();
	}

	/**
	 * The entity waits on turn.
	 */
	public void waitEntity() {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " waitEntity()");

		addAllObject(step);

		Main.activeSimulation.addStep(step);
		Main.increaseTab();

		Main.decreaseTab();
	}

	public abstract void die();

	public abstract void explode();

	public String getName() {
		return name;
	}
}
