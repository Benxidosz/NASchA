package Skeleton.things.gate;

import Skeleton.Main;
import Skeleton.entities.children.Settler;
import Skeleton.things.Thing;

public class TeleportGate extends Thing {
	public Settler Unnamed1;
	private TeleportGate pair;

	public TeleportGate(String name) {
		super(name);
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " created.");

		Main.decreaseTab();
	}

	public void applySunEruption() {
		Main.printTabs();
		System.out.print(Main.call++ + " " + name + " applySunEruption() void ");

		System.out.println("eruption applied (TeleportGate is not safe).");
		entities.forEach((e) -> {
			Main.increaseTab();
			e.die();
		});

		Main.decreaseTab();
	}
	
	public void activate() {
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " activate() void ");

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
