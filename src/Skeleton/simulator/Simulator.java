package Skeleton.simulator;

import java.util.ArrayList;

public class Simulator {
	private final ArrayList<Simulation> simulations;

	public Simulator() {
		simulations = new ArrayList<>();
	}

	public void addSimulation(Simulation s) {
		simulations.add(s);
	}

	public void printSimulations() {
		for (int i = 0; i < simulations.size(); i++) {
			System.out.println(i + ": " + simulations.get(i).getTitle() + ".");
		}
		System.out.println("-1: quit.");
	}

	public void runSimulation(int i) throws Exception {
		if (simulations.size() > i) {
			Simulation simulation = simulations.get(i);
			simulation.runProcess();
		} else
			throw new Exception("Wrong command.");
	}
}
