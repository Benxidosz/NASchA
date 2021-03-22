package Skeleton.simulator;

import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Store all the simulations and it can run them.
 */
public class Simulator {
	/**
	 * Stored simulations.
	 */
	private final ArrayList<Simulation> simulations;

	public Simulator() {
		simulations = new ArrayList<>();
	}

	/**
	 * Add a simulation to simulator.
	 * @param s The simulation, which will added.
	 */
	public void addSimulation(Simulation s) {
		simulations.add(s);
	}

	/**
	 * Print all stored simulation.
	 */
	public void printSimulations() {
		System.out.println("--------");
		for (int i = 0; i < simulations.size(); i++) {
			System.out.println(i + ": " + simulations.get(i).getTitle());
		}
		System.out.println("-1: quit.");
		System.out.println("--------");
	}

	/**
	 * Run a given (by index) simulation.
	 * @param i The given index.
	 * @throws InputMismatchException Throwed when the user give a wrong index.
	 */
	public void runSimulation(int i) throws InputMismatchException {
		if (simulations.size() > i) {
			Simulation simulation = simulations.get(i);
			simulation.runProcess();
		} else
			throw new InputMismatchException("Wrong command.");
	}
}
