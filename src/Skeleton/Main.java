package Skeleton;

import Skeleton.entities.children.Settler;
import Skeleton.things.asteroids.Asteroid;

public class Main {
    static public int tabs = 0;
    static public int call = 1;
    static public void printTabs() {
        for (int i = 0; i < tabs; ++i) {
            System.out.print("\t");
        }
    }

    public static void main(String[] args) {
        Asteroid asteroid = new Asteroid("a1");
        for (int i = 0; i < 10; ++i)
            asteroid.etities.add(new Settler("S" + i));

        asteroid.explode();
    }
}
