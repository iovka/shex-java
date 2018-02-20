package fr.univLille.cristal.shex.schema.concrsynt;

import java.util.Set;

import org.eclipse.rdf4j.model.Value;

public class ValueSetValueConstraint implements Constraint {
	private Set<Value> explicitValues;
	private Set<Constraint> constraintsValue;

	public ValueSetValueConstraint(Set<Value> explicitValues, Set<Constraint> constraintsValue) {
		this.explicitValues = explicitValues;
		this.constraintsValue = constraintsValue;
	}

	@Override
	public boolean contains(Value node) {
		if (explicitValues.contains(node))
			return true;
		
		for (Constraint constraint:constraintsValue)
			if (constraint.contains(node))
				return true;
		
		return false;
	}
	
	@Override
	public String toString() {
		return "values:"+explicitValues+ " U "+constraintsValue;
	}
	
	
}
