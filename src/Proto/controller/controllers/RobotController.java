package Proto.controller.controllers;

import Proto.GameManager;
import Proto.controller.Controller;
import Proto.entity.entities.Robot;

import java.util.LinkedList;

public class RobotController extends Controller {
	public final LinkedList<Robot> robots = new LinkedList<>();

	public RobotController(GameManager gm) {
		super(gm);
	}

	@Override
	public void makeTurn() {

	}
}
