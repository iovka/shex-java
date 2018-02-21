package fr.univLille.cristal.shex.schema.concrsynt;

import org.eclipse.rdf4j.model.Value;

public class WildcardConstraint implements Constraint {
	
	@Override
	public boolean contains(Value node) {
		return true;
	}
	
	@Override
	public String toString() {
		return "WILDCARD";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
}
