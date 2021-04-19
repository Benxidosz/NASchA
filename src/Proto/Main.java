package Proto;

import Proto.simulator.Simulator;

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

    }
}
