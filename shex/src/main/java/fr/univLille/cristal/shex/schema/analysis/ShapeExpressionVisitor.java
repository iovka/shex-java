package fr.univLille.cristal.shex.schema.analysis;

import fr.univLille.cristal.shex.schema.abstrsynt.NodeConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAnd;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExternal;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNot;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOr;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 * @param <ResultType>
 */
public abstract class ShapeExpressionVisitor<ResultType> {
	
	public abstract ResultType getResult ();
	
	public void visitShapeAnd (ShapeAnd expr, Object ... arguments) {
		for (ShapeExpr subExpr: expr.getSubExpressions()) {
			subExpr.accept(this, arguments);
		}
	}

	public void visitShapeOr (ShapeOr expr, Object ... arguments) {
		for (ShapeExpr subExpr: expr.getSubExpressions()) {
			subExpr.accept(this, arguments);
		}
	}
	
	public void visitShapeNot (ShapeNot expr, Object ...arguments) {
		expr.getSubExpression().accept(this, arguments);
	}
	
	public abstract void visitShape (Shape expr, Object... arguments) ;
	public abstract void visitNodeConstraint (NodeConstraint expr, Object ... arguments);
	public abstract void visitShapeExprRef(ShapeExprRef shapeRef, Object[] arguments);
	public abstract void visitShapeExternal (ShapeExternal shapeExt, Object[] arguments);
	
}
