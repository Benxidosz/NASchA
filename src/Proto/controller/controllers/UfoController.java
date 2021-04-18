package Proto.controller.controllers;

import Proto.GameManager;
import Proto.controller.Controller;

public class UfoController implements Controller {
	public static UfoController ref;

	@Override
	public void makeTurn() {
		ref = this;
	}
}
