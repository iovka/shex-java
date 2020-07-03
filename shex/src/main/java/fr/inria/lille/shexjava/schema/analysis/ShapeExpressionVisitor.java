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
package fr.inria.lille.shexjava.schema.analysis;

import fr.inria.lille.shexjava.schema.abstrsynt.NodeConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeAnd;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExpr;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExprRef;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExternal;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeNot;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeOr;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 * @param <ResultType>
 */
public abstract class ShapeExpressionVisitor<ResultType> {

	private ResultType result;

	public ResultType getResult () {
		return result;
	}

	protected void setResult (ResultType result) {
		this.result = result;
	}
	
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
	public abstract void visitShapeExprRef(ShapeExprRef shapeRef, Object... arguments);
	public void visitShapeExternal (ShapeExternal shapeExt, Object... arguments) {
		throw new UnsupportedOperationException("not yet implemented");
	}
	
}
