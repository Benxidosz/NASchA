package Skeleton.entities.children;

import Skeleton.Main;
import Skeleton.controllers.SolarSystem;
import Skeleton.materials.children.Coal;
import Skeleton.materials.children.Uran;
import Skeleton.things.Thing;
import Skeleton.things.gate.TeleportGate;
import Skeleton.entities.Entity;
import Skeleton.materials.Material;

import java.util.ArrayList;

public class Settler extends Entity {
	private TeleportGate[] gates;
	private SolarSystem mySystem;
	private ArrayList<Material> materials;

	public Settler(String name, Thing location) {
		super(name, location);
		materials = new ArrayList<Material>();
	}

	public void mine() {
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " mine()");
		Main.increaseTab();

		Material m = location.excavate();
		addMaterial(m);

		Main.decreaseTab();
	}
	
	public void buildBase() {
		Main.printTabs();
		System.out.println(Main.call + " " + name + " buildBase()");
		Main.increaseTab();

		for(int i = 0; i < materials.size(); ++i){
			if(materials.get(i) instanceof Uran)
				location.buildBase(materials.get(i));

			if(materials.get(i) instanceof Coal){
				location.buildBase(materials.get(i));
			}
		}

		Main.decreaseTab();
	}
	
	public void buildGate() {
		Main.printTabs();
		System.out.println(Main.call + " " + name + " buildGate()");
		Main.increaseTab();


		TeleportGate gate1 = new TeleportGate("firstGate");
		TeleportGate gate2 = new TeleportGate( "secondGate");
		gate1.setPair(gate2);
		gate2.setPair(gate1);
		addGate(gate1);
		addGate(gate2);

		Main.decreaseTab();
	}
	
	public void buildRobot() {
		Main.printTabs();
		System.out.println(Main.call + " " + name + " buildRobot()");
		Main.increaseTab();

		for(int i = 0; i < materials.size(); ++i){
			rmMaterial(materials.get(i));
		}

		Robot r = new Robot("robot", this.location);
		mySystem.addRobot(r);

		Main.decreaseTab();
	}
	
	public void placeMaterial(Material m) {
		Main.printTabs();
		System.out.println(Main.call + " " + name + " placeMaterial()");
		Main.increaseTab();

		location.placeMaterial(m);
		rmMaterial(m);

		Main.decreaseTab();
	}
	
	public void putGateDown(TeleportGate g) {
		Main.printTabs();
		System.out.println(Main.call + " " + name + " putGateDown()");
		Main.increaseTab();

		g.addNeighbour(location);
		//g.getPair().addNeighbour(location); ToDo: Miert?????
		mySystem.addThing(g);
		g.activate();
		g.removeEntity(this);

		Main.decreaseTab();
	}
	
	public void addGate(TeleportGate g) {
		Main.printTabs();
		System.out.println(Main.call + " " + name + " addGate()");
		Main.increaseTab();

		if(gates[0] == null)
			gates[0] = g;
		else
			gates[1] = g;


		Main.decreaseTab();
	}
	
	public void addMaterial(Material m) {
		Main.printTabs();
		System.out.println(Main.call + " " + name + " addMaterial()");
		Main.increaseTab();

		materials.add(m);

		Main.decreaseTab();
	}
	
	public void rmMaterial(Material m) {
		Main.printTabs();
		System.out.println(Main.call + " " + name + " rmMaterial()");
		Main.increaseTab();

		materials.remove(m);

		Main.decreaseTab();
	}

	@Override
	public void die() {
		Main.printTabs();

		System.out.println(Main.call++ + " " + name +  " die()");

		Main.decreaseTab();;
	}

	@Override
	public void explode() {
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " explode()");
		Main.increaseTab();

		die();

		Main.decreaseTab();;
	}

}
