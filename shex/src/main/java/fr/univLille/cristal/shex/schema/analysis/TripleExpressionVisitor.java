package fr.univLille.cristal.shex.schema.analysis;

import fr.univLille.cristal.shex.schema.abstrsynt.EachOf;
import fr.univLille.cristal.shex.schema.abstrsynt.EmptyTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.OneOf;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExprRef;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 * @param <ResultType>
 */
public abstract class TripleExpressionVisitor<ResultType> {
	
	public abstract ResultType getResult ();

	public abstract void visitTripleConstraint (TripleConstraint tc, Object ... arguments);
	
	public abstract void visitTripleExprReference (TripleExprRef expr, Object... arguments) ;

	public abstract void visitEmpty(EmptyTripleExpression expr, Object[] arguments);
	
	public void visitEachOf (EachOf expr, Object ... arguments) {
		for (TripleExpr subExpr : expr.getSubExpressions())
			subExpr.accept(this, arguments);
	}
	
	public void visitOneOf (OneOf expr, Object ... arguments) {
		for (TripleExpr subExpr : expr.getSubExpressions())
			subExpr.accept(this, arguments);
	}
	
	public void visitRepeated(RepeatedTripleExpression expr, Object[] arguments) {
		expr.getSubExpression().accept(this,arguments);
	}
}
