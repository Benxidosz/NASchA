package Proto.controller.controllers;

import Proto.GameManager;
import Proto.controller.Controller;
import Proto.entity.entities.Settler;
import Proto.entity.entities.Ufo;
import Proto.things.Thing;

import java.util.LinkedList;

public class UfoController implements Controller {
	/**
	 * A reference for the class.
	 */
	private static UfoController ref;
	/**
	 * Returns the reference.
	 * @return the reference
	 */
	public static UfoController getInstance() {
		return ref;
	}
	/**
	 * Sets the reference.
	 */
	public static void init() {
		ref = new UfoController();
	}

	/**
	 * The id of the ufo.
	 * Used for the name of the object.
	 */
	private static int ufoId;

	/**
	 * Returns the id of the ufo.
	 * @return the id of the ufo.
	 */
	public static String getUfoId() {
		return "uf" + ufoId++;
	}

	/**
	 * The ufos in the Solar System stored in list.
	 */
	private LinkedList<Ufo> ufos = new LinkedList<>();

	/**
	 * The constructor of the class
	 */
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
		GameManager.getInstance().jobsDone();
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

	public LinkedList<Ufo> getUfos() {
		return ufos;
	}

	public Ufo getUfoByName(String name) {
		for (Ufo settler : ufos) {
			if (settler.getName().equals(name)) {
				return settler;
			}
		}

		return null;
	}

	public void rmUfo(Ufo ufo) {
		ufos.remove(ufo);
	}

	/**
	 * Adds an ufo, got as a parameter, to the
	 * ufos array.
	 * @param u the Ufo which is added.
	 */
	public void addUfo(Ufo u) {
		ufos.add(u);
	}
}
