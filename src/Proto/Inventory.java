package Proto;

import Proto.material.Material;
import Proto.material.materials.*;

import java.util.LinkedList;

public class Inventory {
	private final LinkedList<Material> materials;
	private final int maxSpace;

	public Inventory() {
		maxSpace = -1;
		materials = new LinkedList<>();
	}

	public Inventory(int space) {
		maxSpace = space;
		materials = new LinkedList<>();
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
}
