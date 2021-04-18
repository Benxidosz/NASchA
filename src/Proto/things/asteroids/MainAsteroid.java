package Proto.things.asteroids;

import Proto.Main;
import Proto.material.Material;
import Proto.simulator.Step;

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

	}
}
