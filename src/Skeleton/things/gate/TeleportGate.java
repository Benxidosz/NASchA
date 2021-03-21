package Skeleton.things.gate;

import Skeleton.Main;
import Skeleton.entities.children.Settler;
import Skeleton.things.Thing;

public class TeleportGate extends Thing {
	public Settler Unnamed1;
	private TeleportGate pair;

	public TeleportGate(String name) {
		super(name);
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " created.");

		Main.decreaseTab();
	}

	public void applySunEruption() {
		Main.printTabs();
		System.out.print(Main.call++ + " " + name + " applySunEruption() void ");

		System.out.println("eruption applied (TeleportGate is not safe).");
		entities.forEach((e) -> {
			Main.increaseTab();
			e.die();
		});

		Main.decreaseTab();
	}
	
	public void activate() {
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " activate() void ");

		Main.decreaseTab();
	}
}
