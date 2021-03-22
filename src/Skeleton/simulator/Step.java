package Skeleton.simulator;

import java.util.ArrayList;

public class Step {
	private ArrayList<SimulationObject> objects;
	private String out;

	public Step(String out) {
		objects = new ArrayList<>();
		this.out = out;
	}

	public void print() {
		System.out.println(out);
	}

	public void addObject(SimulationObject o) {
		objects.add(o);
	}

	public void listObjects() {
		System.out.println("--------");
		System.out.println("Objects:");
		for (int i = 0; i < objects.size(); ++i) {
			System.out.println(i + ": " + objects.get(i).printName());
		}
		System.out.println("--------");
	}

	public void listObjectParam(int i) throws Exception {
		if (i < objects.size())
			objects.get(i).listParameters();
		else
			throw new Exception("Wrong command!");
	}
}
