package Skeleton.entities.children;

import Skeleton.Main;
import Skeleton.controllers.SolarSystem;
import Skeleton.entities.Entity;
import Skeleton.things.Thing;

public class Robot extends Entity {
	private SolarSystem mySystem;

	public Robot(String name, Thing location) {
		super(name, location);
	}

	@Override
	public void die() {
		Main.printTabs();
		System.out.println(Main.call + " " + name + " die()");
		Main.increaseTab();

		Main.decreaseTab();
	}

	@Override
	public void explode() {
		Main.printTabs();
		System.out.println(Main.call + " " + name + " explode()");
		Main.increaseTab();

		die();

		Main.decreaseTab();
	}
}
