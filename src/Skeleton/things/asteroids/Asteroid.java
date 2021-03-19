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
	public ArrayList<Entity> etities = new ArrayList<>();
	private String name;

	public Asteroid(String name) {
		this.name = name;
	}

	public void explode() {
		Main.printTabs();
		System.out.println(name + " " + Main.call++ + " explode()");
		etities.forEach((e) -> {
			Main.tabs++;
			e.explode();
		});
		Main.tabs--;
	}
	
	public Material excavate() {
		return null;
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
