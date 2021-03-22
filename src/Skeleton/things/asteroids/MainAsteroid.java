package Skeleton.things.asteroids;

import Skeleton.Main;
import Skeleton.materials.Material;

public class MainAsteroid extends Asteroid {
	private Material builtIn;

	public MainAsteroid(String name) {
		super(name);
	}

	@Override
	protected void addAllObject(Step step) {
		super.addAllObject(step);

	}

	@Override
	public void buildBase(Material m) {
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " buildBase() void");

		Main.decreaseTab();
	}
}
