package Proto.controller.controllers;

import Proto.GameManager;
import Proto.controller.Controller;
import Proto.entity.entities.Robot;
import Proto.things.Thing;

import java.util.LinkedList;

public class RobotController implements Controller {
	public static RobotController ref;

	private final LinkedList<Robot> robots = new LinkedList<>();

	public RobotController() {
		ref = this;
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
