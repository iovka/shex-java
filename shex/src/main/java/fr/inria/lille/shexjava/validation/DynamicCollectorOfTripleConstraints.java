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
package fr.inria.lille.shexjava.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.abstrsynt.*;
import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;
import fr.inria.lille.shexjava.schema.analysis.TripleExpressionVisitor;

/** Allows to compute the triple constraints that appear in a shape.
 * Memorizes already computed results. 
 * 
 * @author Jérémie Dusart
 */
public class DynamicCollectorOfTripleConstraints {

	private Map<Label, List<TripleConstraint>> collectedTCs = new HashMap<>();
	
	public List<TripleConstraint> getTCs (ShapeExpr sexpr) {
		List<TripleConstraint> result = collectedTCs.get(sexpr.getId());
		if (result == null) {
			sexpr.accept(collectorForShapeExpr);
			result = collectorForShapeExpr.getResult();
		}
		return result;
	}
	
	public List<TripleConstraint> getTCs (TripleExpr texpr) {
		List<TripleConstraint> result = collectedTCs.get(texpr.getId());
		if (result == null) {
			texpr.accept(collectorForTripleExpr);
			result = collectorForTripleExpr.getResult();
		}
		return result;
	}
	
	
	private final TripleExpressionVisitor<List<TripleConstraint>> collectorForTripleExpr = new TripleExpressionVisitor<List<TripleConstraint>>() {

		private List<TripleConstraint> result;

		private void setResult (TripleExpr expr, List<TripleConstraint> result) {
			this.result = result;
			collectedTCs.put(expr.getId(), result);
		}
		
		@Override
		public List<TripleConstraint> getResult() {
			return result;
		}

		@Override
		public void visitTripleConstraint(TripleConstraint tc, Object... arguments) {
			setResult(tc, Collections.singletonList(tc));
		}

		@Override
		public void visitEmpty(EmptyTripleExpression expr, Object[] arguments) {
			setResult(expr, Collections.emptyList());
		}

		@Override
		public void visitEachOf(EachOf expr, Object... arguments) {
			List<TripleConstraint> newResult = new ArrayList<>();
			for (TripleExpr subExpr : expr.getSubExpressions()) {
				subExpr.accept(this, arguments);
				newResult.addAll(getResult());
			}
			setResult(expr, newResult);
		}

		@Override
		public void visitOneOf(OneOf expr, Object... arguments) {
			List<TripleConstraint> newResult = new ArrayList<>();
			for (TripleExpr subExpr : expr.getSubExpressions()) {
				subExpr.accept(this, arguments);
				newResult.addAll(getResult());
			}
			setResult(expr, newResult);
		}


		@Override
		public void visitRepeated(RepeatedTripleExpression expr, Object[] arguments) {
			expr.getSubExpression().accept(this, arguments);
			setResult(expr, getResult());
		}

		@Override
		public void visitTripleExprReference(TripleExprRef expr, Object... arguments) {
			expr.getTripleExp().accept(this, arguments);
			setResult(expr, getResult());
		}
	};
	
	//Collect the triple constraint bellows a shapeExpr
	private final ShapeExpressionVisitor<List<TripleConstraint>> collectorForShapeExpr = new ShapeExpressionVisitor<List<TripleConstraint>>() {
		private List<TripleConstraint> result;

		private void setResult (ShapeExpr expr, List<TripleConstraint> result) {
			this.result = result;
			collectedTCs.put(expr.getId(), result);
		}
		
		@Override
		public List<TripleConstraint> getResult() {
			return result;
		}

		public void visitShapeAnd (ShapeAnd expr, Object ... arguments) {
			List<TripleConstraint> tmp = new ArrayList<TripleConstraint>();
			for (ShapeExpr subExpr: expr.getSubExpressions()) {
				subExpr.accept(this, arguments);
				tmp.addAll(this.getResult());
			}
			setResult(expr, tmp);
		}

		public void visitShapeOr (ShapeOr expr, Object ... arguments) {
			List<TripleConstraint> tmp = new ArrayList<TripleConstraint>();
			for (ShapeExpr subExpr: expr.getSubExpressions()) {
				subExpr.accept(this, arguments);
				tmp.addAll(this.getResult());
			}
			setResult(expr, tmp);
		}
		
		public void visitShapeNot (ShapeNot expr, Object ...arguments) {
			expr.getSubExpression().accept(this, arguments);
			setResult(expr, this.getResult());
		}
		
		@Override
		public void visitShape(Shape expr, Object... arguments) {
			result = collectedTCs.get(expr.getTripleExpression().getId());
			if (result == null) {
				expr.getTripleExpression().accept(collectorForTripleExpr);
				result = collectorForTripleExpr.getResult();
			}
			setResult(expr, result);
		}

		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
			setResult(expr, Collections.emptyList());			
		}

		@Override
		public void visitShapeExprRef(ShapeExprRef shapeRef, Object... arguments) {
			shapeRef.getShapeDefinition().accept(this, arguments);
			setResult(shapeRef, this.getResult());			
		}

		@Override
		public void visitExtendsShapeExpr(ExtendsShapeExpr expr, Object... arguments) {
			List<TripleConstraint> tmp = new ArrayList<TripleConstraint>();
			expr.getBaseShapeExpr().accept(this, arguments);
			tmp.addAll(this.getResult());
			expr.getExtension().accept(this, arguments);
			tmp.addAll(this.getResult());
			setResult(expr, tmp);
		}

		@Override
		public void visitAbstractShape(AbstractShapeExpr expr, Object... arguments) {
			setResult(expr, Collections.emptyList());
		}
	};
}
