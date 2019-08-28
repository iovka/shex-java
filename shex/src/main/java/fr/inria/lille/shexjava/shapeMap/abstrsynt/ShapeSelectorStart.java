package fr.inria.lille.shexjava.shapeMap.abstrsynt;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;

public class ShapeSelectorStart extends ShapeSelector {

	@Override
	public Label apply(ShexSchema schema) {
		return schema.getStart().getId();
	}

	@Override
	public String toString() {
		return " START";
	}

}
