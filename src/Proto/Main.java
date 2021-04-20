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

    private static boolean testMode = false;

    public static boolean isTestMode() {
        return testMode;
    }

    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            if (args[0].equals("-t")) {
                testMode = true;
                Simulator simulator = new Simulator();
                if (args.length > 1) {
                    File input = null;
                    File output = null;
                    File expected = null;
                    boolean testAll = false;
                    for (int i = 0; i < args.length; ++i) {
                        if (args[i].equals("-i") && i + 1 < args.length)
                            input = new File(args[++i]);

                        if (args[i].equals("-o") && i + 1 < args.length)
                            output = new File(args[++i]);
                        if (args[i].equals("-e") && i + 1 < args.length)
                            expected = new File(args[++i]);
                        if (args[i].equals("-a"))
                            testAll = true;
                    }

                    if (input != null && output != null && expected != null)
                        simulator.Read(input, output, expected);

                    if (testAll) {
                        File outDir = new File("outTest");
                        int passed = 0;
                        outDir.mkdir();
                        for (int i = 0; i < 21; ++i) {
                            simulator = new Simulator();
                            String fileBase = "test" + (i + 1);
                            System.out.println(fileBase + ": ");
                            input = new File("tests", fileBase + "_input.txt");
                            expected = new File("tests", fileBase + "_output.txt");
                            output = new File(outDir, (fileBase + "_out.txt"));

                            if (simulator.Read(input, output, expected))
                                ++passed;
                            System.out.println("-------------------------------");
                        }

                        System.out.println("All test Done. " + passed + " of 21 passed.");
                    }
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
