package Skeleton;

import Skeleton.controllers.SolarSystem;
import Skeleton.entities.children.Settler;
import Skeleton.materials.children.Coal;
import Skeleton.materials.children.Uran;
import Skeleton.materials.children.Iron;
import Skeleton.materials.children.WaterIce;
import Skeleton.materials.children.Silicon;
import Skeleton.simulator.Simulation;
import Skeleton.simulator.SimulationProcess;
import Skeleton.simulator.Simulator;
import Skeleton.things.asteroids.Asteroid;
import Skeleton.things.asteroids.MainAsteroid;

import java.util.Scanner;

public class Main {
    static private int tabs = 0;
    static public int call = 1;

    static public SolarSystem system = new SolarSystem("MainSystem");

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
            Asteroid a = new Asteroid("A1");
            a.addEntity(new Settler("S0"));
            a.addEntity(new Settler("S1"));
            a.addEntity(new Settler("S2"));
            a.applySunEruption();
        }));

        simulator.addSimulation(new Simulation("Build robot, all condition set.", () -> {
            Settler s = new Settler("S0");
            s.addMaterial(new Uran( "u"));
            s.addMaterial(new Coal("c"));
            s.addMaterial(new Iron("i"));
            s.buildRobot();
        }));

        simulator.addSimulation(new Simulation("Build gate, all condition set.", () -> {
            Settler s = new Settler("S0");
            s.addMaterial(new Uran("u"));
            s.addMaterial(new WaterIce("w"));
            s.addMaterial(new Iron("i1"));
            s.addMaterial(new Iron("i2"));
            s.buildGate();
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
