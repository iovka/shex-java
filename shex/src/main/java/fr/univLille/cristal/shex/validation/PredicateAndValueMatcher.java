package fr.univLille.cristal.shex.validation;

import fr.univLille.cristal.shex.graph.NeighborTriple;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class PredicateAndValueMatcher implements Matcher {
	private Typing typing;
	
	public PredicateAndValueMatcher(Typing typing) {
		this.typing = typing;
	}
	
	@Override
	public Boolean apply(NeighborTriple triple, TripleConstraint tc) {
		if (tc.getProperty().equals(triple.getPredicate())) 
			return typing.contains(triple.getOpposite(), tc.getShapeExpr().getId());
		return false;
	}


}
