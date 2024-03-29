package Skeleton.materials.children;

import Skeleton.Main;
import Skeleton.materials.Material;
import Skeleton.simulator.Step;
import Skeleton.things.asteroids.Asteroid;

public class Uran extends Material {

	/**
	 * Constructor of the Uran class. Sets the name and the asteroid where the material is.
	 * @param name The name of the object.
	 */
	public Uran(String name){
		super(name);
	}

	@Override
	public void nearSun() {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " nearSun()");
		Main.activeSimulation.addStep(step);
		Main.increaseTab();
		myAsteroid.explode();
		Main.decreaseTab();
	}

	@Override
	public String printName() {
		return name;
	}
}
