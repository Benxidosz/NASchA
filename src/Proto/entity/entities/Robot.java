package Proto.entity.entities;

import Proto.controller.controllers.RobotController;
import Proto.entity.Entity;
import Proto.simulator.listableObj;
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
		RobotController.getInstance().rmRobot(this);
	}

	/**
	 * Robot explodes, therefore dies.
	 */
	@Override
	public void explode() {
		Thing dest = location.randomNeighbour();
		move(dest);
	}

	/**
	 * Lists the attributes of the object.
	 * @return the attributes as a string.
	 */
	@Override
	public String List() {
		StringBuilder result = new StringBuilder("+------------------+\n");

		result.append("name: " + getName() + "\n");
		result.append("\nlocation: " + location.getName() + "\n");

		return result.toString();
	}
}
