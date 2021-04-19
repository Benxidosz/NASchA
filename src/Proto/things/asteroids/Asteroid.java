package Proto.things.asteroids;

import Proto.Main;
import Proto.controller.controllers.SolarSystem;
import Proto.entity.Entity;
import Proto.material.Material;
import Proto.material.materials.Coal;
import Proto.material.materials.Iron;
import Proto.material.materials.Silicon;
import Proto.material.materials.Uran;
import Proto.material.materials.WaterIce;
import Proto.things.Thing;
import Proto.simulator.*;

import java.util.Random;

/**
 *  Represents the Asteroids in the Solar System.
 *  Stores it's layer, core and if it's near by sun.
 */
public class Asteroid extends Thing {
	/**
	 * The number of layers on the Asteriod
	 */
	protected int layer;
	/**
	 * Stores if the Asteroid is near sun
	 */
	protected boolean nearBySun;
	/**
	 * The core's material
	 */
	protected Material core;

	/**
	 * The constructor of the Asteroid class. Sets it's attributes.
	 * @param name The name of the object
	 */
	public Asteroid(String name) {
		super(name);

		this.layer = Main.rng.nextInt(6) + 2;

		if(Main.rng.nextBoolean())
			this.nearBySun = true;
		else
			this.nearBySun = false;

		Material core;
		int b = Main.rng.nextInt(6);
		switch(b){
			case 0:
				core = new Coal(SolarSystem.getCoalId());
				break;
			case 1:
				core = new Iron(SolarSystem.getIronId());
				break;
			case 2:
				core = new Silicon(SolarSystem.getSiliconId());
				break;
			case 3:
				core = new Uran(SolarSystem.getUranId());
				break;
			case 4:
				core = new WaterIce(SolarSystem.getWaterIceId());
				break;
			default:
				core = null;
		}

		this.core = core;
	}

	public Asteroid(String name, int layer, Material core, boolean nearBySun) {
		super(name);
		this.layer = layer;
		this.core = core;
		this.nearBySun = nearBySun;
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
		if (layer == 0 && core == null)
			return;


		for (var iter = entities.iterator(); iter.hasNext();) {
			Entity tmp = iter.next();
			tmp.die();
			iter.remove();
		}
	}

	/**
	 * Settler build the base with material.
	 * Its overriden in the children class.
	 * @param m The material built base with
	 */
	@Override
	public boolean buildBase(Material m) {
		return false;
	}

	/**
	 * Settler set the asteroid core
	 * @param m The core it sets
	 */
	public void setCore(Material m) {
		core = m;
		if (m != null) {
			m.setMyAsteroid(this);
		}
	}

	/**
	 * Returns if the Asteroid is drillable.
	 * @return true if the layer is 0
	 */
	@Override
	public boolean isDrillable(){
		return (layer != 0);
	}

	/**
	 * Returns the material contained by the asteroid.
	 * @return The core material
	 */
	@Override
	public Material getCore() {
		return core;
	}

	/**
	 * Returns the number of layers on the asteroid.
	 * @return the number of layers.
	 */
	@Override
	public int getLayer() {
		return layer;
	}

	/**
	 * Lists the attributes of the object.
	 * @return the attributes as a string.
	 */
	@Override
	public String List() {
		StringBuilder result = new StringBuilder("+------------------+\n");
		result.append("name: " + getName() + "\n");
		result.append("neighbours: ");
		if (neighbour.size() == 0)
			result.append("null");
		else
			for (Thing nei : neighbour)
				result.append(nei.getName() + " ");

		result.append("\nentities: ");
		if (entities.size() == 0)
			result.append("null");
		else
			for (Entity ent : entities)
				result.append(ent.getName() + " ");

		result.append("\nlayer number: " + (layer == 0 ? "null" : layer)+ "\n");
		result.append("core: " + (core == null ? "null" : core.getName()) + "\n");
		result.append("nearsun: " + (nearBySun ? "true" : "false") + "\n");
		return result.toString();
	}
}
