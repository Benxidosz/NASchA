package Proto.simulator;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Step {
	private ArrayList<SimulationObject> objects;
	private String out;

	/**
	 * Store the current called methode
	 * @param out
	 */
	public Step(String out) {
		objects = new ArrayList<>();
		this.out = out;
	}

	/**
	 * print out the current methode
	 */
	public void print() {
		System.out.println(out);
	}

	/**
	 * add objects to the objects list
	 * @param o
	 */
	public void addObject(SimulationObject o) {
		objects.add(o);
	}

	/**
	 * list the objects
	 */
	public void listObjects() {
		System.out.println("--------");
		System.out.println("Objects:");
		for (int i = 0; i < objects.size(); ++i) {
			if (objects.get(i) != null)
				System.out.println(i + ": " + objects.get(i).printName());
		}
		System.out.println("--------");
	}

	/**
	 * list the objects parameters
	 * @param i
	 * @throws Exception
	 */
	public void listObjectParam(int i) throws Exception {
		if (i < objects.size()) {
			if (objects.get(i) != null)
				objects.get(i).listParameters();
		} else
			throw new InputMismatchException("Wrong command!");
	}
}
