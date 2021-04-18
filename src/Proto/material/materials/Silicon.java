package Proto.material.materials;

import Proto.material.Material;

public class Silicon extends Material {

	/**
	 * Constructor of the Silicon class. Sets the name and the asteroid where the material is.
	 * @param name The name of the object.
	 */
	public Silicon (String name) {
		super(name);
	}

	@Override
	public String printName() {
		return name;
	}
}
