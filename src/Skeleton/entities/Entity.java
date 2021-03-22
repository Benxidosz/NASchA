package Skeleton.entities;

import Skeleton.Main;
import Skeleton.things.Thing;

import java.sql.SQLOutput;

public abstract class Entity {
	protected Thing location;
	protected String name;

	public Entity(String name, Thing location) {
		this.name = name;
		this.location = location;
	}

	protected void addAllObject(Step step) {
		step.addObject(this);
		step.addObject(location);
	}

	public void drill() {
		Main.printTabs();
		System.out.println(Main.call + " " + name + " drill()");
		Main.increaseTab();

		location.drill();

		Main.decreaseTab();
	}
	
	public void move(Thing destination) {
		Main.printTabs();
		System.out.println(Main.call + " " + name + " move() to " /*+ destination.getName()*/);
		Main.increaseTab();

		destination.addEntity(this);
		if (location != null)
			location.removeEntity(this);

		location = destination;
		Main.decreaseTab();
	}

	public void waitEntity() {
		Main.printTabs();
		System.out.println(Main.call + " " + name + " waitEntity()");
		Main.increaseTab();

		Main.decreaseTab();
	}

	public abstract void die();

	public abstract void explode();

	public String getName() {
		return name;
	}
}
