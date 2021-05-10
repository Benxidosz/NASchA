package Graphics.controller;

/**
 * Represents the controllers as an interface
 */
public abstract class Controller {

	/**
	 * The actual game manager
	 */
	protected final GameManager manager;

	/**
	 * Set own manager
	 * @param manager
	 */
	protected Controller(GameManager manager) {
		this.manager = manager;
	}

	/**
	 * Controller making turn.
	 */
	public abstract void makeTurn();

	/**
	 * Handles the instruction received.
	 * @param line The instruction
	 */
	public abstract void handleCommand(String line);
}
