package Skeleton.simulator;

import Skeleton.Main;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Simulation {
	/**
	 * The title what will be printed.
	 */
	private final String title;
	/**
	 * A process what will initialize and run the simulation.
	 */
	private final SimulationProcess initialization;
	/**
	 * Stored steps.
	 */
	private final ArrayList<Step> steps;
	/**
	 * The next index of step.
	 */
	private int next;

	public Simulation(String title, SimulationProcess initialization) {
		this.title = title;
		this.initialization = initialization;
		steps = new ArrayList<>();
	}

	/**
	 * Run the simulation.
	 */
	public void runProcess() {
		Main.activeSimulation = this;
		initialization.run();
		next = 0;
		while(steps.size() > next) {
			printStep();
			printOps();
		}
		steps.forEach(Step::print);
		Main.scanner.nextLine();
		Main.scanner.nextLine();
	}

	public String getTitle() {
		return title;
	}

	public void addStep(Step step) {
		steps.add(step);
	}

	/**
	 * Print the next step.
	 */
	private void printStep() {
		steps.get(next).print();
	}

	/**
	 * List the next step's objects.
	 */
	private void listObjects() {
		steps.get(next).listObjects();
	}

	/**
	 * List one of the step's object.
	 * @param i
	 */
	private void listObjectParam(int i) throws Exception {
		steps.get(next).listObjectParam(i);
	}

	/**
	 * Increase the next.
	 */
	private void nextStep() {
		next++;
	}

	/**
	 * Print the user's options for steps.
	 */
	private void printOps() {
		System.out.println("--------");
		System.out.println("1. Next.");
		System.out.println("2. List objs.");
		System.out.println("3. List obj params.");
		System.out.println("4. Skip.");
		System.out.println("--------");
		int command = Main.scanner.nextInt();
		switch(command) {
			case 1: nextStep(); break;
			case 2: listObjects(); break;
			case 3:
				int arg = Main.scanner.nextInt();
				try {
					listObjectParam(arg);

				} catch (InputMismatchException e) {
					System.err.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 4:
				next = steps.size();
				break;
			default: break;
		}
	}
}
