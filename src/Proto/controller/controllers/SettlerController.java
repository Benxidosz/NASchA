package Proto.controller.controllers;

import Proto.GameManager;
import Proto.Main;
import Proto.controller.Controller;
import Proto.entity.entities.Settler;
import Proto.material.Material;
import Proto.things.Thing;
import Proto.things.gate.TeleportGate;

import java.util.LinkedList;

/**
 * Represents the controller class of the settlers.
 * Handles the input from the interface, from the
 * user, and stores the settler that are done.
 */
public class SettlerController implements Controller {
	/**
	 * A reference for the class.
	 */
	private static SettlerController ref;
	/**
	 * The id of the settler.
	 * Used for the name of the object.
	 */
	private static int settlerId;

	/**
	 * Returns the reference.
	 * @return The reference
	 */
	public static SettlerController getInstance() {
		return ref;
	}

	/**
	 * Sets the reference and the id.
	 */
	public static void init() {
		ref = new SettlerController();
		settlerId = 0;
	}

	/**
	 * Returns the settlers.
	 * @return The settlers.
	 */
	public LinkedList<Settler> getSettlers() {
		return settlers;
	}

	/**
	 * Stores the settlers in the Solar System in a list.
	 */
	private final LinkedList<Settler> settlers = new LinkedList<>();
	/**
	 * Stores the number of settlers that are done.
	 */
	private int doneSettlers = 0;

	/**
	 * The constructor of the class
	 */
	private SettlerController() {

	}

	/**
	 * Returns the settler by his name
	 * @param name Name of the settler
	 * @return The settler
	 */
	public Settler getSettlerByName(String name) {
		for (Settler settler : settlers) {
			if (settler.getName().equals(name)) {
				return settler;
			}
		}

		return null;
	}

	/**
	 * Returns the id of the settler.
	 * @return The id of the settler.
	 */
	public static String  getSettlerId() {
		return "s" + settlerId++;
	}

	/**
	 * Handles the inputs from the interface.
	 * @param command the input from the interface.
	 */
	public void handleCommand(String command) {
		String[] args = command.split(" ");

		Settler selected = null;
		if (args.length > 1)
			selected = getSettlerByName(args[1]);

		if ("List".equals(args[0])) {
			if (args.length < 2) {
				settlers.forEach(s -> System.out.println(s.getName()));
			} else {
				if (selected != null) {
					System.out.println(selected.getName() + ": " + (selected.isActive() ? "Ready" : "Worked"));
					System.out.println("Location and its neighbours:");
					System.out.println("\tLocation: " + selected.getLocation().getName());
					System.out.println("\tLocation layer: " + selected.getLocation().getLayer());
					if (selected.getLocation().getLayer() == 0)
						System.out.println("\tLocation core: " + selected.getLocation().getCore());
					System.out.println("\t Neis:");
					selected.getLocation().getNeighbour().forEach(nei -> System.out.println("\t\t" + nei.getName()));
					System.out.println("\tGates:");
					selected.getGates().forEach(g -> System.out.println("\t\t" + g.getName()));
					System.out.println("\tMaterials:");
					selected.getMyInventory().getMaterials().forEach(m -> System.out.println("\t\t" + m.getName()));
				}
			}
		}

		if (selected == null || !selected.isActive())
			return;

		if ("Move".equals(args[0])) {
			if (selected.getLocation() != null) {
				Thing dest = selected.getLocation().getNeiByName(args[2]);
				selected.move(dest);
			}
		} else if ("Drill".equals(args[0])) {
			selected.drill();
		} else if ("Mine".equals(args[0])) {
			selected.mine();
		} else if ("Buildrobot".equals(args[0])) {
			selected.buildRobot(args[2]);
 		} else if ("Buildgate".equals(args[0])) {
			selected.buildGate(args[2]);
		} else if ("Buildbase".equals(args[0])) {
			selected.buildBase();
		} else if ("Putdown".equals(args[0])) {
			Material material = selected.getMaterialByName(args[2]);
			TeleportGate tg = selected.getGateByName(args[2]);
			if (material != null) {
				selected.placeMaterial(material);
			} else if (tg != null) {
				selected.putGateDown(tg);
			}
		}
	}

	/**
	 * Increases the number of the settlers that are done.
	 */
	public void done() {
		++doneSettlers;
	}

	/**
	 * Adds a settler to the list where the settlers
	 * of the Solar System are stored in.
	 * @param s the Settler which is added.
	 */
	public void addSettler(Settler s) {
		settlers.add(s);
	}

	/**
	 * Removes a settler from the list where the settlers
	 * of the Solar System are stored in.
	 * @param s the Settler which is removed.
	 */
	public void rmSettler(Settler s) {
		settlers.remove(s);
		if (settlers.size() == 0)
			GameManager.getInstance().lose();
	}

	/**
	 * Handles a turn for the settlers.
	 */
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

			if (line.equals("quit")) {
				GameManager.getInstance().makeQuit();
				ended = true;
			}
		}

		GameManager.getInstance().jobsDone();
	}
}
