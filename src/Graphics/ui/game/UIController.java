package Graphics.ui.game;

import Graphics.Main;
import Graphics.controller.GameManager;
import Graphics.controller.controllers.RobotController;
import Graphics.controller.controllers.SettlerController;
import Graphics.controller.controllers.SolarSystem;
import Graphics.controller.controllers.UfoController;
import Graphics.material.MaterialCompare;
import Graphics.observable.thing.Thing;
import Graphics.observable.thing.Asteroid;
import Graphics.observable.thing.MainAsteroid;
import Graphics.ui.game.views.asteroidView.AsteroidViewController;
import Graphics.ui.game.views.boardView.BoardViewController;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class UIController extends GameManager {
    private static UIController ref;
    public static UIController getInstance() {
        return ref;
    }

    private View activeView;

    private final int settlerNum;
    private final int ufoNum;
    private final int asteroidNum;

    public Thing getSelectedThing() {
        return selectedThing;
    }

    public void setSelectedThing(Thing selectedThing) {
        this.selectedThing = selectedThing;
    }

    private Thing selectedThing;

    private BoardViewController boardView;
    private AsteroidViewController asteroidView;
    private boolean boardActive;

    private final Stage gameStage;

    public static void init() {
        throw new UnsupportedOperationException();
    }

    public static void init(int settlerNum, int ufoNum, int asteroidNum) {
        ref = new UIController(settlerNum, ufoNum, asteroidNum);
    }

    private UIController(int settlerNum, int ufoNum, int asteroidNum) {
        super();

        this.settlerNum = settlerNum;
        this.ufoNum = ufoNum;
        this.asteroidNum = asteroidNum;

        gameStage = new Stage();
        gameStage.setResizable(false);
    }

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
        } catch (IOException e) {
            e.printStackTrace();
        }

        activeView = boardView;
        boardActive = true;

        activeView.setActive();
        activeView.rePaint();
        newTurn();
    }

    @Override
    public void newTurn() {
        doneControllers = 0;
        turnNum++;

        SolarSystem.getInstance().makeTurn();
        RobotController.getInstance().makeTurn();
        UfoController.getInstance().makeTurn();
    }

    public Stage getGameStage() {
        return gameStage;
    }

    public void switchView() {
        if (boardActive) {
            activeView = asteroidView;
        } else
            activeView = boardView;

        boardActive = !boardActive;
        activeView.setActive();
        activeView.refresh();
        activeView.rePaint();
    }
}
