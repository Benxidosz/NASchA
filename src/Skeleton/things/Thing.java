package Skeleton.things;

import Skeleton.Main;
import Skeleton.controllers.SolarSystem;
import Skeleton.entities.Entity;
import Skeleton.materials.Material;
import Skeleton.things.gate.TeleportGate;

import java.util.ArrayList;

public abstract class Thing {
	protected ArrayList<Thing> neighbour = new ArrayList<>();
	protected ArrayList<Entity> entities = new ArrayList<>();
	private SolarSystem mySystem;
	protected String name;

	public Thing(String name) {
		this.name = name;
	}

	public void addEntity(Entity entity) {
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " addEntity() void.");
		entities.add(entity);

		Main.decreaseTab();
	}

	public void removeEntity(Entity entity) {
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " removeEntity() void.");
		entities.remove(entity);

		Main.decreaseTab();
	}

	public void addNeighbour(Thing nei) {
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " addNeighbour() void.");
		neighbour.add(nei);

		Main.decreaseTab();
	}

	public void removeNeighbour(Thing nei) {
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " removeNeighbour() void.");
		neighbour.remove(nei);

		Main.decreaseTab();
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
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " randomNeighbour() void.");

		Main.decreaseTab();
		return null;
	}

	public void setPair(TeleportGate gate2) {

	}
}