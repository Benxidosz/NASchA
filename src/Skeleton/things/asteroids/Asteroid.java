package Skeleton.things.asteroids;

import Skeleton.Main;
import Skeleton.entities.Entity;
import Skeleton.materials.Material;
import Skeleton.things.Thing;

import java.util.ArrayList;

public class Asteroid extends Thing {
	private int layer;
	private boolean nearBySun;
	protected Material core;
	protected ArrayList<Entity> etities = new ArrayList<>();
	private String name;
	public void explode() {
		Main.printTabs();
		System.out.println(" " + Main.call++ + " explode()");
		Main.tabs++;
		etities.forEach(Entity::explode);
		Main.tabs--;
	}
	
	public Material excavate() {

	}
	
	public void placeMaterial(Material m) {
	}
	
	public void drill() {
	}
	
	public void applySunEruption() {
	}
	
	public void buildBase(Material m) {
	}
}
