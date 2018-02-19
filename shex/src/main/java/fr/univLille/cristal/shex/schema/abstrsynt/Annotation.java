package fr.univLille.cristal.shex.schema.abstrsynt;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Value;

public class Annotation {
	private IRI predicate;
	private Value objectValue;
	
	
	public Annotation(IRI predicate, Value objectValue) {
		super();
		this.predicate = predicate;
		this.objectValue = objectValue;
	}

	public IRI getPredicate() {
		return predicate;
	}


	public Value getObjectValue() {
		return objectValue;
	}

}
