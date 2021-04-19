package Proto;

import Proto.controller.controllers.RobotController;
import Proto.controller.controllers.SettlerController;
import Proto.controller.controllers.SolarSystem;
import Proto.controller.controllers.UfoController;
import Proto.entity.entities.Settler;
import Proto.entity.entities.Ufo;
import Proto.material.materials.*;
import Proto.things.Thing;
import Proto.things.asteroids.Asteroid;
import Proto.things.asteroids.MainAsteroid;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controls the operation of the game
 */
public class GameManager {
    /**
     * The recipes for building
     */
    public HashMap<String, Inventory> recipes = new HashMap<>();
    /**
     * The number of Controller which are done in this turn
     */
    private int doneControllers;
    /**
     * Number of turns.
     */
    private int turnNum;

    /**
     * A reference for the class.
     */
    private static GameManager ref;

    /**
     * Returns the reference.
     * @return the reference
     */
    public static GameManager getInstance() {
        return ref;
    }

    /**
     * Sets the reference.
     */
    public static void init() {
        ref = new GameManager();
    }

    /**
     * If it's true, the program quits.
     */
    private boolean quit;
    /**
     * If it's true current game end.
     */
    private boolean ended;

    public void makeQuit() {
        quit = true;
    }

    /**
     * The constructor of this class.
     */
    private GameManager(){
        doneControllers = 0;
        turnNum = 0;
        ref = this;
        quit = false;
        ended = false;

        //recipes
        Inventory base = new Inventory();
        for (int i = 0; i < 3; ++i) {
            base.addMaterial(new Uran("baseUran" + i));
            base.addMaterial(new Coal("baseCoal" + i));
            base.addMaterial(new Iron("baseIron" + i));
            base.addMaterial(new Silicon("baseSilicon" + i));
            base.addMaterial(new WaterIce("baseWaterIce" + i));
        }
        recipes.put("Base", base);

        Inventory robot = new Inventory();
        robot.addMaterial(new Uran("robotUran"));
        robot.addMaterial(new Coal("robotCoal"));
        robot.addMaterial(new Iron("robotIron"));
        recipes.put("Robot", robot);

        Inventory tg = new Inventory();
        tg.addMaterial(new Iron("tgIron1"));
        tg.addMaterial(new Iron("tgIron2"));
        tg.addMaterial(new Uran("tgUran"));
        tg.addMaterial(new WaterIce("tgIce"));
        recipes.put("TeleportGate", tg);
    }

    /**
     * Checks if the controllers are done.
     */
    public void jobsDone(){
        if(doneControllers == 4)
            newTurn();
    }

    /**
     * Increases the newTurn attribute.
     */
    public void newTurn(){
        while (!quit && !ended) {
            doneControllers = 0;
            turnNum++;

            System.out.println("Turn: " + turnNum);

            SolarSystem.getInstance().makeTurn();
            RobotController.getInstance().makeTurn();
            UfoController.getInstance().makeTurn();
            SettlerController.getInstance().makeTurn();
        }
    }

    /**
     * Asks the user the number of settlers and asteroids and creates the entities.
     */
    public void newGame(){
        turnNum = 0;
        doneControllers = 0;
        quit = false;
        ended = false;

        SettlerController.init();
        UfoController.init();
        RobotController.init();
        SolarSystem.init();

        System.out.println("How many settlers do you want?");
        String input = Main.scanner.nextLine().toUpperCase();
        int settlernum = Integer.parseInt(input);

        System.out.println("How many asteroids do you want?");
        input = Main.scanner.nextLine().toUpperCase();
        int asteroidnum = Integer.parseInt(input);

        System.out.println("How many ufos do you want?");
        input = Main.scanner.nextLine().toUpperCase();
        int ufoNum = Integer.parseInt(input);

        ArrayList<Asteroid> asteroids = new ArrayList<>();

        MainAsteroid main = new MainAsteroid("main");
        asteroids.add(main);

        //generate asteroids
        for(int i = 0; i < asteroidnum; i++){
            Asteroid temp = new Asteroid(SolarSystem.getAsteroidId());
            asteroids.add(temp);
        }

        //Generate neis
        for(Asteroid a1: asteroids) {
            for (Asteroid a2 : asteroids) {
                if (a1 != a2)
                    if (Main.rng.nextBoolean()) {
                        a1.addNeighbour(a2);
                        a2.addNeighbour(a1);
                    }
            }

            if (a1.getNeighbour().size() == 0) {
                a1.addNeighbour(asteroids.get(Main.rng.nextInt(asteroids.size())));
            }
        }

        //generate settlers and add SettlerController
        for(int i = 0; i < settlernum; i++){
            Settler s = new Settler(main, SettlerController.getSettlerId());
            SettlerController.getInstance().addSettler(s);
        }

        //generate ufos and add UfoController
        for(int i = 0; i < ufoNum; i++){
            Thing loc = asteroids.get(Main.rng.nextInt(asteroids.size()));
            Ufo u = new Ufo(loc, UfoController.getUfoId());
            UfoController.getInstance().addUfo(u);
        }

        //add asteroids to SolarSystem
        asteroids.forEach(a -> SolarSystem.getInstance().addThing(a));

        newTurn();
    }

    /**
     *Notificates the user the win and calls newGame().
     */
    public void win() {
        ended = true;
        System.out.println("Victory!");
        System.out.println("Play again? [Y/N]");
        try{
            String input = Main.scanner.nextLine().toUpperCase();
            if(input == "Y")
                newGame();
            if(input == "N")
                makeQuit();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Notificates the user the loss and calls newGame().
     */
    public void lose(){
        ended = true;
        System.out.println("Defeat!");
        System.out.println("Play again? [Y/N]");
        try{
            String input = Main.scanner.nextLine().toUpperCase();
            if(input == "Y")
                newGame();
            if(input == "N")
                makeQuit();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Run the game until we quit.
     */
    public void run() {
        while (!quit) {
            newGame();
        }
    }
}
