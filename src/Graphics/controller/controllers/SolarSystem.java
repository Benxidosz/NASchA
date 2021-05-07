package Graphics.controller.controllers;

import Graphics.Main;
import Graphics.controller.Controller;
import Graphics.things.Thing;
import Graphics.things.gate.TeleportGate;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Represents the controller class of the solar system in the game.
 */
public class SolarSystem implements Controller {
	/**
	 * The things in the solar system, stored in a list.
	 */
	private LinkedList<Thing> things = new LinkedList<>();
	/**
	 * The teleport gates in the solar system, stored in a list.
	 */
	private LinkedList<TeleportGate> gates = new LinkedList<>();
	/**
	 * The remaining turns until the next sun eruption.
	 */
	private int untilEruption = -1;
	/**
	 * A reference for the class.
	 */
	private static SolarSystem ref;

	/**
	 * Return the reference.
	 * @return the reference.
	 */
	public static SolarSystem getInstance() {
		return ref;
	}

	/**
	 * Sets the reference and the IDs of the materials.
	 */
	public static void init() {
		ref = new SolarSystem();
		coalId = 0;
		ironId = 0;
		siliconId = 0;
		uranId = 0;
		waterIceId = 0;
	}

	/**
	 * The IDs of the Materials and the Things in the Solar System.
	 */
	private static int coalId;
	private static int ironId;
	private static int siliconId;
	private static int uranId;
	private static int waterIceId;
	private static int teleportGateId;
	private static int asteroidId;

	/**
	 * Returns the ID of the Coal.
	 * @return the ID.
	 */
	public static String  getCoalId() {
		return "c" + coalId++;
	}

	/**
	 * Returns the ID of the Iron.
	 * @return the ID.
	 */
	public static String getIronId() {
		return "i" + ironId++;
	}

	/**
	 * Returns the ID of the Silicon.
	 * @return the ID.
	 */
	public static String getSiliconId() {
		return "s" + siliconId++;
	}

	/**
	 * Returns the ID of the Uran.
	 * @return the ID.
	 */
	public static String getUranId() {
		return "ur" + uranId++;
	}

	/**
	 * Returns the ID of the WaterIce.
	 * @return the ID.
	 */
	public static String getWaterIceId() {
		return "w" + waterIceId++;
	}

	/**
	 * Returns the ID of the TeleportGate.
	 * @return the ID.
	 */
	public static String getTeleportGateId(){
		return "tg" + teleportGateId++;
	}

	/**
	 * Returns the ID of the Asteroid.
	 * @return the ID.
	 */
	public static String getAsteroidId() {
		return "a" + asteroidId++;
	}

	/**
	 * The constructor of the class.
	 * Sets itself as the reference.
	 */
	private SolarSystem() {
		ref = this;
	}

	/**
	 * Removes a thing from the SolarSystem
	 * @param t The thing that is removed.
	 */
	public void removeThing(Thing t) {
		things.remove(t);
	}

	/**
	 * Removes a gate from the SolarSystem
	 * @param t The gate that is removed.
	 */
	public void removeThing(TeleportGate t) {
		things.remove(t);
		gates.remove(t);
	}

	/**
	 * Add a thing from the Solar System
	 * @param t The thing that is added.
	 */
	public void addThing(Thing t) {
		things.add(t);
	}

	/**
	 * Add a TeleportGate from the Solar System
	 * @param t the TeleportGate that is added.
	 */
	public void addThing(TeleportGate t) {
		things.add(t);
		gates.add(t);
	}

	/**
	 * Calculates the area where something has effect in.
	 * Makes an circle with r radius as an area that is affected.
	 * @param start the Thing where it starts from.
	 * @param r the radius of the circle.
	 * @return the area.
	 */
	private LinkedList<Thing> makeArea(Thing start, int r) {
		LinkedList<Thing> inProcess = new LinkedList<>();
		LinkedList<Thing> touched = new LinkedList<>();
		HashMap<Thing, Integer> bfs = new HashMap<>();

		inProcess.offer(start);
		touched.offer(start);
		bfs.put(start, 0);

		while (!inProcess.isEmpty()) {
			Thing underProcess = inProcess.poll();

			if (bfs.get(underProcess) > r)
				break;

			underProcess.getNeighbour().forEach(nei -> {
				if (!touched.contains(nei)) {
					if (bfs.get(underProcess) < r) {
						inProcess.offer(nei);
						touched.offer(nei);
						bfs.put(nei, bfs.get(underProcess) + 1);
					}
				}
			});
		}

		return touched;
	}

	/**
	 * Makes a solar eruption.
	 */
	public void makeSolarEruption() {
		Thing start = things.get(Main.rng.nextInt(things.size()));
		LinkedList<Thing> affected = makeArea(start, Main.rng.nextInt(things.size() / 2) + things.size() / 10);
		affected.forEach(Thing::MainlySunEruption);
		untilEruption = -1;
	}

	/**
	 * Makes a solar eruption in a distance from the start.
	 * @param start the Thing where it starts from.
	 * @param r the radius of the circle.
	 */
	public void makeSolarEruption(Thing start, int r) {
		LinkedList<Thing> affected = makeArea(start, r);
		affected.forEach(Thing::MainlySunEruption);
		untilEruption = -1;
	}

	/**
	 * Makes a turn in the game.
	 * Steps with the TeleportGates and handles the eruption.
	 */
	@Override
	public void makeTurn() {
		gates.forEach(TeleportGate::makeTurn);

		if (untilEruption == -1) {
			if (Main.rng.nextBoolean()) {
				untilEruption = Main.rng.nextInt(5) + 3;
			}
		} else {
			untilEruption--;
			if (untilEruption == 0) {
				makeSolarEruption();
			}
		}
		System.out.println(untilEruption == -1 ? "No SolarEruption danger." : "Danger! Eruption, after " + untilEruption + " turn.");
	}

	/**
	 * Handle the input from the interface.
	 * @param command the input.
	 */
	@Override
	public void handleCommand(String command) {
		String[] args = command.split(" ");

		if (args.length == 1 && args[0].equals("Makeeruption")) {
			makeSolarEruption();
		} else if (args.length == 3 && args[0].equals("Makeeruption")) {
			Thing start = null;
			for (Thing t : things)
				if (t.getName().equals(args[1])) {
					start = t;
					break;
				}

			if (start != null)
				makeSolarEruption(start, Integer.parseInt(args[2]));
		}
	}

	/**
	 * Returns thing with the given name.
	 * @param name the name
	 * @return the thing
	 */
	public Thing getThingByName(String name) {
		for (Thing t : things)
			if (t.getName().equals(name))
				return t;

		return null;
	}

	/**
	 * Returns the things in the game.
	 * @return the things
	 */
	public LinkedList<Thing> getThings() {
		return things;
	}
}
