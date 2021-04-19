package Proto.entity.entities;

import Proto.Main;
import Proto.controller.controllers.RobotController;
import Proto.entity.Entity;
import Proto.simulator.Step;
import Proto.things.Thing;

public class Robot extends Entity{

	public Robot(Thing loc){
		super(loc);
	}
	@Override
	public void mine(){ }

	/**
	 * Robot gets deleted.
	 */
	@Override
	public void die(){
		super.die();
		RobotController.ref.rmRobot(this);
	}

	/**
	 * Robot explodes, therefore dies.
	 */
	@Override
	public void explode() {
		Thing dest = location.randomNeighbour();
		move(dest);
	}
}
