package Proto.material.materials;

import Proto.material.Material;
import Proto.material.compare.MaterialCompare;

/**
 * It represents a Silicon type material and helps to distinguish it from other different materials.
 */
public class Silicon extends Material {

	/**
	 * Constructor of the Silicon class. Sets the name and the asteroid where the material is.
	 * @param name The name of the object.
	 */
	public Silicon (String name) {
		super(name);
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
	 * @return false
	 */
	@Override
	public boolean compare(WaterIce o) {
		return false;
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
	 * @return true
	 */
	@Override
	public boolean compare(Silicon o) {
		return true;
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
