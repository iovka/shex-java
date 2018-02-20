package fr.univLille.cristal.shex.schema.concrsynt;

import org.eclipse.rdf4j.model.Value;

public class WildcardConstraint implements Constraint {
	
	@Override
	public boolean contains(Value node) {
		return true;
	}
	
	public String toString() {
		return "WILDCARD";
	}

}
