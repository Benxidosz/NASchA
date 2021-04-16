package Proto;

import Proto.controller.controllers.SolarSystem;
import Proto.entity.entities.Robot;
import Proto.entity.entities.Settler;
import Proto.material.materials.Coal;
import Proto.material.materials.Uran;
import Proto.material.materials.Iron;
import Proto.material.materials.WaterIce;
import Proto.material.materials.Silicon;
import Proto.simulator.Simulation;
import Proto.simulator.Simulator;
import Proto.things.asteroids.Asteroid;
import Proto.things.gate.TeleportGate;

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

        simulator.addSimulation(new Simulation("Settler drill uran.", () -> {
            Asteroid a = new Asteroid("A0");
            Asteroid nei1 = new Asteroid("A0N1");
            Asteroid nei2 = new Asteroid("A0N2");
            Settler s = new Settler("S0");
            Robot r = new Robot("R0");
            a.setCore(new Uran("u"));
            s.move(a);
            r.move(a);
            a.addNeighbour(nei1);
            a.addNeighbour(nei2);
            s.drill();
        }));

        simulator.addSimulation(new Simulation("Settler drill water ice.", () -> {
            Asteroid a = new Asteroid("A0");
            Settler s = new Settler("S0");
            a.setCore(new WaterIce("w"));
            s.move(a);
            s.drill();
        }));

        simulator.addSimulation(new Simulation("Settler move to Asteroid.", () -> {
            Asteroid a = new Asteroid("A0");
            Asteroid a2 = new Asteroid("A1");
            Settler s = new Settler("S0");
            s.move(a);
            s.move(a2);
        }));

        simulator.addSimulation(new Simulation("Settler move to active TeleportGate.", () -> {
            TeleportGate g1 = new TeleportGate("G1");
            TeleportGate g2 = new TeleportGate("G2");
            g1.setPair(g2);
            g2.setPair(g1);
            g1.activate();
            Settler s = new Settler("S0");
            s.move(g1);
        }));

        simulator.addSimulation(new Simulation("Settler place uran.", () -> {
            Settler s = new Settler("S0");
            Asteroid a = new Asteroid("A0");
            Uran u = new Uran("u");
            s.move(a);
            s.addMaterial(u);
            s.placeMaterial(u);
        }));

        simulator.addSimulation(new Simulation("Settler place water ice.", () -> {
            Settler s = new Settler("S0");
            Asteroid a = new Asteroid("A0");
            WaterIce w = new WaterIce("w");
            s.move(a);
            s.addMaterial(w);
            s.placeMaterial(w);

        }));

        simulator.addSimulation(new Simulation("Settler place coal.", () -> {
            Settler s = new Settler("S0");
            Asteroid a = new Asteroid("A0");
            Coal c = new Coal("c");
            s.move(a);
            s.addMaterial(c);
            s.placeMaterial(c);
        }));

        simulator.addSimulation(new Simulation("Settler place iron.", () -> {
            Settler s = new Settler("S0");
            Asteroid a = new Asteroid("A0");
            Iron i = new Iron("i");
            s.move(a);
            s.addMaterial(i);
            s.placeMaterial(i);
        }));

        simulator.addSimulation(new Simulation("Settler place silicon.", () -> {
            Settler s = new Settler("S0");
            Asteroid a = new Asteroid("A0");
            Silicon sil = new Silicon("s");
            s.addMaterial(sil);
            s.move(a);
            s.addMaterial(sil);
            s.placeMaterial(sil);
        }));

        simulator.addSimulation(new Simulation("Settler mine uran.", () -> {
            Settler s = new Settler("S0");
            Asteroid a = new Asteroid("A0");
            Uran u = new Uran("u");
            a.setCore(u);
            s.move(a);
            s.mine();
        }));

        simulator.addSimulation(new Simulation("Settler mine water ice.", () -> {
            Settler s = new Settler("S0");
            Asteroid a = new Asteroid("A0");
            WaterIce w = new WaterIce("w");
            a.setCore(w);
            s.move(a);
            s.mine();
        }));

        simulator.addSimulation(new Simulation("Settler mine coal.", () -> {
            Settler s = new Settler("S0");
            Asteroid a = new Asteroid("A0");
            Coal c = new Coal("c");
            a.setCore(c);
            s.move(a);
            s.mine();
        }));

        simulator.addSimulation(new Simulation("Settler mine iron.", () -> {
            Settler s = new Settler("S0");
            Asteroid a = new Asteroid("A0");
            Iron i = new Iron("i");
            a.setCore(i);
            s.move(a);
            s.mine();
        }));

        simulator.addSimulation(new Simulation("Settler mine silicon.", () -> {
            Settler s = new Settler("S0");
            Asteroid a = new Asteroid("A0");
            Silicon sil = new Silicon("s");
            a.setCore(sil);
            s.move(a);
            s.mine();
        }));

        simulator.addSimulation(new Simulation("Settler put down gate.", () -> {
            Settler s = new Settler("S0");
            Asteroid a = new Asteroid("A0");
            TeleportGate g = new TeleportGate("G0");
            TeleportGate pair = new TeleportGate("G0P");
            g.setPair(pair);
            s.move(a);
            s.putGateDown(g);
        }));

        simulator.addSimulation(new Simulation("Settler wait.", () -> {
            Settler s = new Settler("S0");
            s.waitEntity();
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
                e.printStackTrace();
            }
            tabs = 0;
            call = 1;
        }
    }
}
