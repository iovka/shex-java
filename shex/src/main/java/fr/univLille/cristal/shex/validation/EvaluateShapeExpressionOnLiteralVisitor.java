/**
Copyright 2017 University of Lille

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/

package fr.univLille.cristal.shex.validation;

import org.eclipse.rdf4j.model.Literal;

import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.NodeConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAnd;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNot;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExternal;
import fr.univLille.cristal.shex.schema.analysis.ShapeExpressionVisitor;

/** Recursively visits the expression and its {@link ShapeExprRef} atoms.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
class EvaluateShapeExpressionOnLiteralVisitor extends ShapeExpressionVisitor<Boolean> {
	
	private Literal literal; 
	private Boolean result;
	
	public EvaluateShapeExpressionOnLiteralVisitor(Literal literal) {
		this.literal = literal;
	}

	@Override
	public Boolean getResult() {
		if (result == null) return false;
		return result;
	}
	
	@Override
	public void visitShapeAnd(ShapeAnd expr, Object... arguments) {
		for (ShapeExpr e : expr.getSubExpressions()) {
			e.accept(this);
			if (result == null || !result) break;
		}
	}

	@Override
	public void visitShapeOr(ShapeOr expr, Object... arguments) {
		for (ShapeExpr e : expr.getSubExpressions()) {
			e.accept(this);
			if (result == null || result) break;
		}
	}
	
	@Override
	public void visitShapeNot(ShapeNot expr, Object... arguments) {
		expr.getSubExpression().accept(this);
		if (result != null) result = !result;
	}
	
	@Override
	public void visitShape(Shape expr, Object... arguments) {
		result = null;
	}

	@Override
	public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
		result = expr.contains(literal);
	}

	@Override
	public void visitShapeExprRef(ShapeExprRef ref, Object[] arguments) {
		ref.getShapeDefinition().accept(this);
	}

	@Override
	public void visitShapeExternal(ShapeExternal shapeExt, Object[] arguments) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
}