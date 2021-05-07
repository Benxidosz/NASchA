package Graphics.entity.entities;

import Graphics.controller.controllers.RobotController;
import Graphics.entity.Entity;
import Graphics.things.Thing;

/**
 * The robot which is controlled by the system and can drill and move
 */
public class Robot extends Entity {

	/**
	 * The constructor of the Robot class.
	 * @param loc The location where the Robot is
	 * @param name The name of the Robot
	 */
	public Robot(String name, Thing loc){
		super(loc, name);
	}

	/**
	 * Override mine by doing nothing.
	 */
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
		result.append("location: " + location.getName() + "\n");

		return result.toString();
	}
}
