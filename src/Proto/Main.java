package Proto;

import Proto.simulator.Simulator;

import java.util.Random;
import java.util.Scanner;

public class Main {
    static public Scanner scanner = new Scanner(System.in);
    public static Random rng = new Random();

    public static void main(String[] args) {
        boolean testMode = false;
        if (args.length > 0) {
            if (args[0].equals("-t")) {
                testMode = true;
                Simulator simulator = new Simulator();
                simulator.run();
            }
        }

        if (!testMode) {
            GameManager.init();
            GameManager.getInstance().run();
        }
    }
}
