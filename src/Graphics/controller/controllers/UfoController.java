package Graphics.controller.controllers;

import Graphics.controller.GameManager;
import Graphics.controller.Controller;
import Graphics.entity.entities.Ufo;
import Graphics.thing.Thing;

import java.util.LinkedList;

/**
 * Controls the ufos activities.
 * Calculates the next step for each ufo.
 */
public class UfoController extends Controller {
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
	public static void init(GameManager manager) {
		ref = new UfoController(manager);
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
	public UfoController(GameManager manager) {
		super(manager);
		ref = this;
	}

	/**
	 * Calculates the next step of the ufo.
	 * If the location is not drillable and the core is not null it mines,
	 * otherwise moves to a random neighbour.
	 * @param u the Ufo which makes the step.
	 */
	private void calculateStep(Ufo u) {
		Thing tmpLoc = u.getLocation();
		if (!tmpLoc.isDrillable() && tmpLoc.getCore() != null)
			u.mine();
		else {
			u.move(tmpLoc.randomNeighbour());
		}
	}

	/**
	 * Calls the calculateStep function for each ufo
	 * in the Solar System.
	 */
	@Override
	public void makeTurn() {
		ufos.forEach(this::calculateStep);
		manager.jobsDone();
	}

	/**
	 * Handles the instruction received.
	 * @param command The instruction
	 */
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


	/**
	 * Returns the ufos in the game.
	 * @return the ufos
	 */
	public LinkedList<Ufo> getUfos() {
		return ufos;
	}

	/**
	 * Returns the ufo with the given name.
	 * @param name the name
	 * @return the ufo
	 */
	public Ufo getUfoByName(String name) {
		for (Ufo ufo : ufos) {
			if (ufo.getName().equals(name)) {
				return ufo;
			}
		}

		return null;
	}

	/**
	 * Removes an ufo, got as a parameter, from the
	 * ufos array.
	 * @param ufo the Ufo which is deleted.
	 */
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
