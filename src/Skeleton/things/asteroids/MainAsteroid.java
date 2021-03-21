package Skeleton.things.asteroids;

import Skeleton.Main;
import Skeleton.materials.Material;

public class MainAsteroid extends Asteroid {
	private Material builtIn;

	public MainAsteroid(int layer, boolean nearBySun, Material core, String name) {
		super(layer, nearBySun, core, name);
	}

	@Override
	public void buildBase(Material m) {
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " buildBase() void");

		Main.decreaseTab();
	}
}
