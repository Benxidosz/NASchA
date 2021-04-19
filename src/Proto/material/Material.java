package Proto.material;

import Proto.Main;
import Proto.things.asteroids.Asteroid;

public abstract class Material {
	protected Asteroid myAsteroid;
	protected String name;

	/**
	 * Constructor of the Material class. Sets the name and the asteroid where the material is.
	 * @param name The name of the object.
	 */
	public Material(String name){
		this.name = name;
	}


	/**
	 * The asteroid that contains the material is near sun and its last layer was drilled.
	 */
	public void nearSun() {

	}

	public String getName(){
		return name;
	}

	public void setMyAsteroid(Asteroid myAsteroid) {
		this.myAsteroid = myAsteroid;
	}

}
