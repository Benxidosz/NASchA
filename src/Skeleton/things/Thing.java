package Skeleton.things;

import Skeleton.Main;
import Skeleton.controllers.SolarSystem;
import Skeleton.entities.Entity;
import Skeleton.materials.Material;
import Skeleton.simulator.SimulationObject;
import Skeleton.simulator.Step;
import Skeleton.things.gate.TeleportGate;

import java.util.ArrayList;
import java.util.Random;

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
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " removeEntity() void.");
		entities.remove(entity);

		Main.decreaseTab();
	}

	public void addNeighbour(Thing nei) {
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " addNeighbour() void.");
		neighbour.add(nei);

		Main.decreaseTab();
	}

	public void removeNeighbour(Thing nei) {
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " removeNeighbour() void.");
		neighbour.remove(nei);

		Main.decreaseTab();
	}

	public void applySunEruption() {
	}

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
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " randomNeighbour() void.");

		Main.decreaseTab();
		return null;
	}

	public void setPair(TeleportGate gate2) {

	}

	public String getName() {
		return name;
	}
}