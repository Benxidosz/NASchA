package Graphics.controller;

import Graphics.App;
import Graphics.Inventory;
import Graphics.Main;
import Graphics.controller.controllers.RobotController;
import Graphics.controller.controllers.SettlerController;
import Graphics.controller.controllers.SolarSystem;
import Graphics.controller.controllers.UfoController;
import Graphics.observable.entity.entities.Settler;
import Graphics.observable.entity.entities.Ufo;
import Graphics.material.MaterialCompare;
import Graphics.material.materials.*;
import Graphics.observable.thing.things.TeleportGate;
import Graphics.simulator.Simulator;
import Graphics.observable.thing.Thing;
import Graphics.observable.thing.things.Asteroid;
import Graphics.observable.thing.things.MainAsteroid;

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
    protected int doneControllers;
    /**
     * Number of turns.
     */
    protected int turnNum;

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
    protected boolean quit;
    /**
     * If it's true current game end.
     */
    protected boolean ended;

    /**
     * Make the quit to true.
     */
    public void makeQuit() {
        quit = true;
    }

    /**
     * The constructor of this class.
     */
    protected GameManager(){
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
        doneControllers++;
        if (doneControllers == 4)
            newTurn();
    }

    public int getTurnNum() {
        return turnNum;
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
     * Generate settlers for the board, then add to SettlerController.
     * @param main The Main asteroid where the settlers start.
     * @param settlerNum The number of settlers.
     */
    protected void generateSettlers(MainAsteroid main, int settlerNum) {
        for(int i = 0; i < settlerNum; i++){
            Settler s = new Settler(main, SettlerController.getSettlerId());
            if (App.isTestMode()) {
                if (i % 3 == 0) {
                    s.addMaterial(new Coal(s.getName() + "c1"));
                    s.addMaterial(new Coal(s.getName() + "c2"));
                    s.addMaterial(new Coal(s.getName() + "c3"));
                    s.addMaterial(new Iron(s.getName() + "i1"));
                    s.addMaterial(new Iron(s.getName() + "i2"));
                    s.addMaterial(new Iron(s.getName() + "i3"));
                    s.addMaterial(new WaterIce(s.getName() + "w1"));
                    s.addMaterial(new WaterIce(s.getName() + "w2"));
                    s.addMaterial(new WaterIce(s.getName() + "w3"));
                    String id = SolarSystem.getTeleportGateId();
                    TeleportGate gate1 = new TeleportGate(s.getName() + id + "a");
                    TeleportGate gate2 = new TeleportGate(s.getName() + id + "b");
                    gate1.setPair(gate2);
                    gate2.setPair(gate1);
                    s.addGate(gate1);
                    s.addGate(gate2);
                } else if (i % 3 == 1) {
                    s.addMaterial(new Uran(s.getName() + "u1"));
                    s.addMaterial(new Uran(s.getName() + "u2"));
                    s.addMaterial(new Uran(s.getName() + "uDanger", 2));
                    s.addMaterial(new Silicon(s.getName() + "s1"));
                    s.addMaterial(new Silicon(s.getName() + "s2"));
                    s.addMaterial(new Silicon(s.getName() + "s3"));
                    String id = SolarSystem.getTeleportGateId();
                    TeleportGate gate1 = new TeleportGate(s.getName() + id + "a");
                    TeleportGate gate2 = new TeleportGate(s.getName() + id + "b");
                    gate1.setPair(gate2);
                    gate2.setPair(gate1);
                    s.addGate(gate1);
                    s.addGate(gate2);
                } else {
                    s.addMaterial(new Coal(s.getName() + "c1"));
                    s.addMaterial(new Iron(s.getName() + "i1"));
                    s.addMaterial(new Uran(s.getName() + "uDanger", 2));
                    s.addMaterial(new Iron(s.getName() + "i2"));
                    s.addMaterial(new Iron(s.getName() + "i3"));
                    s.addMaterial(new WaterIce(s.getName() + "w1"));
                    s.addMaterial(new Uran(s.getName() + "u1"));
                }
            }
            main.addEntity(s);
            SettlerController.getInstance().addSettler(s);
        }
    }

    /**
     * Generate ufos for the board, then add to UfoController.
     * @param asteroids The asteroids of the board.
     * @param ufoNum The number of ufos.
     */
    protected void generateUfo(ArrayList<Asteroid> asteroids, int ufoNum) {
        for(int i = 0; i < ufoNum; i++){
            Thing loc = asteroids.get(Main.rng.nextInt(asteroids.size()));
            Ufo u = new Ufo(loc, UfoController.getUfoId());
            loc.addEntity(u);
            UfoController.getInstance().addUfo(u);
        }
    }

    /**
     * Generate asteroids for the board and add them to a list.
     * @param asteroids The list of asteroids, where they should be added.
     * @param asteroidNum The number of asteroids.
     */
    protected void generateAsteroids(ArrayList<Asteroid> asteroids, int asteroidNum) {
        for(int i = 0; i < asteroidNum; i++){
            Asteroid temp = new Asteroid(SolarSystem.getAsteroidId());
            asteroids.add(temp);
        }
    }

    protected void generateNeis(ArrayList<Asteroid> asteroids) {
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
    }

    /**
     * Asks the user the number of settlers and asteroids and creates the entities.
     */
    public void newGame(){
        turnNum = 0;
        doneControllers = 0;
        quit = false;
        ended = false;

        SettlerController.init(this);
        UfoController.init(this);
        RobotController.init(this);
        SolarSystem.init(this);
        MaterialCompare.init();

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

        MainAsteroid main = null;
        try {
            main = new MainAsteroid("main");
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        asteroids.add(main);

        generateAsteroids(asteroids, asteroidnum);

        asteroids.forEach(a -> SolarSystem.getInstance().addThing(a));

        generateNeis(asteroids);

        generateSettlers(main, settlernum);

        generateUfo(asteroids, ufoNum);

        newTurn();
    }

    /**
     *Notificates the user the win and calls newGame().
     */
    public void win() {
        ended = true;
        Simulator.addMessage("game win\n");
        if (!Main.isTestMode()) {
            System.out.println("Victory!");
            System.out.println("Play again? [Y/N]");
            try {
                String input = Main.scanner.nextLine().toUpperCase();
                if (input.toUpperCase() == "N")
                    makeQuit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Notificates the user the loss and calls newGame().
     */
    public void lose(){
        ended = true;
        Simulator.addMessage("game lose\n");
        if (!Main.isTestMode()) {
            System.out.println("Defeat!");
            System.out.println("Play again? [Y/N]");
            try {
                String input = Main.scanner.nextLine().toUpperCase();
                if (input.toUpperCase() == "N")
                    makeQuit();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
