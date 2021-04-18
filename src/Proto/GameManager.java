package Proto;

import Proto.controller.Controller;

import java.util.HashMap;

public class GameManager {
	public static GameManager ref;
	public HashMap<String, Inventory> recipes;

	private Controller controllers;


	public GameManager() {
		ref = this;
	}

	public void jobsDone() {
	}

	public void newTurn() {

	}

	public void newGame() {

	}

	public void win() {

	}

	public void lose() {

	}
}
