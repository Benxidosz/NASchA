package Proto;

import Proto.controller.Controller;
import Proto.material.Material;

import java.util.ArrayList;
import java.util.HashMap;

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

    public void newGame(){
        System.out.println("How many players do you want? ");
        String input = Main.scanner.nextLine().toUpperCase();
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
        System.out.println("Defeat!");
    }

}
