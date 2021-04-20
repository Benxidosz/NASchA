package Proto.controller.controllers;

import Proto.GameManager;
import Proto.Main;
import Proto.controller.Controller;
import Proto.entity.entities.Robot;
import Proto.entity.entities.Settler;
import Proto.things.Thing;

import java.util.LinkedList;

/**
 * Represents the controller class of the robots.
 * Calculates the next step for each robot.
 */
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

	/**
	 * Calculates the next step of the robot.
	 * If the location is drillable it drills,
	 * otherwise moves to a random neighbour.
	 * @param r the Robot which makes the step.
	 */
	private void calculateStep(Robot r) {
		Thing tmpLoc = r.getLocation();
		if (tmpLoc.isDrillable())
			r.drill();
		else {
			r.move(tmpLoc.randomNeighbour());
		}
	}

	/**
	 * Returns the robot by his given name
	 * @param name Name of the robot
	 * @return The robot
	 */
	public Robot getRobotByName(String name) {
		for (Robot robot : robots) {
			if (robot.getName().equals(name)) {
				return robot;
			}
		}

		return null;
	}

	/**
	 * Adds a robot, got as a parameter, to the
	 * robots array.
	 * @param r the Robot which is added.
	 */
	public void addRobot(Robot r) {
		robots.add(r);
	}

	/**
	 * Removes a robot, got as a parameter, from the
	 * robots array.
	 * @param r the Robot which is deleted.
	 */
	public void rmRobot(Robot r) {
		robots.remove(r);
	}

	/**
	 * Calls the calculateStep function for each robot
	 * in the Solar System.
	 */
	@Override
	public void makeTurn() {
		robots.forEach(this::calculateStep);
	}

	public LinkedList<Robot> getRobots() {
		return robots;
	}

	/**
	 * Handles the inputs from the interface.
	 * @param line The command
	 */
	@Override
	public void handleCommand(String line) {
		String[] args = line.split(" ");

		Robot selected = null;
		if (args.length > 1)
			selected = getRobotByName(args[1]);

		if ("Move".equals(args[0])) {
			if (selected.getLocation() != null) {
				Thing dest = selected.getLocation().getNeiByName(args[2]);
				selected.move(dest);
			}
		} else if ("Drill".equals(args[0])) {
			selected.drill();
		}
	}
}
