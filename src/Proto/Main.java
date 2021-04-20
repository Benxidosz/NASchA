package Proto;

import Proto.material.Material;
import Proto.material.compare.MaterialCompare;
import Proto.material.materials.Coal;
import Proto.material.materials.Iron;
import Proto.material.materials.Uran;
import Proto.material.materials.WaterIce;
import Proto.simulator.Simulator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.MarshalException;
import java.util.Random;
import java.util.Scanner;

/**
 * The main class of the game.
 */
public class Main {
    static public Scanner scanner = new Scanner(System.in);
    public static Random rng = new Random();
    public static BufferedWriter writer;



    public static void log(String log) {
        System.out.println(log);
        try {
            writer.write(log);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean testMode = false;

    public static boolean isTestMode() {
        return testMode;
    }

    public static void main(String[] args) throws IOException {
        writer = new BufferedWriter(new FileWriter("log.txt"));
        if (args.length > 0) {
            if (args[0].equals("-t")) {
                testMode = true;
                File input = null;
                File output = null;
                Simulator simulator = new Simulator();
                if (args.length > 1) {
                    for (int i = 1; i < args.length - 1; ++i) {
                        if (args[i].equals("-i") && i + 1 < args.length)
                            input = new File(args[++i]);

                        if (args[i].equals("-o") && i + 1 < args.length)
                            output = new File(args[++i]);
                    }

                    if (input != null && output != null)
                        simulator.Read(input, output);
                } else {
                    System.out.println("You are in test mode! You can load file by:\n" +
                            "load -i <inputFile> -o <testOutputFile>");
                    simulator.run();
                }
            }
        }

        if (!testMode) {
            GameManager.init();
            GameManager.getInstance().run();
        }
    }
}
