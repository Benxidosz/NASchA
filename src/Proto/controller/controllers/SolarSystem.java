package Proto.controller.controllers;

import Proto.GameManager;
import Proto.Main;
import Proto.controller.Controller;
import Proto.entity.entities.Robot;
import Proto.entity.entities.Settler;
import Proto.simulator.SimulationObject;
import Proto.simulator.Step;
import Proto.things.Thing;

import java.util.ArrayList;

public class SolarSystem extends Controller {
	private String name;

	public SolarSystem(GameManager gm, String name) {
		super(gm);
		this.name = name;
	}

	/**
	 * Removes a thing from the SolarSystem
	 * @param t The thing that is removed.
	 */
	public void removeThing(Thing t) {

	}

	/**
	 * Add a thing from the SolarSystem
	 * @param t The thing that is added.
	 */
	public void addThing(Thing t) {

	}

	/**
	 * Removes a robot from the SolarSystem
	 * @param r The robot that is removed.
	 */
	public void removeRobot(Robot r) {

	}

	/**
	 * Add a robot from the SolarSystem
	 * @param r The robot that is added.
	 */
	public void addRobot(Robot r) {

	}

	/**
	 * Removes a settler from the SolarSystem
	 * @param s The robot that is removed.
	 */
	public void removeSettler(Settler s) {

	}

	/**
	 * Makes a solar eruption.
	 */
	public void makeSolarEruption() {

	}

	/**
	 * Starts the game.
	 */
	public void startGame() {

	}

	public String getName(){
		return name;
	}


	@Override
	public void makeTurn() {

	}
}
