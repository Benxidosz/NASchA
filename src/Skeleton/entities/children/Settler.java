package Skeleton.entities.children;

import Skeleton.Main;
import Skeleton.controllers.SolarSystem;
import Skeleton.materials.children.Coal;
import Skeleton.materials.children.Uran;
import Skeleton.simulator.SimulationObject;
import Skeleton.simulator.Step;
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

	@Override
	protected void addAllObject(Step step) {
		super.addAllObject(step);

		for (Material m : materials)
			step.addObject(m);
		step.addObject(gates[0]);
		step.addObject(gates[1]);
		step.addObject(mySystem);
	}

	public void mine() {
		StringBuilder builder = new StringBuilder();
		builder.append(Main.printTabs() + Main.call++ + " " + name + " mine()");

		System.out.println("Can the settler store more? [Y/N]");
		try{
			Step step;
			String input = Main.scanner.nextLine().toUpperCase();
			if (input.equals("Y")) {
				builder.append("asteroid mined.");
				step = new Step(builder.toString());
				addAllObject(step);
				Main.activeSimulation.addStep(step);

				Main.increaseTab();
				Material m = location.excavate();

				Main.increaseTab();
				addMaterial(m);
			} else if (input.equals("N")) {
				builder.append("asteroid was not mined.");
				step = new Step(builder.toString());
				addAllObject(step);
				Main.activeSimulation.addStep(step);
			} else {
				throw new InputMismatchException("Wrong Input!");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

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

		Main.increaseTab();
		TeleportGate gate2 = new TeleportGate( "secondGate");

		Main.increaseTab();
		gate1.setPair(gate2);

		Main.increaseTab();
		gate2.setPair(gate1);

		Main.increaseTab();
		addGate(gate1);

		Main.increaseTab();
		addGate(gate2);

		addAllObject(step);

		step.addObject(gate1);
		step.addObject(gate2);


		Main.decreaseTab();
	}
	
	public void buildRobot() {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " buildRobot()");
		Main.activeSimulation.addStep(step);
		addAllObject(step);

		for (Material material : materials) {
			Main.increaseTab();
			rmMaterial(material);
		}
		Main.increaseTab();
		Robot r = new Robot("R0");
		mySystem.addRobot(r);

		Main.decreaseTab();
	}
	
	public void placeMaterial(Material m) {
		Main.printTabs();
		System.out.println(Main.call + " " + name + " placeMaterial()");
		Main.increaseTab();

		if(location.placeMaterial(m))
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

		Main.increaseTab();
		g.activate();

		Main.increaseTab();
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
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name + " rmMaterial(" + m.getName() + ")");
		Main.activeSimulation.addStep(step);

		Main.decreaseTab();
	}

	@Override
	public void die() {
		Step step = new Step(Main.printTabs() + Main.call++ + " " + name +  " die()");
		Main.activeSimulation.addStep(step);

		Main.decreaseTab();
	}

	@Override
	public void explode() {
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " explode()");
		Main.increaseTab();

		die();

		Main.decreaseTab();;
	}


	@Override
	public void listParameters() {

	}

	@Override
	public String printName() {
		return name;
	}
}
