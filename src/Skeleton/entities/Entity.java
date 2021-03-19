package Skeleton.entities;

import Skeleton.things.Thing;

public abstract class Entity {
	protected Thing location;
	protected String name;

	public Entity(String name) {
		this.name = name;
	}

	public void drill() {
	}
	
	public void move(Thing destination) {
	}

	public void waitEntity() {

	}
	
	public abstract void die();
	
	public abstract void explode();
}
