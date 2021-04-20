package Proto.things.gate;

import Proto.entity.Entity;
import Proto.things.Thing;

/**
 * Settlers can build teleport gates. Each has one pair somewhere on the field.
 * They can move on to that pair in 1 step, even if they are not neighbours.
 */
public class TeleportGate extends Thing {
	/**
	 * The pair TeleportGate of this TeleportGate
	 */
	private TeleportGate pair;
	/**
	 * Stores if the TeleportGate is normal
	 */
	private boolean normal;
	/**
	 * Stores if the TeleportGate is active
	 */
	private boolean active;

	private boolean setted;

	/**
	 * The constructor of the class.
	 * @param name the name of the Object.
	 */
	public TeleportGate(String name) {
		super(name);
		active = false;
		setted = false;
		normal = true;
	}

	/**
	 * Applies sun eruption on the TeleportGate.
	 */
	public void applySunEruption() {
		super.applySunEruption();
		normal = false;
	}

	/**
	 * Activates the gates pair when this gate is placed.
	 */
	public void activate() {
		setted = true;
		pair.setActive(true);
	}

	/**
	 * If the TeleportGate isn't normal it travels and gets a new neighbour.
	 */
	public void makeTurn(){
		if(normal) return;

		Thing rand1 = randomNeighbour();
		Thing rand2 = rand1.randomNeighbour();

		if(rand2 == this) return;

		for(Thing t: neighbour) {
			removeNeighbour(t);
			t.removeNeighbour(this);
		}

		addNeighbour(rand2);
		rand2.addNeighbour(this);
	}

	/**
	 * Adds a Entity on the TeleportGate.
	 * @param entity the entity that is added.
	 */
	@Override
	public Thing addEntity(Entity entity){
		if(active) {
			pair.passEntity(entity);
			return pair;
		}
		entities.add(entity);
		return this;
	}

	/**
	 * Teleport entity to the gates pair.
	 * @param entity the entity that is teleported.
	 */
	public void passEntity(Entity entity){
		entities.add(entity);

	}

	/**
	 * Sets the teleport gates pair.
	 * @param gate2 The gates pair
	 */
	public void setPair(TeleportGate gate2) {
		pair = gate2;
	}

	/**
	 * Sets The teleport gates active attribute
	 * @param act The boolean to set
	 */
	public void setActive(boolean act){
		active = act;
	}

	/**
	 * Lists the attributes of the object.
	 * @return the attributes as a string.
	 */
	@Override
	public String List() {
		StringBuilder result = new StringBuilder("+------------------+\n");

		result.append("name: " + getName());

		result.append("\nneighbours: ");
		if (neighbour.size() == 0)
			result.append("null");
		else
			for (Thing nei : neighbour)
				if (nei != neighbour.get(neighbour.size() - 1))
					result.append(nei.getName() + " ");
				else
					result.append(nei.getName());

		result.append("\nentities: ");
		if (entities.size() == 0)
			result.append("null");
		else
			for (Entity ent : entities)
				if (ent != entities.get(entities.size() - 1))
					result.append(ent.getName() + " ");
				else
					result.append(ent.getName());

		result.append("\npair: " + ((pair != null) ? pair.getName() : "null"));
		result.append("\nsetted: " + (setted ? "true" : "false"));
		result.append("\nactive: " + (active ? "true" : "false"));
		result.append("\nnormal: " + (normal ? "true" : "false") + "\n");
		return result.toString();
	}
}
