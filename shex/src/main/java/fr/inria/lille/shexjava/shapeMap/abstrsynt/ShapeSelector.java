package fr.inria.lille.shexjava.shapeMap.abstrsynt;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;

public abstract class ShapeSelector {
	public abstract Label apply(ShexSchema schema);
}
