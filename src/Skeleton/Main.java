package Skeleton;

import Skeleton.entities.children.Settler;
//import Skeleton.things.asteroids.Asteroid;
import Skeleton.things.gate.TeleportGate;

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
        TeleportGate tg = new TeleportGate("TeleportGate1");
        tg.addEntity(new Settler("S0"));
        tg.addEntity(new Settler("S1"));
        tg.addEntity(new Settler("S2"));
        tg.applySunEruption();

        tg.activate();
    }
}
