package Proto.material.materials;

import Proto.material.Material;
import Proto.material.compare.MaterialCompare;

/**
 * It represents a Iron type material and helps to distinguish it from other different materials.
 */
public class Iron extends Material {

	/**
	 * Constructor of the Iron class. Sets the name and the asteroid where the material is.
	 * @param name The name of the object.
	 */
	public Iron (String name) {
		super(name);
	}

	@Override
	public boolean compare(Material o) {
		return MaterialCompare.getInstance().compare(this, o);
	}

	@Override
	public boolean compare(Iron o) {
		return true;
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

	@Override
	public boolean compare(Coal o) {
		return false;
	}
}
