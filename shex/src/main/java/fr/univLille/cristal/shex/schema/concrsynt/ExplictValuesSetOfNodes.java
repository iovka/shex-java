package fr.univLille.cristal.shex.schema.concrsynt;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class ExplictValuesSetOfNodes implements SetOfNodes {

	private Set<Value> allowedValues;
	
	public ExplictValuesSetOfNodes(Collection<Value> allowedValues) {
		this.allowedValues = new HashSet<>(allowedValues);
	}

	@Override
	public boolean contains(Value node) {
		return allowedValues.contains(node);
	}

	@Override
	public String toString() {
		return allowedValues.toString();
	}
	
}
