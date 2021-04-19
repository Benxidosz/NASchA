package Proto;

import Proto.material.Material;
import Proto.material.materials.*;

import java.util.LinkedList;

public class Inventory {
	private final LinkedList<Material> materials = new LinkedList<>();
	private final int maxSpace;

	public Inventory() {
		maxSpace = -1;
	}

	public Inventory(int space) {
		maxSpace = space;
	}

	public boolean compareMaterial(Material m1, Material m2) {
		return false;
	}

	private boolean compareMaterial(Coal m1, Coal m2) {
		return true;
	}

	private boolean compareMaterial(Iron m1, Iron m2) {
		return true;
	}

	private boolean compareMaterial(Silicon m1, Silicon m2) {
		return true;
	}

	private boolean compareMaterial(Uran m1, Uran m2) {
		return true;
	}

	private boolean compareMaterial(WaterIce m1, WaterIce m2) {
		return true;
	}

	public void addMaterial(Material m) {
		if (materials.size() < maxSpace || maxSpace == -1)
			materials.add(m);
	}

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

	public void rmAllFromRecipe(Inventory recipe) {
		recipe.materials.forEach(this::rmMaterial);
	}

	public boolean containsMaterial(Material material) {
		for (Material m : materials)
			if (compareMaterial(material, m))
				return true;

		return false;
	}

	public boolean containsRecipe(Inventory recipe) {
		for (Material m : recipe.materials)
			if (!containsMaterial(m))
				return false;

		return true;
	}

	public LinkedList<Material> getMaterials() {
		return materials;
	}

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

	@Override
	public Object clone() throws CloneNotSupportedException {
		Inventory clone = new Inventory(maxSpace);
		for (Material m : materials) {
			clone.addMaterial(m);
		}

		return clone;
	}
}
