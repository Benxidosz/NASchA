package Proto.controller.controllers;

import Proto.controller.Controller;
import Proto.entity.entities.Robot;
import Proto.entity.entities.Settler;
import Proto.things.Thing;

import java.util.ArrayList;

public class SolarSystem implements Controller {
	private ArrayList<Robot> robots = new ArrayList<>();
	private ArrayList<Settler> settlers = new ArrayList<>();
	private ArrayList<Thing> things = new ArrayList<>();
	private String name;
	public static SolarSystem ref;

	public SolarSystem(String name) {
		this.name = name;
		ref = this;
	}

	/**
	 * The players win the game.
	 */
	public void win() {

	}

	/**
	 * The players lose the game.
	 */
	public void lose() {

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

	@Override
	public void handleCommand(String line) {

	}
}
