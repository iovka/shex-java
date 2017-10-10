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

import fr.univLille.cristal.shex.schema.abstrsynt.NeighbourhoodConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.NodeConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAndExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNotExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOrExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeRef;
import fr.univLille.cristal.shex.schema.analysis.ShapeExpressionVisitor;

/** Recursively visits the expression and its {@link ShapeRef} atoms.
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
	public void visitShapeAnd(ShapeAndExpression expr, Object... arguments) {
		for (ShapeExpression e : expr.getSubExpressions()) {
			e.accept(this);
			if (result == null || !result) break;
		}
	}

	@Override
	public void visitShapeOr(ShapeOrExpression expr, Object... arguments) {
		for (ShapeExpression e : expr.getSubExpressions()) {
			e.accept(this);
			if (result == null || result) break;
		}
	}
	
	@Override
	public void visitShapeNot(ShapeNotExpression expr, Object... arguments) {
		expr.getSubExpression().accept(this);
		if (result != null) result = !result;
	}
	
	@Override
	public void visitNeighbourhoodConstraint(NeighbourhoodConstraint expr, Object... arguments) {
		result = null;
	}

	@Override
	public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
		result = expr.contains(literal);
	}

	@Override
	public void visitShapeRef(ShapeRef ref, Object[] arguments) {
		ref.getShapeDefinition().expression.accept(this);
	}
	
}