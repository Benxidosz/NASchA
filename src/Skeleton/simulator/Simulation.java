package Skeleton.simulator;

import java.util.function.Function;

public class Simulation {
	private final String title;
	private final SimulationProcess initialization;
	private Function nextFunc;
	private Object arg;

	public Simulation(String title, SimulationProcess initialization) {
		this.title = title;
		this.initialization = initialization;
	}

	public void runProcess() {
		initialization.run(this);
		while (nextFunc != null) {
			nextFunc.apply(arg);
		}
	}

	public String getTitle() {
		return title;
	}

	public void setNext(Function next, Object arg) {
		nextFunc = next;
		this.arg = arg;
	}
}
