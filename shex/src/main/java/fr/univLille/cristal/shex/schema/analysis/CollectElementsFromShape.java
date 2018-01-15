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

import java.util.Set;
import java.util.function.Predicate;

import fr.univLille.cristal.shex.schema.abstrsynt.NodeConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAnd;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExternal;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNot;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOr;

/** Collects all {@link AbstractASTElement}s that satisfy a given filter.
 * 
 * @author Iovka Boneva
 * 12 oct. 2017
 * @param <C>
 */
public class CollectElementsFromShape<C> extends ShapeExpressionVisitor<Set<C>> {

	private Set<C> set;
	private Predicate<Object> filter;
	private boolean traverseTripleExpressions;

	public CollectElementsFromShape (Predicate<Object> filter, Set<C> collectionSet, boolean traverseTripleExpressions) {
		this.set = collectionSet;
		this.filter = filter;
		this.traverseTripleExpressions = traverseTripleExpressions;
	}
	
	@Override
	public Set<C> getResult() {
		return set;
	}

	@Override
	public void visitShape(Shape expr, Object... arguments) {
		if (filter.test(expr))
			set.add((C)expr);
		if (traverseTripleExpressions) {
			CollectElementsFromTriple<C> c = new CollectElementsFromTriple<C>(filter,set, traverseTripleExpressions);
			expr.getTripleExpression().accept(c, arguments);
		}
	}

	@Override
	public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
		if (filter.test(expr))
			set.add((C)expr);
	}

	@Override
	public void visitShapeExprRef(ShapeExprRef shapeRef, Object[] arguments) {
		if (filter.test(shapeRef))
			set.add((C)shapeRef);
	}

	@Override
	public void visitShapeExternal(ShapeExternal shapeExt, Object[] arguments) {
		if (filter.test(shapeExt))
			set.add((C)shapeExt);
	}
	
	@Override
	public void visitShapeAnd(ShapeAnd expr, Object... arguments) {
		if (filter.test(expr))
			set.add((C)expr);
		super.visitShapeAnd(expr, arguments);
	}
	
	@Override
	public void visitShapeOr(ShapeOr expr, Object... arguments) {
		if (filter.test(expr))
			set.add((C)expr);
		super.visitShapeOr(expr, arguments);
	}
	
	@Override
	public void visitShapeNot(ShapeNot expr, Object... arguments) {
		if (filter.test(expr))
			set.add((C)expr);
		super.visitShapeNot(expr, arguments);
	}

}
