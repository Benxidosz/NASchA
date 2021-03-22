package Skeleton.things.gate;

import Skeleton.Main;
import Skeleton.entities.Entity;
import Skeleton.entities.children.Settler;
import Skeleton.materials.Material;
import Skeleton.simulator.Step;
import Skeleton.simulator.SimulationObject;
import Skeleton.things.Thing;

public class TeleportGate extends Thing implements SimulationObject {
	private TeleportGate pair;
	private boolean active;

	public TeleportGate(String name) {
		super(name);

		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " created.");
		step.addObject(this);

		Main.activeSimulation.addStep(step);

		Main.decreaseTab();
	}

	public void applySunEruption() {
		Step step = new Step(Main.call + " " + name + " applySunEruption()");
		Main.activeSimulation.addStep(step);
		entities.forEach((e) -> {
			Main.increaseTab();
			e.die();
		});

		Main.decreaseTab();
	}
	
	public void activate() {
		Step step = new Step(Main.call + " " + name + " activate()");
		Main.activeSimulation.addStep(step);
		pair.setActive(true);
		Main.decreaseTab();
	}

	@Override
	public void addEntity(Entity entity){
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " addEntity(" + entity.getName() + ")");

		addAllObject(step);

		Main.activeSimulation.addStep(step);

		pair.passEntity(entity);

		Main.decreaseTab();
	}

	public void passEntity(Entity entity){
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " passEntity(" + entity.getName() + ")");

		addAllObject(step);

		Main.activeSimulation.addStep(step);

		entities.add(entity);

		Main.decreaseTab();
	}

	public void setPair(TeleportGate gate2) {
		pair = gate2;
	}

	public void setActive(boolean act){
		active = act;
	}

	@Override
	public void listParameters() {
		System.out.println(name + ":\n" +
				"Neighbours: ");
		for(int i=0; i<neighbour.size(); ++i)
			System.out.println(neighbour.get(i).getName() + " ");
		System.out.println("Entities: ");
		for(int i=0; i<entities.size(); ++i)
			System.out.println(entities.get(i).getName() + " ");
		System.out.println("Pair: " + pair.getName());
		if(active)
			System.out.println("true");
		else
			System.out.println("false");
	}

	@Override
	public String printName() {
		return name;
	}
}
