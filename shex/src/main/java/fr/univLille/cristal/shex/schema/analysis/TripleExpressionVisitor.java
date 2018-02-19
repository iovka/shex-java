/*******************************************************************************
 * Copyright (C) 2018 Université de Lille - Inria
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
