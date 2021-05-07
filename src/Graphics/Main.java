package Graphics;

import Graphics.simulator.Simulator;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * The main class of the game.
 */
public class Main {

    static public Scanner scanner = new Scanner(System.in);

    /**
     * Random variable for the random activities.
     */
    public static Random rng = new Random();

    /**
     * It's true if the program is in test mode.
     */
    private static boolean testMode = false;

    /**
     * Returns the value of the testMode variable.
     * @return the boolean.
     */
    public static boolean isTestMode() {
        return testMode;
    }

    /**
     * The main function
     * @param args the launching mode
     * @throws IOException
     */
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
                        File inDir = new File("tests");
                        if (!inDir.isDirectory()) {
                            inDir.delete();
                            inDir.mkdir();
                        }
                        int passed = 0;
                        int testNum = inDir.listFiles().length / 2;
                        outDir.mkdir();
                        for (int i = 0; i < testNum; ++i) {
                            simulator = new Simulator();
                            String fileBase = "test" + (i + 1);
                            System.out.println(fileBase + ": ");
                            input = new File(inDir, fileBase + "_input.txt");
                            expected = new File(inDir, fileBase + "_output.txt");
                            output = new File(outDir, (fileBase + "_out.txt"));

                            if (simulator.Read(input, output, expected))
                                ++passed;
                            System.out.println("-------------------------------");
                        }

                        System.out.println("All test Done. " + passed + " of " + testNum + " passed.");
                    }
                } else  {
                    System.out.println("You are in test mode! You can load file by:\n" +
                            "load -i <inputFile> -o <testOutputFile> -e <expected>");
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
