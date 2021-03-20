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
	}
	
	public void move(Thing destination) {
		Main.printTabs();
		System.out.println();
	}

	public void waitEntity() {

	}
	
	public abstract void die();
	
	public abstract void explode();
}
