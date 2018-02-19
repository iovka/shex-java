package fr.univLille.cristal.shex.schema.abstrsynt;

import fr.univLille.cristal.shex.schema.analysis.ShapeExpressionVisitor;

/**
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public class ShapeNot extends ShapeExpr {

	private ShapeExpr subExpression;
	
	public ShapeNot(ShapeExpr subExpression) {
		this.subExpression = subExpression;
	}
	
	public ShapeExpr getSubExpression(){
		return subExpression;
	}

	@Override
	public <ResultType> void accept(ShapeExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitShapeNot(this, arguments);
	}
	

	@Override
	public String toString() {
		return "(NOT "+subExpression+")";
	}
	
}
