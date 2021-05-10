package Graphics.observable.entity.entities;

import Graphics.controller.controllers.RobotController;
import Graphics.observable.thing.Thing;
import Graphics.observable.entity.Entity;
import Graphics.ui.game.drawable.drawables.Obstacle;
import javafx.scene.canvas.Canvas;

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

	/**
	 * Call the given obstacle draw method, with this added as second argument.
	 * @param canvas The canvas what will be given back to the obstacle.
	 * @param obstacle The obstacle which will be gotten the information.
	 */
	@Override
	public void observe(Canvas canvas, Obstacle obstacle) {
		obstacle.draw(canvas, this);
	}
}
