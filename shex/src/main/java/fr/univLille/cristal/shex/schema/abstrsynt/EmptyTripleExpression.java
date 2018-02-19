package fr.univLille.cristal.shex.schema.abstrsynt;

import fr.univLille.cristal.shex.schema.analysis.TripleExpressionVisitor;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class EmptyTripleExpression extends TripleExpr {

	
	@Override
	public <ResultType> void accept(TripleExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitEmpty(this, arguments);
	}
	
	@Override
	public String toString() {
		return "EMPTY";
	}
	
}
