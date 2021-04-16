package Proto.materials.children;

import Proto.Main;
import Proto.materials.Material;
import Proto.simulator.Step;
import Proto.things.asteroids.Asteroid;

public class WaterIce extends Material{

	/**
	 * Constructor of the WaterIce class. Sets the name and the asteroid where the material is.
	 * @param name The name of the object.
	 */
	public WaterIce(String name){
		super(name);
	}

	@Override
	public void nearSun() {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " nearSun()");
		Main.activeSimulation.addStep(step);
		Main.increaseTab();
		myAsteroid.setCore(null);
		Main.decreaseTab();
	}

	@Override
	public String printName() {
		return name;
	}
}
