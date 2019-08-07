package fr.inria.lille.shexjava.shapeMap;

import java.util.Collection;

import fr.inria.lille.shexjava.shapeMap.abstrsynt.ShapeAssociation;

public class BaseShapeMap {
	protected Collection<ShapeAssociation> associations;

	public BaseShapeMap(Collection<ShapeAssociation> associations) {
		super();
		this.associations = associations;
	}

	public Collection<ShapeAssociation> getAssociations() {
		return associations;
	}
	
	
}
