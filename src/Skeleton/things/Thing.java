package Skeleton.things;

import Skeleton.controllers.SolarSystem;
import Skeleton.entities.Entity;
import Skeleton.materials.Material;

import java.util.ArrayList;

public abstract class Thing {
	protected ArrayList<Thing> neighbour = new ArrayList<>();
	protected ArrayList<Entity> entities = new ArrayList<>();
	private SolarSystem mySystem;
	public void addEntity(Entity entity) {
		entities.add(entity);
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
