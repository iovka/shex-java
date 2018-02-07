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
