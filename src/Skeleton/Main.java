package Skeleton;

import Skeleton.entities.children.Settler;

public class Main {
    static public int tabs = 0;
    static public int call = 1;
    static public void printTabs() {
        for (int i = 0; i < tabs; ++i) {
            System.out.print("\t");
        }
    }

    public static void main(String[] args) {
        Settler alma = new Settler();
        alma.mine();

    }
}
