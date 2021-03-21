package Skeleton.materials.children;

import Skeleton.materials.Material;

public class WaterIce extends Material {
	public void nearSun() {
		myAsteroid.setCore(null);
	}

	@Override
	public String printName() {
		return null;
	}
}
