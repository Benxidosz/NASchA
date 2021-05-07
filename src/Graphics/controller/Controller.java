package Graphics.controller;

/**
 * Represents the controllers as an interface
 */
public interface Controller {
	/**
	 * Controller making turn.
	 */
	void makeTurn();

	/**
	 * Handles the instruction received.
	 * @param line The instruction
	 */
	void handleCommand(String line);
}
