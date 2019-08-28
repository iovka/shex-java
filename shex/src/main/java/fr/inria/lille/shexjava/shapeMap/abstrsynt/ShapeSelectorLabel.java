package fr.inria.lille.shexjava.shapeMap.abstrsynt;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;

public class ShapeSelectorLabel extends ShapeSelector {
	protected Label label;
		
	public ShapeSelectorLabel(Label label) {
		super();
		this.label = label;
	}

	@Override
	public Label apply(ShexSchema schema) {
		return label;
	}

	@Override
	public String toString() {
		return label.toPrettyString();
	}

	
}
