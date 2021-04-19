package Proto.controller.controllers;

import Proto.controller.Controller;

public class UfoController implements Controller {
	public static UfoController ref;

	@Override
	public void makeTurn() {
		ref = this;
	}

	@Override
	public void handleCommand(String line) {

	}

	public void rmUfo(Ufo ufo) {
		ufos.remove(ufo);
	}
}
