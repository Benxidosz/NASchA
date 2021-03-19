package Skeleton;

import Skeleton.entities.children.Settler;
import Skeleton.things.asteroids.Asteroid;
import Skeleton.things.asteroids.MainAsteroid;

public class Main {
    static private int tabs = 0;
    static public int call = 1;

    static public void printTabs() {
        for (int i = 0; i < tabs; ++i) {
            System.out.print("\t");
        }
    }

    static public void increaseTab() {
        ++tabs;
    }
    static public void decreaseTab() {
        if (tabs > 0)
            --tabs;
    }

    public static void main(String[] args) {
        MainAsteroid ma = new MainAsteroid(2, false, null, "MainAsteroid");
        ma.addEntity(new Settler("S0"));
        ma.addEntity(new Settler("S1"));
        ma.addEntity(new Settler("S2"));
        ma.addEntity(new Settler("S3"));
        ma.applySunEruption();
    }
}
