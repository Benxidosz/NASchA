package Skeleton.materials;

import Skeleton.Main;
import Skeleton.simulator.SimulationObject;
import Skeleton.simulator.Step;
import Skeleton.things.asteroids.Asteroid;

public abstract class Material implements SimulationObject {
	protected Asteroid myAsteroid;
	protected String name;

	/**
	 * Constructor of the Material class. Sets the name and the asteroid where the material is.
	 * @param name The name of the object.
	 */
	public Material(String name){
		this.name = name;
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " created.");
		step.addObject(this);
		Main.activeSimulation.addStep(step);
	}

	protected void addAllObject(Step step) {
		step.addObject(this);
		step.addObject(myAsteroid);
	}

	/**
	 * The asteroid that contains the material is near sun and its last layer was drilled.
	 */
	public void nearSun() {

	}

	public String getName(){
		return name;
	}

	@Override
	public void listParameters() {
		System.out.println(name + ":\n" +
				"Asteroid: " + myAsteroid.getName());
	}
}
