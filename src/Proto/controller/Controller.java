package Proto.controller;

import Proto.GameManager;

public abstract class Controller {
	protected final GameManager manager;

	public Controller(GameManager gm) {
		manager = gm;
	}

	public abstract void makeTurn();
}
