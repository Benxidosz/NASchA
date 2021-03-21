package Skeleton;

import Skeleton.entities.children.Settler;
import Skeleton.simulator.Simulation;
import Skeleton.simulator.SimulationProcess;
import Skeleton.simulator.Simulator;
import Skeleton.things.asteroids.Asteroid;
import Skeleton.things.asteroids.MainAsteroid;

import java.util.Scanner;

public class Main {
    static private int tabs = 0;
    static public int call = 1;

    static public Scanner scanner = new Scanner(System.in);

    static public String printTabs() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tabs; ++i) {
            builder.append("\t");
        }
        return builder.toString();
    }

    static public void increaseTab() {
        ++tabs;
    }
    static public void decreaseTab() {
        if (tabs > 0)
            --tabs;
    }

    public static Simulation activeSimulation;


    public static void main(String[] args) {
        Simulator simulator = new Simulator();

        simulator.addSimulation(new Simulation("Asteroid Eruption.", () -> {
            Asteroid a = new Asteroid(2, true, null, "A1");
            a.addEntity(new Settler("S0", a));
            a.addEntity(new Settler("S1", a));
            a.addEntity(new Settler("S2", a));
            a.applySunEruption();
        }));

        while (true) {
            simulator.printSimulations();
            int command = scanner.nextInt();
            scanner.nextLine();
            if (command == -1)
                break;
            try {
                simulator.runSimulation(command);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            tabs = 0;
            call = 1;
        }
    }
}
