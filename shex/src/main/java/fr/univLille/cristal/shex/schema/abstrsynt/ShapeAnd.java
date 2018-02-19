package fr.univLille.cristal.shex.schema.abstrsynt;

import java.util.List;

import fr.univLille.cristal.shex.schema.analysis.ShapeExpressionVisitor;
import fr.univLille.cristal.shex.util.CollectionToString;

/**
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public class ShapeAnd extends AbstractNaryShapeExpr{


	public ShapeAnd(List<ShapeExpr> subExpressions) {
		super(subExpressions);
	}
	
	@Override
	public String toString() {
		return CollectionToString.collectionToString(getSubExpressions(), " AND ", "(", ")");
	}

	@Override
	public <ResultType> void accept(ShapeExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitShapeAnd(this, arguments);
	}
	
}