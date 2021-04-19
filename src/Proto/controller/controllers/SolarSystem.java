package Proto.controller.controllers;

import Proto.Main;
import Proto.controller.Controller;
import Proto.entity.entities.Robot;
import Proto.entity.entities.Settler;
import Proto.things.Thing;
import Proto.things.gate.TeleportGate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class SolarSystem implements Controller {
	private LinkedList<Thing> things = new LinkedList<>();
	private LinkedList<TeleportGate> gates = new LinkedList<>();
	private String name;
	private int untilEruption = -1;
	public static SolarSystem ref;

	public SolarSystem(String name) {
		this.name = name;
		ref = this;
	}

	/**
	 * Removes a thing from the SolarSystem
	 * @param t The thing that is removed.
	 */
	public void removeThing(Thing t) {
		things.remove(t);
	}

	public void removeThing(TeleportGate t) {
		things.remove(t);
		gates.remove(t);
	}

	/**
	 * Add a thing from the SolarSystem
	 * @param t The thing that is added.
	 */
	public void addThing(Thing t) {
		things.add(t);
	}

	public void addThing(TeleportGate t) {
		things.add(t);
		gates.add(t);
	}

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
					inProcess.offer(nei);
					touched.offer(nei);
					bfs.put(nei, bfs.get(underProcess) + 1);
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
		affected.forEach(Thing::applySunEruption);
		untilEruption = -1;
	}

	public void makeSolarEruption(Thing start, int r) {
		LinkedList<Thing> affected = makeArea(start, r);
		affected.forEach(Thing::applySunEruption);
		untilEruption = -1;
	}

	public String getName(){
		return name;
	}

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
	}

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
}
