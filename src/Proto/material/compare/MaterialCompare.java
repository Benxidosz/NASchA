package Proto.material.compare;

import Proto.material.Material;
import Proto.material.materials.*;

/**
 * It compares the materials.
 */
public class MaterialCompare {
	/**
	 * A reference for the class.
	 */
	private static MaterialCompare ref;

	/**
	 * Sets the reference.
	 */
	public static void init() {
		ref = new MaterialCompare();
	}

	/**
	 * The constructor of the class
	 */
	private MaterialCompare() { }

	/**
	 * Returns the reference.
	 * @return the reference.
	 */
	public static MaterialCompare getInstance() {
		return ref;
	}

	/**
	 * Compare two material
	 * @param m a Material
	 * @param o another Material
	 * @return if the two material have the same type, it returns true.
	 */
	public boolean compare(Material m, Material o) {
		return m.compare(o);
	}

	/**
	 * Compare a coal and a material
	 * @param m a coal
	 * @param o a material
	 * @return if the two material are both coals, it returns true.
	 */
	public boolean compare(Coal m, Material o) { return o.compare(m); }

	/**
	 * Compare an iron and a material
	 * @param m an iron
	 * @param o a material
	 * @return if the two material are both irons, it returns true.
	 */
	public boolean compare(Iron m, Material o) {
		return o.compare(m);
	}

	/**
	 * Compare a silicon and a material
	 * @param m a silicon
	 * @param o a material
	 * @return if the two material are both silicons, it returns true.
	 */
	public boolean compare(Silicon m, Material o) {
		return o.compare(m);
	}

	/**
	 * Compare an uran and a material
	 * @param m an uran
	 * @param o a material
	 * @return if the two material are both urans, it returns true.
	 */
	public boolean compare(Uran m, Material o) {
		return o.compare(m);
	}

	/**
	 * Compare a waterice and a material
	 * @param m a waterice
	 * @param o a material
	 * @return if the two material are both waterices, it returns true.
	 */
	public boolean compare(WaterIce m, Material o) {
		return o.compare(m);
	}
}
