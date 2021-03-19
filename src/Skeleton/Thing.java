package Skeleton;

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
	}
	
	public void placeMaterial(Material m) {
	}
	
	public void buildBase(Material m) {
	}
	
	public Thing randomNeighbour() {
	}
}
