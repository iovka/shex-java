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
public class ShapeOr extends AbstractNaryShapeExpr{


	public ShapeOr(List<ShapeExpr> subExpressions) {
		super(subExpressions);
	}
	
	@Override
	public String toString() {
		return CollectionToString.collectionToString(getSubExpressions(), " OR ", "(", ")");
	}

	@Override
	public <ResultType> void accept(ShapeExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitShapeOr(this, arguments);
	}
	
}