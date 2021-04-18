package Proto.controller.controllers;

import Proto.GameManager;
import Proto.Main;
import Proto.controller.Controller;
import Proto.entity.entities.Settler;

import java.util.LinkedList;
import java.util.Scanner;

public class SettlerController implements Controller {
	public static SettlerController ref;

	private final LinkedList<Settler> settlers = new LinkedList<>();
	private int doneSettlers = 0;

	public SettlerController() {
		ref = this;
	}

	public void handleCommand(String command) {
		String[] args = command.split(" ");

	}

	public void done() {
		++doneSettlers;
	}

	public void addSettler(Settler s) {
		settlers.add(s);
	}

	public void rmSettler(Settler s) {
		settlers.remove(s);
	}

	@Override
	public void makeTurn() {
		doneSettlers = 0;
		settlers.forEach(s -> s.setActive(true));

		boolean ended = false;
		while (!ended) {
			String line = Main.scanner.nextLine();
			handleCommand(line);

			if (line.equals("endTurn") && doneSettlers == settlers.size())
				ended = true;
		}

		GameManager.ref.jobsDone();
	}
}
