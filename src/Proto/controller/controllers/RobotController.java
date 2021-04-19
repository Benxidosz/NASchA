package Proto.controller.controllers;

import Proto.GameManager;
import Proto.controller.Controller;
import Proto.entity.entities.Robot;
import Proto.things.Thing;

import java.util.LinkedList;

public class RobotController implements Controller {
	/**
	 * A reference for the class.
	 */
	private static RobotController ref;
	/**
	 * The id of the robot.
	 * Used for the name of the object.
	 */
	private static int robotId;

	/**
	 * Returns the id of the robot.
	 * @return the id of the robot.
	 */
	public static String getRobotId() {
		return "r" + robotId++;
	}

	/**
	 * Returns the reference.
	 * @return the reference
	 */
	public static RobotController getInstance() {
		return ref;
	}

	/**
	 * Sets the reference.
	 */
	public static void init() {
		ref = new RobotController();
	}

	/**
	 * The robots in the Solar System stored in list.
	 */
	private final LinkedList<Robot> robots = new LinkedList<>();

	/**
	 * The constructor of the class
	 */
	private RobotController() {

	}

	private void calculateStep(Robot r) {
		Thing tmpLoc = r.getLocation();
		if (tmpLoc.isDrillable())
			r.drill();
		else {
			r.move(tmpLoc.randomNeighbour());
		}
	}

	public void addRobot(Robot r) {
		robots.add(r);
	}

	public void rmRobot(Robot r) {
		robots.remove(r);
	}

	@Override
	public void makeTurn() {
		robots.forEach(this::calculateStep);
	}

	@Override
	public void handleCommand(String line) {

	}
}
