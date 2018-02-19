package fr.univLille.cristal.shex.validation;

import fr.univLille.cristal.shex.graph.NeighborTriple;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class PredicateMatcher implements Matcher {
	
	@Override
	public Boolean apply(NeighborTriple triple, TripleConstraint tc) {
		return tc.getProperty().equals(triple.getPredicate());
	}


}
