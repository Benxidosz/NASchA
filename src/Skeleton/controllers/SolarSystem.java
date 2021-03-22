package Skeleton.controllers;

import Skeleton.Main;
import Skeleton.entities.children.Robot;
import Skeleton.entities.children.Settler;
import Skeleton.simulator.SimulationObject;
import Skeleton.simulator.Step;
import Skeleton.things.Thing;

import java.util.ArrayList;

public class SolarSystem implements SimulationObject {
	private ArrayList<Robot> robots = new ArrayList<>();
	private ArrayList<Settler> settlers = new ArrayList<>();
	private ArrayList<Thing> things = new ArrayList<>();
	private String name;

	public SolarSystem(String name) { this.name = name; }

	private void addAllObject(Step step) {
		step.addObject(this);
		for (Robot o : robots)
			step.addObject(o);
		for (Settler o : settlers)
			step.addObject(o);
		for (Thing o : things)
			step.addObject(o);
	}

	public void win() {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " win()");

		addAllObject(step);

		Main.activeSimulation.addStep(step);

		Main.decreaseTab();
	}
	
	public void lose() {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " lose()");

		addAllObject(step);

		Main.activeSimulation.addStep(step);

		Main.decreaseTab();
	}
	
	public void removeThing(Thing t) {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " removeThing(" + t.getName() + ")");

		addAllObject(step);
		step.addObject(t);

		Main.activeSimulation.addStep(step);

		things.remove(t);

		Main.decreaseTab();
	}
	
	public void addThing(Thing t) {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " addThing(" + t.getName() + ")");

		addAllObject(step);
		step.addObject(t);

		Main.activeSimulation.addStep(step);

		things.add(t);

		Main.decreaseTab();
	}
	
	public void removeRobot(Robot r) {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " removeRobot(" + r.getName() + ")");

		addAllObject(step);
		step.addObject(r);

		Main.activeSimulation.addStep(step);

		robots.remove(r);

		Main.decreaseTab();
	}
	
	public void addRobot(Robot r) {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " addRobot(" + r.getName() + ")");

		addAllObject(step);
		step.addObject(r);

		Main.activeSimulation.addStep(step);

		robots.add(r);

		Main.decreaseTab();
	}
	
	public void removeSettler(Settler s) {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " removeSettler(" + s.getName() + ")");

		addAllObject(step);
		step.addObject(s);

		Main.activeSimulation.addStep(step);

		settlers.remove(s);

		Main.decreaseTab();
	}
	
	public void makeSolarEruption() {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " makeSolarEruption()");

		addAllObject(step);

		Main.activeSimulation.addStep(step);
		things.forEach((e) -> {
			Main.increaseTab();
			e.applySunEruption();
		});

		Main.decreaseTab();
	}
	
	public void startGame() {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " startGame()");

		addAllObject(step);

		Main.activeSimulation.addStep(step);

		Main.decreaseTab();
	}

	@Override
	public void listParameters() {
		System.out.println("--------");
		System.out.println(name + ":");
		System.out.println("\trobots:");
		robots.forEach(r -> System.out.println("\t" + r.getName()));
		System.out.println("\tsettlers:");
		settlers.forEach(s -> System.out.println("\t" + s.getName()));
		System.out.println("\tthings:");
		things.forEach(t -> System.out.println("\t" + t.getName()));
		System.out.println("--------");
	}

	@Override
	public String printName() {
		return name;
	}
}
