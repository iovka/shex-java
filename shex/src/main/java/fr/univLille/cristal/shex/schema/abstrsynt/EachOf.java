package fr.univLille.cristal.shex.schema.abstrsynt;

import java.util.List;

import fr.univLille.cristal.shex.schema.analysis.TripleExpressionVisitor;
import fr.univLille.cristal.shex.util.CollectionToString;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class EachOf extends AbstractNaryTripleExpr {
	

	public EachOf(List<TripleExpr> subExpressions) {
		super(subExpressions);
	}
	
	@Override
	public String toString() {
		return CollectionToString.collectionToString(getSubExpressions(), " ; ", "EachOf(", ")");
	}

	@Override
	public <ResultType> void accept(TripleExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitEachOf(this, arguments);
	}

	
}
