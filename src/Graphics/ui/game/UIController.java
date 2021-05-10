package Graphics.ui.game;

import Graphics.Main;
import Graphics.controller.GameManager;
import Graphics.controller.controllers.RobotController;
import Graphics.controller.controllers.SettlerController;
import Graphics.controller.controllers.SolarSystem;
import Graphics.controller.controllers.UfoController;
import Graphics.material.MaterialCompare;
import Graphics.observable.entity.entities.Settler;
import Graphics.observable.thing.Thing;
import Graphics.observable.thing.things.Asteroid;
import Graphics.observable.thing.things.MainAsteroid;
import Graphics.ui.game.views.asteroidView.AsteroidViewController;
import Graphics.ui.game.views.boardView.BoardViewController;
import Graphics.ui.game.views.messageBox.GameMessage;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Controller class for the user interface
 */
public class UIController extends GameManager {
    /**
     * The actual UIController's reference.
     */
    private static UIController ref;

    /**
     * Getter for the ref attribute.
     * @return The ref attribute.
     */
    public static UIController getInstance() {
        return ref;
    }

    /**
     * All Sprites, what the program uses.
     */
    private static HashMap<String, Image> sprites;

    /**
     * Give back a sprite, from sprites, which have the given key.
     * @param key The wanted sprite's key.
     * @return The Sprite, from the HashMap.
     */
    public static Image getSprite(String key) {
        return sprites.get(key);
    }

    /**
     * The radius of circle obstacles.
     */
    private static int obstacleRadius = 20;

    /**
     * The active view, which should be presented.
     */
    private View activeView;

    /**
     * The number settlers.
     */
    private final int settlerNum;

    /**
     * The number of Ufos.
     */
    private final int ufoNum;

    /**
     * The number of asteroids.
     */
    private final int asteroidNum;

    /**
     * Getter obstacleRadius field.
     * @return The field.
     */
    public static int getObstacleRadius() {
        return obstacleRadius;
    }

    /**
     * Give the selected thing back.
     * @return The selected thing.
     */
    public Thing getSelectedThing() {
        return selectedThing;
    }

    /**
     * Set the selected thing.
     * @param selectedThing The given value.
     */
    public void setSelectedThing(Thing selectedThing) {
        this.selectedThing = selectedThing;
    }

    /**
     * The selected thing in the board.
     */
    private Thing selectedThing;

    /**
     * The board view of the game.
     */
    private BoardViewController boardView;

    /**
     * The asteroid view of the game.
     */
    private AsteroidViewController asteroidView;

    /**
     * The end game message of the game.
     */
    private GameMessage gameMessage;

    /**
     * The program's stage, what is showed to user.
     */
    private final Stage gameStage;

    /**
     * The initialization method of the UIController.
     * @param settlerNum The number of settlers.
     * @param ufoNum The number of Ufos.
     * @param asteroidNum The number of asteroids.
     */
    public static void init(int settlerNum, int ufoNum, int asteroidNum) {
        ref = new UIController(settlerNum, ufoNum, asteroidNum);
    }

    /**
     * Check if the settler with the given name is free or not, if free,  it didn't do anything so, the it can't do
     * the given command. That's why it shows an Alert window, with a message of: "Settler can't do this.".
     * @param name The settlers name
     */
    public static void checkFree(String name) {
        Settler settler = SettlerController.getInstance().getSettlerByName(name);
        if (settler != null && settler.isActive()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Something went wrong!");
            alert.setHeaderText(null);
            alert.setContentText("Settler can't do this.");

            alert.showAndWait();
        }
    }

    /**
     * The constructor of the UIController, it is private, because only the init method should be called.
     * @param settlerNum The number of settlers.
     * @param ufoNum The number of Ufos.
     * @param asteroidNum The number of asteroids.
     */
    private UIController(int settlerNum, int ufoNum, int asteroidNum) {
        super();

        this.settlerNum = settlerNum;
        this.ufoNum = ufoNum;
        this.asteroidNum = asteroidNum;

        sprites = new HashMap<>();
        sprites.put("asteroid_idle", new Image("sprites/Asteroid.png", false));
        sprites.put("asteroid_selected", new Image("sprites/AsteroidSelected.png", false));
        sprites.put("asteroid_hoover", new Image("sprites/AsteroidHoover.png", false));
        sprites.put("gate_idle", new Image("sprites/TeleportGate.png", false));
        sprites.put("gate_selected", new Image("sprites/TeleportGateSelected.png", false));
        sprites.put("gate_hoover", new Image("sprites/TeleportGateHoover.png", false));
        sprites.put("main_idle", new Image("sprites/MainAsteroid.png", false));
        sprites.put("main_selected", new Image("sprites/MainAsteroidSelected.png", false));
        sprites.put("main_hoover", new Image("sprites/MainAsteroidHoover.png", false));
        sprites.put("board", new Image("sprites/Board.png", false));
        sprites.put("settler", new Image("sprites/Settler.png", false));
        sprites.put("ufo", new Image("sprites/Ufo.png", false));
        sprites.put("robot", new Image("sprites/Robot.png", false));
        sprites.put("gate_pairActive", new Image("sprites/TeleportGatePairActive.png", false));

        gameStage = new Stage();
        gameStage.setResizable(false);
    }

    /**
     * Run a BFS algorithm from the given Asteroid, give back the untouched Things.
     * @param a The starting asteroid.
     * @return The untouched Things.
     */
    private LinkedList<Thing> BFSFromAsteroid(Asteroid a) {
        LinkedList<Thing> unTouched = new LinkedList<>(SolarSystem.getInstance().getThings());

        LinkedList<Thing> underProcess = new LinkedList<>();
        LinkedList<Thing> done = new LinkedList<>();

        underProcess.add(a);
        unTouched.remove(a);

        while (!underProcess.isEmpty()) {
            Thing process = underProcess.pollFirst();

            for (Thing nei : process.getNeighbour()) {
                if (!done.contains(nei)) {
                    underProcess.add(nei);
                    unTouched.remove(nei);
                }
            }

            done.add(process);
        }

        return unTouched;
    }

    /**
     * Make the asteroids field a connected graph.
     * @param asteroids The list of asteroids, which represent the asteroid field.
     */
    private void correctAsteroids(ArrayList<Asteroid> asteroids) {
        for (Asteroid a : asteroids) {
            LinkedList<Thing> unTouched = BFSFromAsteroid(a);
            while (!unTouched.isEmpty()) {
                Thing nei = unTouched.get(Main.rng.nextInt(unTouched.size()));

                a.addNeighbour(nei);
                nei.addNeighbour(a);

                unTouched = BFSFromAsteroid(a);
            }
        }
    }

    /**
     * Generate Neighbours for the asteroids.
     * @param asteroids The asteroids.
     */
    @Override
    protected void generateNeis(ArrayList<Asteroid> asteroids) {
        for(Asteroid a1: asteroids) {
            for (Asteroid a2 : asteroids) {
                if (a1 != a2)
                    if (Main.rng.nextDouble() < 0.03) {
                        a1.addNeighbour(a2);
                        a2.addNeighbour(a1);
                    }
            }
        }

        for(Asteroid a1: asteroids) {
            if (a1.getNeighbour().size() == 0) {
                boolean gotten = false;
                while (!gotten) {
                    Asteroid nei = asteroids.get(Main.rng.nextInt(asteroids.size()));
                    if (nei != a1) {
                        gotten = true;
                        a1.addNeighbour(nei);
                        nei.addNeighbour(a1);
                    }
                }
            }
        }
    }

    /**
     * Start a new Game. (Initialize the backend).
     */
    @Override
    public void newGame() {
        turnNum = 0;
        doneControllers = 0;
        quit = false;
        ended = false;

        SettlerController.init(this);
        UfoController.init(this);
        RobotController.init(this);
        SolarSystem.init(this);
        MaterialCompare.init();

        ArrayList<Asteroid> asteroids = new ArrayList<>();

        MainAsteroid main = null;

        try {
            main = new MainAsteroid("main");
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            gameStage.close();
            return;
        }
        asteroids.add(main);

        //generate asteroids
        generateAsteroids(asteroids, asteroidNum);

        //add asteroids to SolarSystem
        asteroids.forEach(a -> SolarSystem.getInstance().addThing(a));

        //Generate neis
        generateNeis(asteroids);

        correctAsteroids(asteroids);

        //generate settlers and add SettlerController
        generateSettlers(main, settlerNum);

        //generate ufos and add UfoController
        generateUfo(asteroids, ufoNum);

        try {
            boardView = new BoardViewController();
            asteroidView = new AsteroidViewController();
            gameMessage = new GameMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        activeView = boardView;

        activeView.setActive();
        activeView.rePaint();
        newTurn();
    }

    /**
     * Start a new turn of the present game.
     */
    @Override
    public void newTurn() {
        doneControllers = 0;
        turnNum++;

        SolarSystem.getInstance().makeTurn();
        RobotController.getInstance().makeTurn();
        UfoController.getInstance().makeTurn();
    }

    /**
     * Called, when the player win.
     */
    @Override
    public void win() {
        gameMessage.showAndWait("Win");
        String data = gameMessage.getData();
        if ("new".equals(data)) {
            newGame();
        } else if ("exit".equals(data)) {
            gameStage.close();
        }
    }

    /**
     * Called, when the player lose.
     */
    @Override
    public void lose() {
        gameMessage.showAndWait("Lose");
        String data = gameMessage.getData();
        if ("new".equals(data)) {
            newGame();
        } else if ("exit".equals(data)) {
            gameStage.close();
        }
    }

    /**
     * Get the current stage.
     * @return
     */
    public Stage getGameStage() {
        return gameStage;
    }

    /**
     * Switch between views.
     */
    public void switchView() {
        if (activeView == boardView) {
            activeView = asteroidView;
        } else
            activeView = boardView;
        activeView.setActive();
        activeView.refresh();
        activeView.rePaint();
    }
}
