package fr.univLille.cristal.shex.schema.abstrsynt;

import java.util.List;

import fr.univLille.cristal.shex.schema.analysis.TripleExpressionVisitor;
import fr.univLille.cristal.shex.util.CollectionToString;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class OneOf extends AbstractNaryTripleExpr {
	

	public OneOf(List<TripleExpr> subExpressions) {
		super(subExpressions);
	}
	
	@Override
	public String toString() {
		return CollectionToString.collectionToString(getSubExpressions(), " | ", "OneOf(", ")");
	}

	@Override
	public <ResultType> void accept(TripleExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitOneOf(this, arguments);
	}
	
}
