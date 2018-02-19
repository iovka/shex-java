package fr.univLille.cristal.shex.schema;

import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.IRI;

/**
 * 
 * @author Iovka Boneva
 * 11 oct. 2017
 */
public class TripleExprLabel extends Label {
	
	public TripleExprLabel(BNode bnode) {
		super(bnode);
	}

	public TripleExprLabel(IRI iri) {
		super(iri);
	}

	public TripleExprLabel(BNode bnode, boolean generated) {
		super(bnode, generated);
	}

	public TripleExprLabel(IRI iri, boolean generated) {
		super(iri, generated);
	}
	
	
}
