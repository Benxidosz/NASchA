package Skeleton.things.asteroids;

import Skeleton.Main;
import Skeleton.entities.Entity;
import Skeleton.materials.Material;
import Skeleton.simulator.SimulationObject;
import Skeleton.simulator.Step;
import Skeleton.things.Thing;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;

public class Asteroid extends Thing implements SimulationObject {
	protected int layer;
	protected boolean nearBySun;
	protected Material core;

	public Asteroid(int layer, boolean nearBySun, Material core, String name) {
		super(name);

		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " created.");
		step.addObject(this);

		Main.activeSimulation.addStep(step);

		this.layer = layer;
		this.nearBySun = nearBySun;
		this.core = core;

		Main.decreaseTab();
	}

	public void explode() {
		System.out.println(Main.printTabs() + Main.call++ + " " + name + " explode() void.");

		entities.forEach((e) -> {
			Main.increaseTab();
			e.explode();
		});

		Main.decreaseTab();
	}

	public Material excavate() {
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " explode() " + core + ".");

		Main.decreaseTab();
		return core;
	}

	public void placeMaterial(Material m) {
		Main.printTabs();
		System.out.print(Main.call++ + " " + name + " placeMaterial() void ");

		System.out.println("All condition set? [Y/N]");
		try {
			String input = Main.scanner.nextLine().toUpperCase();
			if (input.equals("Y")) {
				System.out.println("core set.");
			} else if (input.equals("N")) {
				System.out.println("core not set.");
			} else {
				throw new InputMismatchException("Wrong Input!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Main.decreaseTab();
	}

	public void drill() {
		Main.printTabs();
		System.out.print(Main.call++ + " " + name + " drill() void ");

		System.out.println("Is there any layer left? [Y/N]");
		try {
			String input = Main.scanner.nextLine().toUpperCase();
			if (input.equals("Y")) {
				System.out.println("layer drilled.");
			} else if (input.equals("N")) {
				System.out.println("layer not drilled.");
			} else {
				throw new InputMismatchException("Wrong Input!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Main.decreaseTab();
	}

	public void applySunEruption() {
		StringBuilder builder = new StringBuilder();

		builder.append(Main.printTabs()).append(Main.call++).append(" ").append(name).append(" applySunEruption() void ");

		System.out.println("Is the Asteroid safe? [Y/N]");
		try {
			String input = Main.scanner.nextLine().toUpperCase();
			if (input.equals("Y")) {
				builder.append("eruption not applied.");
				Step step = new Step(builder.toString());
				Main.activeSimulation.addStep(step);

			} else if (input.equals("N")) {
				builder.append("eruption applied.");
				Step step = new Step(builder.toString());
				Main.activeSimulation.addStep(step);

				entities.forEach((e) -> {
					Main.increaseTab();
					e.die();
				});
			} else {
				throw new InputMismatchException("Wrong Input!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Main.decreaseTab();
	}

	public void buildBase(Material m) {
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " buildBase() void (it does nothing)");

		Main.decreaseTab();
	}

	public void setCore(Object o) {
	}

	@Override
	public void listParameters() {
		System.out.println(name + ":\n" +
				"layer: " + layer + "\n");
	}

	@Override
	public String printName() {
		return name;
	}
}
