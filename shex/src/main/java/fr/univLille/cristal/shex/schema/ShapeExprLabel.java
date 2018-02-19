package fr.univLille.cristal.shex.schema;

import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.IRI;

/**
 * 
 * @author Iovka Boneva
 * 11 oct. 2017
 */
public class ShapeExprLabel extends Label {

	public ShapeExprLabel(BNode bnode) {
		super(bnode);
	}

	public ShapeExprLabel(IRI iri) {
		super(iri);
	}

	public ShapeExprLabel(BNode bnode, boolean generated) {
		super(bnode, generated);
	}

	public ShapeExprLabel(IRI iri, boolean generated) {
		super(iri, generated);
	}
	
	
}
