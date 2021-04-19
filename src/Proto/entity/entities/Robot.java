package Proto.entity.entities;

import Proto.controller.controllers.RobotController;
import Proto.entity.Entity;
import Proto.things.Thing;

public class Robot extends Entity{

	public Robot(String name, Thing loc){
		super(loc, name);
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
