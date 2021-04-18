package Proto.controller.controllers;

import Proto.GameManager;
import Proto.controller.Controller;
import Proto.entity.entities.Settler;

import java.util.LinkedList;

public class SettlerController extends Controller {
	public final LinkedList<Settler> settlers = new LinkedList<>();

	public SettlerController(GameManager gm) {
		super(gm);
	}

	@Override
	public void makeTurn() {

	}
}
