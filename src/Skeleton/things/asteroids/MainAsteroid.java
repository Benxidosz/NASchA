package Skeleton.things.asteroids;

import Skeleton.Main;
import Skeleton.materials.Material;
import Skeleton.simulator.Step;

import java.util.ArrayList;

public class MainAsteroid extends Asteroid {
	private ArrayList<Material> builtIn = new ArrayList<>();

	public MainAsteroid(String name) {
		super(name);
	}

	@Override
	protected void addAllObject(Step step) {
		super.addAllObject(step);

	}

	/**
	 * A player build base with a material.
	 * @param m the material.
	 */
	@Override
	public void buildBase(Material m) {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " buildBase()");

		addAllObject(step);
		step.addObject(m);

		Main.activeSimulation.addStep(step);
		builtIn.add(m);

		Main.decreaseTab();
	}

	/**
	 * list the main asteroid parameters
	 */
	@Override
	public void listParameters() {
		System.out.println(name + ":\n" +
				"layer: " + layer + "\n" +
				"core: " + core.getName() + "\n" +
				"near by sun: ");
		if(nearBySun)
			System.out.println("true");
		else
			System.out.println("false");
		System.out.println("Neighbours: " );
		for(int i=0; i<neighbour.size(); ++i)
			System.out.println(neighbour.get(i).getName() + " ");
		System.out.println("Entities: ");
		for(int i=0; i<entities.size(); ++i)
			System.out.println(entities.get(i).getName() + " ");
		System.out.println("materials built in: ");
		for(int i=0; i<builtIn.size(); ++i)
			System.out.println(builtIn.get(i).getName() + " ");
	}
}
