package Graphics;

import Graphics.material.Material;
import Graphics.material.MaterialCompare;

import java.util.LinkedList;

/**
 * A type of storage for multiple Materials. It contains the methods what needed to compare the different materials.
 * With this class it can store the materials what we still need on the MainAsteroid to accomplish.
 */
public class Inventory implements Cloneable {
	/**
	 * A container for materials stored in the Inventory.
	 */
	private final LinkedList<Material> materials = new LinkedList<>();
	/**
	 * It tells how many pieces can fit in it.
	 */
	private final int maxSpace;
	/**
	 * The constructor of the Inventory.
	 */
	public Inventory() {
		maxSpace = -1;
	}

	/**
	 * The constructor of the Inventory.
	 * @param space The number of space.
	 */
	public Inventory(int space) {
		maxSpace = space;
	}

	/**
	 * It compares two materials. It will be called if the two Materials are not the same.
	 * @param m1 A material.
	 * @param m2 Another material.
	 * @return with false.
	 */
	private boolean compareMaterial(Material m1, Material m2) {
		return MaterialCompare.getInstance().compare(m1, m2);
	}

	/**
	 * Adds a material to the container.
	 * @param m the material which is added to the materials list.
	 */
	public void addMaterial(Material m) {
		if (materials.size() < maxSpace || maxSpace == -1)
			materials.add(m);
	}

	/**
	 * Removes a material from the container.
	 * @param m the material which is removed from the materials list.
	 */
	public void rmMaterial(Material m) {
		Material found = null;
		for (Material material : materials) {
			if (compareMaterial(material, m)) {
				found = material;
				break;
			}
		}
		if (found != null)
			materials.remove(found);
	}

	/**
	 * Removes the materials in the resulting recipe Inventory from its own materials.
	 * @param recipe a recipe from the Inventory.
	 */
	public void rmAllFromRecipe(Inventory recipe) {
		recipe.materials.forEach(this::rmMaterial);
	}

	/**
	 * Checks and returns the resulting material is presents in the Materials.
	 * @param material a material
	 * @return if the given material is equal to the received m, it's true, if not it's false.
	 */
	public boolean containsMaterial(Material material) {
		for (Material m : materials)
			if (compareMaterial(material, m))
				return true;

		return false;
	}

	/**
	 * Checks and returns the contents of the received recipe can be found in the inventory.
	 * @param recipe a recipe from the Inventory.
	 * @return if the copied recipe is empty, it is true, if not it's false.
	 */
	public boolean containsRecipe(Inventory recipe) {
		try {
			Inventory tmp = (Inventory) this.clone();
			for (Material m : recipe.materials)
				if (!tmp.containsMaterial(m))
					return false;
				else
					tmp.rmMaterial(m);

			return true;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 *Returns the materials of the Material.
	 * @return with materials
	 */
	public LinkedList<Material> getMaterials() {
		return materials;
	}

	/**
	 * Check the material that he has space
	 * @return the size of material or maxSpace equal -1 if size of material is bigger than maxSpace
	 */
	public boolean hasSpace() {
		return (materials.size() < maxSpace || maxSpace == -1);
	}

	/**
	 * Check the material, is it empty or not.
	 * @return is it empty or not.
	 */
	public boolean isEmpty() {
		return materials.isEmpty();
	}

	/**
	 * Copies an inventory.
	 * @return the clone
	 * @throws CloneNotSupportedException
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		Inventory clone = new Inventory(maxSpace);
		for (Material m : materials) {
			clone.addMaterial(m);
		}

		return clone;
	}
}
