package Proto.material.materials;

import Proto.material.Material;
import Proto.material.compare.MaterialCompare;

/**
 * It represents a Coal type material and helps to distinguish it from other different materials.
 */
public class Coal extends Material {

	/**
	 * Constructor of the Coal class. Sets the name and the asteroid where the material is.
	 * @param name The name of the object.
	 */
	public Coal (String name) {
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
	 * @param m a coal
	 * @return true
	 */
	@Override
	public boolean compare(Coal m) {
		return true;
	}

	@Override
	public boolean compare(Iron o) {
		return false;
	}

	@Override
	public boolean compare(WaterIce o) {
		return false;
	}

	@Override
	public boolean compare(Uran o) {
		return false;
	}

	@Override
	public boolean compare(Silicon o) {
		return false;
	}
}
