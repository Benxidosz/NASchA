package Proto.material.compare;

import Proto.material.Material;
import Proto.material.materials.*;

public class MaterialCompare {
	private static MaterialCompare ref;

	public static void init() {
		ref = new MaterialCompare();
	}

	private MaterialCompare() { }

	public static MaterialCompare getInstance() {
		return ref;
	}

	public boolean compare(Material m, Material o) {
		return m.compare(o);
	}

	public boolean compare(Coal m, Material o) {
		return o.compare(m);
	}

	public boolean compare(Iron m, Material o) {
		return o.compare(m);
	}

	public boolean compare(Silicon m, Material o) {
		return o.compare(m);
	}

	public boolean compare(Uran m, Material o) {
		return o.compare(m);
	}

	public boolean compare(WaterIce m, Material o) {
		return o.compare(m);
	}
}
