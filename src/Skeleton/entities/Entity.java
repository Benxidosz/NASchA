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
		location.removeEntity(this);

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
