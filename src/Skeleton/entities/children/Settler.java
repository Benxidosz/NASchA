package Skeleton.entities.children;

import Skeleton.Main;
import Skeleton.controllers.SolarSystem;
import Skeleton.things.gate.TeleportGate;
import Skeleton.entities.Entity;
import Skeleton.materials.Material;

public class Settler extends Entity {
	private TeleportGate gates;
	private SolarSystem mySystem;
	private Material materials;

	public Settler(String name) {
		super(name);
	}

	public void mine() {
		Main.printTabs();
		System.out.println(" " + Main.call++ + " die()");
		Main.tabs++;
		die();
		Main.tabs--;
	}
	
	public void buildBase() {
	}
	
	public void buildGate() {
	}
	
	public void buildRobot() {
	}
	
	public void placeMaterial(Material m) {
	}
	
	public void putGateDown(TeleportGate g) {
	}
	
	public void addGate(TeleportGate g) {
	}
	
	public void addMaterial(Material m) {
	}
	
	public void rmMaterial(Material m) {
	}

	@Override
	public void die() {
		Main.printTabs();
		System.out.println(name + " " + Main.call++ + " die()");
		Main.tabs--;;
	}

	@Override
	public void explode() {
		Main.printTabs();
		System.out.println(name + " " + Main.call++ + " explode()");
		Main.tabs++;
		die();
		Main.tabs--;;
	}

}
