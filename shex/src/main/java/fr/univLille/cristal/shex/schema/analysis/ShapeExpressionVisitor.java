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

import fr.univLille.cristal.shex.schema.abstrsynt.NeighbourhoodConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.NodeConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAndExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNotExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOrExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeRef;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 * @param <ResultType>
 */
public abstract class ShapeExpressionVisitor<ResultType> {
	
	public abstract ResultType getResult ();
	
	public void visitShapeAnd (ShapeAndExpression expr, Object ... arguments) {
		for (ShapeExpression subExpr: expr.getSubExpressions()) {
			subExpr.accept(this, arguments);
		}
	}

	public void visitShapeOr (ShapeOrExpression expr, Object ... arguments) {
		for (ShapeExpression subExpr: expr.getSubExpressions()) {
			subExpr.accept(this, arguments);
		}
	}
	
	public void visitShapeNot (ShapeNotExpression expr, Object ...arguments) {
		expr.getSubExpression().accept(this, arguments);
	}
	
	public abstract void visitNeighbourhoodConstraint (NeighbourhoodConstraint expr, Object... arguments) ;
	public abstract void visitNodeConstraint (NodeConstraint expr, Object ... arguments);
	public abstract void visitShapeRef(ShapeRef shapeRef, Object[] arguments);
	
}
