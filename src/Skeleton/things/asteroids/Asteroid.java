package Skeleton.things.asteroids;

import Skeleton.Main;
import Skeleton.entities.Entity;
import Skeleton.materials.Material;
import Skeleton.simulator.SimulationObject;
import Skeleton.simulator.Step;
import Skeleton.things.Thing;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Asteroid extends Thing implements SimulationObject {
	protected int layer;
	protected boolean nearBySun;
	protected Material core;

	/**
	 * Adds all objects to a step.
	 * @param step
	 */
	@Override
	protected void addAllObject(Step step) {
		super.addAllObject(step);
		step.addObject(core);
	}

	/**
	 * The constructor of the Asteroid class. Sets it's attributes.
	 * @param name The name of the object
	 */
	public Asteroid(String name) {
		super(name);

		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " created.");

		Main.activeSimulation.addStep(step);

		this.layer = layer;
		this.nearBySun = nearBySun;
		this.core = core;

		Main.decreaseTab();
	}

	/**
	 * Calls explode for all entities on it.
	 */
	public void explode() {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " explode()");

		addAllObject(step);

		Main.activeSimulation.addStep(step);

		ArrayList<Entity> tmp = new ArrayList<>(entities);

		tmp.forEach((e) -> {
			Main.increaseTab();
			e.explode();
		});

		neighbour.forEach(nei -> {
			for (Thing nei2 : neighbour) {
				Main.increaseTab();
				nei.addNeighbour(nei2);
			}
			Main.increaseTab();
			nei.removeNeighbour(this);
		});

		Main.decreaseTab();
	}

	/**
	 * The asteroid gets excavated and sets it's core to null.
	 * @return The core Material.
	 */
	@Override
	public Material excavate() {
		Material ret = core;
		StringBuilder builder = new StringBuilder();
		builder.append(Main.printTabs() + Main.call++ + " " + name + " excavate() return " + core.getName());

		System.out.println("Is the core reachable? [Y/N]");
		try{
			Step step;
			String input = Main.scanner.nextLine().toUpperCase();
			if (input.equals("Y")) {
				builder.append(" core was mined");
				step = new Step(builder.toString());
				addAllObject(step);
				Main.activeSimulation.addStep(step);

				Main.increaseTab();
				setCore(null);
			} else if (input.equals("N")) {
				builder.append(" core was not mined.");
				step = new Step(builder.toString());
				addAllObject(step);
				Main.activeSimulation.addStep(step);
			} else {
				throw new InputMismatchException("Wrong Input!");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}


		Main.decreaseTab();
		return ret;
	}

	/**
	 * Place a Material in it's core
	 * @param m
	 * @return
	 */
	@Override
	public boolean placeMaterial(Material m) {
		StringBuilder builder = new StringBuilder();
		boolean ret = false;

		builder.append(Main.printTabs()).append(Main.call++).append(" ").append(name).append(" placeMaterial(" + m.getName() + ")");

		System.out.println("All condition set for placement? [Y/N]");
		try {
			Step step;
			String input = Main.scanner.nextLine().toUpperCase();
			if (input.equals("Y")) {
				ret = true;
				layer = 0;
				core = null;

				builder.append(" core set.");
				builder.append(" return true");
				step = new Step(builder.toString());
				step.addObject(m);
				addAllObject(step);
				Main.activeSimulation.addStep(step);

				Main.increaseTab();
				setCore(m);

				System.out.println("Is sun near? [Y/N]");
				input = Main.scanner.nextLine().toUpperCase();
				if (input.equals("Y")) {
					nearBySun = true;
					Main.increaseTab();
					m.nearSun();
				} else if (input.equals("N")) {
					nearBySun = false;
				} else {
					throw new InputMismatchException("Wrong Input!");
				}
			} else if (input.equals("N")) {
				builder.append(" core not set.");
				builder.append(" return false");
				step = new Step(builder.toString());
				step.addObject(m);
				addAllObject(step);
				Main.activeSimulation.addStep(step);
			} else {
				throw new InputMismatchException("Wrong Input!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Main.decreaseTab();
		return ret;
	}

	@Override
	public void drill() {
		StringBuilder builder = new StringBuilder();

		builder.append(Main.printTabs()).append(Main.call++).append(" ").append(name).append(" drill()");

		System.out.println("Is it the last layer? [Y/N]");
		try {
			Step step;
			String input = Main.scanner.nextLine().toUpperCase();
			if (input.equals("Y")) {
				builder.append(" last layer drilled.");
				System.out.println("Is it near sun? [Y/N]");
				input = Main.scanner.nextLine().toUpperCase();
				if(input.equals("Y")){
					step = new Step(builder.toString());
					addAllObject(step);
					Main.activeSimulation.addStep(step);
					Main.increaseTab();
					core.nearSun();
				}else if(!input.equals("N")) {
					throw new InputMismatchException("Wrong Input!");
				}
			} else if (input.equals("N")) {
				builder.append(" not the last layer not drilled.");
				step = new Step(builder.toString());
				addAllObject(step);
				Main.activeSimulation.addStep(step);
			} else {
				throw new InputMismatchException("Wrong Input!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Main.decreaseTab();
	}

	@Override
	public void applySunEruption() {
		StringBuilder builder = new StringBuilder();

		builder.append(Main.printTabs()).append(Main.call++).append(" ").append(name).append(" applySunEruption()");

		System.out.println("Is the Asteroid safe? [Y/N]");
		try {
			Step step;
			String input = Main.scanner.nextLine().toUpperCase();
			if (input.equals("Y")) {
				builder.append("eruption not applied.");
				step = new Step(builder.toString());
				Main.activeSimulation.addStep(step);
			} else if (input.equals("N")) {
				builder.append("eruption applied.");
				step = new Step(builder.toString());
				Main.activeSimulation.addStep(step);
				entities.forEach((e) -> {
					Main.increaseTab();
					e.die();
				});
			} else {
				throw new InputMismatchException("Wrong Input!");
			}

			addAllObject(step);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Main.decreaseTab();
	}

	@Override
	public void buildBase(Material m) {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " buildBase() (it does nothing)");

		addAllObject(step);
		step.addObject(m);

		Main.activeSimulation.addStep(step);

		Main.decreaseTab();
	}

	public void setCore(Material m) {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " setCore(" + (m != null? m.getName() : "null") + ")");

		addAllObject(step);
		step.addObject(m);

		Main.activeSimulation.addStep(step);

		core = m;
		if (m != null) {
			Main.increaseTab();
			m.setMyAsteroid(this);
		}
		Main.decreaseTab();
	}

	@Override
	public void listParameters() {
		System.out.println(name + ":\n" +
				"layer: " + layer + "\n" +
				"core: " + core.getName() + "\n" +
				"near by sun: ");
		if(nearBySun)
			System.out.println("true");
		else
			System.out.println("false");
		System.out.println("Neighbours: " );
		for(int i=0; i<neighbour.size(); ++i)
			System.out.println(neighbour.get(i).getName() + " ");
		System.out.println("Entities: ");
		for(int i=0; i<entities.size(); ++i)
			System.out.println(entities.get(i).getName() + " ");
	}

	@Override
	public String printName() {
		return name;
	}
}
