package Graphics.material.materials;

import Graphics.material.Material;
import Graphics.material.compare.MaterialCompare;

/**
 * It represents the WaterIce type material and helps to distinguish it from different materials.
 * If the material gets close to the sun, it will evaporate.
 */
public class WaterIce extends Material{

	/**
	 * Constructor of the WaterIce class. Sets the name and the asteroid where the material is.
	 * @param name The name of the object.
	 */
	public WaterIce(String name){
		super(name);
	}

	/**
	 * Evaporates (remove from the inventory) the given object.
	 */
	@Override
	public void nearSun() {
		myAsteroid.setCore(null);
	}

	/**
	 * An override method which is compares the parameter to the type
	 * of this class' material.
	 * @param o a material
	 * @return if it's the same type, it returns true
	 */
	@Override
	public boolean compare(Material o) {
		return MaterialCompare.getInstance().compare(this, o);
	}

	/**
	 * An override method which is compares the parameter to the type
	 * of this class' material.
	 * @param o an iron
	 * @return false
	 */
	@Override
	public boolean compare(Iron o) {
		return false;
	}

	/**
	 * An override method which is compares the parameter to the type
	 * of this class' material.
	 * @param o a waterice
	 * @return true
	 */
	@Override
	public boolean compare(WaterIce o) {
		return true;
	}

	/**
	 * An override method which is compares the parameter to the type
	 * of this class' material.
	 * @param o an uran
	 * @return false
	 */
	@Override
	public boolean compare(Uran o) {
		return false;
	}

	/**
	 * An override method which is compares the parameter to the type
	 * of this class' material.
	 * @param o a silicon
	 * @return false
	 */
	@Override
	public boolean compare(Silicon o) {
		return false;
	}

	/**
	 * An override method which is compares the parameter to the type
	 * of this class' material.
	 * @param o a coal
	 * @return false
	 */
	@Override
	public boolean compare(Coal o) {
		return false;
	}
}
