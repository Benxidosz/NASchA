package Skeleton.simulator;

import Skeleton.Main;

import java.util.ArrayList;

public class Simulation {
	private final String title;
	private final SimulationProcess initialization;
	private final ArrayList<Step> steps;
	private int next;

	public Simulation(String title, SimulationProcess initialization) {
		this.title = title;
		this.initialization = initialization;
		steps = new ArrayList<>();
	}

	public void runProcess() {
		Main.activeSimulation = this;
		initialization.run();
		next = 0;
		while(steps.size() > next) {
			printStep();
			printOps();
		}
		steps.forEach(Step::print);
	}

	public String getTitle() {
		return title;
	}

	public void addStep(Step step) {
		steps.add(step);
	}

	private void printStep() {
		steps.get(next).print();
	}

	private void listObjects() {
		steps.get(next).listObjects();
	}

	private void listObjectParam(int i) throws Exception {
		steps.get(next).listObjectParam(i);
	}

	private void nextStep() {
		next++;
	}

	private void printOps() {
		System.out.println("1. Next.");
		System.out.println("2. List objs.");
		System.out.println("3. List obj params.");
		int command = Main.scanner.nextInt();
		switch(command) {
			case 1: nextStep(); break;
			case 2: listObjects(); break;
			case 3:
				int arg = Main.scanner.nextInt();
				try {
					listObjectParam(arg);

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			default: break;
		}
	}
}
