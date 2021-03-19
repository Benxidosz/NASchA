package Skeleton.things;

import Skeleton.controllers.SolarSystem;
import Skeleton.entities.Entity;
import Skeleton.materials.Material;

public abstract class Thing {
	protected Thing neighbour;
	protected Entity entities;
	private SolarSystem mySystem;
	public void addEntity(Entity entity) {
	}
	
	public void removeEntity(Entity entity) {
	}
	
	public void addNeighbour(Thing nei) {
	}
	
	public void removeNeighbour(Thing nei) {
	}
	
	public void applySunEruption() {
	}
	
	public void drill() {
	}
	
	public Material excavate() {
		return null;
	}
	
	public void placeMaterial(Material m) {
	}
	
	public void buildBase(Material m) {
	}
	
	public Thing randomNeighbour() {
		return null;
	}
}