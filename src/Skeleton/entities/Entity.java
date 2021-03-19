package Skeleton.entities;

import Skeleton.things.Thing;

public abstract class Entity {
	protected Thing location;
	public void drill() {
	}
	
	public void move(Thing destination) {
	}

	public void wait() {

	}
	
	public abstract void die();
	
	public abstract void explode();
}
