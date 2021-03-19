package Skeleton.things.asteroids;

import Skeleton.Main;
import Skeleton.entities.Entity;
import Skeleton.materials.Material;
import Skeleton.things.Thing;

import java.util.ArrayList;

public class Asteroid extends Thing {
	protected int layer;
	protected boolean nearBySun;
	protected Material core;

	public Asteroid(int layer, boolean nearBySun, Material core, String name) {
		super(name);
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " created.");

		this.layer = layer;
		this.nearBySun = nearBySun;
		this.core = core;

		Main.decreaseTab();
	}

	public void explode() {
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " explode() void.");

		entities.forEach((e) -> {
			Main.increaseTab();
			e.explode();
		});

		Main.decreaseTab();
	}
	
	public Material excavate() {
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " explode() " + core + ".");

		Main.decreaseTab();
		return core;
	}
	
	public void placeMaterial(Material m) {
		Main.printTabs();
		System.out.print(Main.call++ + " " + name + " placeMaterial() void ");

		if (core == null && layer == 0) {
			core = m;
			System.out.println("core set.");
		} else
			System.out.println("core not set.");

		Main.decreaseTab();
	}
	
	public void drill() {
		Main.printTabs();
		System.out.print(Main.call++ + " " + name + " drill() void ");

		if (layer == 0) {
			System.out.println("layer not drilled.");
		} else {
			--layer;
			System.out.println("layer drilled");
		}

		Main.decreaseTab();
	}
	
	public void applySunEruption() {
		Main.printTabs();
		System.out.print(Main.call++ + " " + name + " applySunEruption() void ");

		if (layer == 0 && core == null) {
			System.out.println("eruption not applied (Asteroid is safe).");
		} else {
			System.out.println("eruption applied (Asteroid is not safe).");
			entities.forEach((e) -> {
				Main.increaseTab();
				e.die();
			});
		}

		Main.decreaseTab();
	}
	
	public void buildBase(Material m) {
		Main.printTabs();
		System.out.println(Main.call++ + " " + name + " buildBase() void (it does nothing)");

		Main.decreaseTab();
	}
}
