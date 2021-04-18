package Proto.things.asteroids;

import Proto.Main;
import Proto.entity.Entity;
import Proto.material.Material;
import Proto.things.Thing;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Asteroid extends Thing{
	protected int layer; 			//The number of layers on the Asteriod
	protected boolean nearBySun;	//Stores if the Asteroid is near sun
	protected Material core;		//The core's material

	/**
	 * The constructor of the Asteroid class. Sets it's attributes.
	 * @param name The name of the object
	 */
	public Asteroid(String name) {
		super(name);

		this.layer = layer;
		this.nearBySun = nearBySun;
		this.core = core;
	}

	/**
	 * Calls explode for all entities on it.
	 */
	public void explode() {
		entities.forEach(((e) -> {
			e.explode();
		}));
	}

	/**
	 * The asteroid gets excavated and sets it's core to null.
	 * @return The core Material.
	 */
	@Override
	public Material excavate() {
		Material m = core;
		core = null;
		return m;
	}

	/**
	 * Place a Material in it's core
	 * @param m The material player wants place.
	 */
	@Override
	public boolean placeMaterial(Material m) {
		if(core != null) return false;
		core = m;
		return true;
	}

	/**
	 * Settler drill asteroid layer
	 */
	@Override
	public void drill() {
		layer--;
		if(layer == 0)
			if(nearBySun)
				core.nearSun();
	}

	/**
	 * Simulate the sun eruption on string
	 */
	@Override
	public void applySunEruption() {
		if(layer != 0) return;
		if(core != null) return;
		entities.forEach((e) ->{
			e.die();
		});
	}

	/**
	 * Settler build the base with material.
	 * Its overriden in the children class.
	 * @param m
	 */
	@Override
	public boolean buildBase(Material m) {
		return false;
	}

	/**
	 * Settler set the asteroid core
	 * @param m
	 */
	public void setCore(Material m) {
		core = m;
		if (m != null) {
			m.setMyAsteroid(this);
		}
	}

	/**
	 * Returns if the Asteroid is drillable.
	 * @return
	 */
	public boolean isDrillable(){
		return (layer != 0);
	}
}
