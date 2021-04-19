package Proto.controller.controllers;

import Proto.GameManager;
import Proto.controller.Controller;
import Proto.entity.entities.Robot;
import Proto.entity.entities.Settler;
import Proto.entity.entities.Ufo;
import Proto.things.Thing;

import java.util.LinkedList;

public class UfoController implements Controller {
	public static UfoController ref;
	private LinkedList<Ufo> ufos;

	public UfoController() {
		ref = this;
	}

	private void calculateStep(Ufo u) {
		Thing tmpLoc = u.getLocation();
		if (!tmpLoc.isDrillable() && tmpLoc.getCore() != null)
			u.mine();
		else {
			u.move(tmpLoc.randomNeighbour());
		}
	}

	@Override
	public void makeTurn() {
		ufos.forEach(this::calculateStep);
		GameManager.ref.jobsDone();
	}

	@Override
	public void handleCommand(String command) {
		String[] args = command.split(" ");

		Ufo selected = null;
		for (Ufo settler : ufos) {
			if (settler.getName().equals(args[1])) {
				selected = settler;
				break;
			}
		}

		if ("Move".equals(args[0])) {
			if (selected.getLocation() != null) {
				Thing dest = selected.getLocation().getNeiByName(args[2]);
				selected.move(dest);
			}
		} else if ("Mine".equals(args[0])) {
			selected.mine();
		}
	}

	public void rmUfo(Ufo ufo) {
		ufos.remove(ufo);
	}
}
