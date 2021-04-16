package Proto.material.materials;

import Proto.material.Material;

public class Coal extends Material {

	/**
	 * Constructor of the Coal class. Sets the name and the asteroid where the material is.
	 * @param name The name of the object.
	 */
	public Coal(String name){
		super(name);
	}

	@Override
	public String printName() {
		return name;
	}
}
