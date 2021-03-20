package Skeleton.entities.children;

import Skeleton.controllers.SolarSystem;
import Skeleton.entities.Entity;
import Skeleton.things.Thing;

public class Robot extends Entity {
	private SolarSystem mySystem;

	public Robot(String name, Thing location) {
		super(name, location);
	}


	public void die() {
	}

	public void explode() {
	}
}
