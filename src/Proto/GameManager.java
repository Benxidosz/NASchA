package Proto;

import Proto.controller.Controller;
import Proto.entity.entities.Settler;
import Proto.entity.entities.Ufo;
import Proto.material.Material;
import Proto.things.asteroids.Asteroid;
import Proto.things.asteroids.MainAsteroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameManager {
    private ArrayList<Controller> controllers = new ArrayList<>();      //The controllers of the game
    public HashMap<String, Material> recipes = new HashMap<>();         //The recipes for building
    private int doneControllers;                                        //The number of Controller which are done in this turn
    private int turnNum;                                                //Number of turns.

    /**
     * The constructor of this class.
     */
    public GameManager(){
        doneControllers = 0;
        turnNum = 0;
    }

    /**
     * Checks if the controllers are done.
     */
    public void jobsDone(){
        if(doneControllers == controllers.size())
            newTurn();
    }

    /**
     * Increases the newTurn attribute.
     */
    public void newTurn(){
        doneControllers = 0;
        turnNum++;
    }

    /**
     *
     */
    public void newGame(){
        System.out.println("How many settlers do you want? ");
        String input = Main.scanner.nextLine().toUpperCase();
        int settlernum = Integer.parseInt(input);
        System.out.println("How many asteroids do you want? ");
        input = Main.scanner.nextLine().toUpperCase();
        int asteroidnum = Integer.parseInt(input);
        ArrayList<Asteroid> asteroids = new ArrayList<>();

        for(int i = 0; i < asteroidnum; i++){
            Asteroid temp = new Asteroid("a" + i);
            asteroids.add(temp);
        }

        boolean withzero = false;
        boolean loop = true;

        while(loop) {
            withzero = false;
            for(Asteroid a: asteroids) {
                Random rand = new Random();
                a.addNeighbour(asteroids.get(rand.nextInt(asteroids.size())));
                if (a.getNeighbour().size() == 0)
                    withzero = true;
            }
            if(!withzero)
                loop = false;
        }

        MainAsteroid main = new MainAsteroid("main");

        Random rand = new Random();

        for(int i = 0; i < rand.nextInt(5); i++)
            main.addNeighbour(asteroids.get(i));

        for(int i = 0; i < settlernum; i++){
            Settler s = new Settler("s" + i);
            s.move(main);
        }

        for(int i = 0; i < rand.nextInt(5); i++){
            Ufo u = new Ufo("u" + i);
            Random ran1 = new Random();
            u.move(asteroids.get(ran1.nextInt(asteroids.size())));
        }

        newTurn();
    }

    /**
     *Notificates the user the win and calls newGame().
     */
    public void win(){
        System.out.println("Victory!");
    }

    /**
     * Notificates the user the loss and calls newGame().
     */
    public void lose(){
        turnNum = 0;
        doneControllers = 0;
        System.out.println("Defeat!");
    }

}
