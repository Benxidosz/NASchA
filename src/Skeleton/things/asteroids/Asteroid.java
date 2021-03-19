package Skeleton.things.asteroids;

import Skeleton.materials.Material;
import Skeleton.things.Thing;

public class Asteroid extends Thing {
	private int layer;
	private boolean nearBySun;
	protected Material core;
	public void explode() {

		System.out.println("Asteroid explode()");
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
