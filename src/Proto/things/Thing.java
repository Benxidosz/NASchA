package Proto.things;

import Proto.Main;
import Proto.controller.controllers.SolarSystem;
import Proto.entity.Entity;
import Proto.material.Material;
import Proto.simulator.SimulationObject;
import Proto.simulator.Step;

import java.util.ArrayList;

public abstract class Thing implements SimulationObject {
	protected ArrayList<Thing> neighbour = new ArrayList<>();
	protected ArrayList<Entity> entities = new ArrayList<>();
	protected SolarSystem mySystem;
	protected String name;

	public Thing(String name) {
		this.name = name;
	}

	protected void addAllObject(Step step) {
		step.addObject(this);
		step.addObject(mySystem);
		for (Thing t : neighbour)
			step.addObject(t);
		for (Entity e : entities)
			step.addObject(e);
	}

	public void addEntity(Entity entity) {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " addEntity(" + entity.getName() + ")");

		addAllObject(step);

		Main.activeSimulation.addStep(step);

		entities.add(entity);

		Main.decreaseTab();
	}

	public void removeEntity(Entity entity) {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " removeEntity(" + entity.getName() + ")");

		addAllObject(step);
		step.addObject(entity);

		Main.activeSimulation.addStep(step);

		entities.remove(entity);

		Main.decreaseTab();
	}

	public void addNeighbour(Thing nei) {
		if (!(nei == this || neighbour.contains(nei))) {
			Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " addNeighbour(" + nei.getName() + ")");

			addAllObject(step);
			step.addObject(nei);

			Main.activeSimulation.addStep(step);

			neighbour.add(nei);
		}
		Main.decreaseTab();
	}

	public void removeNeighbour(Thing nei) {
		if (!(nei == this || neighbour.contains(nei))) {
			Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " removeNeighbour(" + nei.getName() + ")");

			addAllObject(step);
			step.addObject(nei);

			Main.activeSimulation.addStep(step);

			neighbour.remove(nei);
		}
		Main.decreaseTab();
	}

	public abstract void applySunEruption();

	public void drill() {
	}

	public Material excavate() {
		return null;
	}

	public boolean placeMaterial(Material m) {
		return false;
	}

	public void buildBase(Material m) {
	}

	public Thing randomNeighbour() {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " randomNeighbour() return " + neighbour.get(0).getName());
		Main.activeSimulation.addStep(step);

		Main.decreaseTab();
		return neighbour.get(0);
	}


	public String getName() {
		return name;
	}
}