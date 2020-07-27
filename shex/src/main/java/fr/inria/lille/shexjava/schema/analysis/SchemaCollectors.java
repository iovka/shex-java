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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.abstrsynt.*;
import fr.inria.lille.shexjava.util.Pair;


/** This class provide a set of functions to collect element from a set of rules. 
 * 
 * @author Iovka Boneva
 * @author Jérémie Dusart
 */
public class SchemaCollectors {

	private static SchemaCollectors staticInstance = new SchemaCollectors();
	public static SchemaCollectors getInstance () {
		return staticInstance;
	}


	// -------------------------------------------------------------------------
	// Work on shape
	// -------------------------------------------------------------------------

	public static Set<ShapeExpr> collectAllShapeExprs (Map<Label, ShapeExpr> rules) {
		Set<ShapeExpr> set = new HashSet<>();
		CollectElementsFromShape<ShapeExpr> collector = 
				new CollectElementsFromShape<ShapeExpr>((Object ast) -> (ast instanceof ShapeExpr), 
						set,
						true);
		for (ShapeExpr expr: rules.values()) {
			expr.accept(collector);
		}
		return set;
	}


	public static Set<ShapeExprRef> collectAllShapeRefs (Map<Label, ShapeExpr> rules) { 
		Set<ShapeExprRef> set = new HashSet<>();
		CollectElementsFromShape<ShapeExprRef> collector = 
				new CollectElementsFromShape<ShapeExprRef>((Object ast) -> (ast instanceof ShapeExprRef), 
						set,
						true);
		for (ShapeExpr expr: rules.values()) {
			expr.accept(collector);
		}
		return set;
	}
	
	


	// -------------------------------------------------------------------------
	// Work on triple
	// -------------------------------------------------------------------------

	public static Set<TripleExpr> collectAllTriples (Map<Label, ShapeExpr> rules) { 
		Set<TripleExpr> set = new HashSet<>();
		CollectElementsFromShape<TripleExpr> collector = 
				new CollectElementsFromShape<TripleExpr>((Object ast) -> (ast instanceof TripleExpr), 
						set,
						true);
		for (ShapeExpr expr: rules.values()) {
			expr.accept(collector);
		}
		return set;
	}

	public static Set<TripleExpr> collectAllTriplesRef (Map<Label, ShapeExpr> rules) { 
		Set<TripleExpr> set = new HashSet<>();
		CollectElementsFromShape<TripleExpr> collector = 
				new CollectElementsFromShape<TripleExpr>((Object ast) -> (ast instanceof TripleExprRef), 
						set,
						true);
		for (ShapeExpr expr: rules.values()) {
			expr.accept(collector);
		}
		return set;
	}

	public static Set<Shape> collectAllShapes (ShapeExpr shapeExpr) {
		Set<Shape> set = new HashSet<>();
		CollectElementsFromShape<Shape> collector = new CollectElementsFromShape<>( x -> x instanceof Shape, set, false);
		shapeExpr.accept(collector);
		return set;
	}

}

//---------------------------------------------------------------------------
// Collectors
//---------------------------------------------------------------------------




// TODO these classes should be available for reuse elsewhere. Other visitors do more or less the same thing
class CollectElementsFromShape<C> extends ShapeExpressionVisitor<Set<C>> {

	private Predicate<Object> filter;
	private boolean traverseTripleExpressions;

	public CollectElementsFromShape (Predicate<Object> filter, Set<C> collectionSet, boolean traverseTripleExpressions) {
		setResult(collectionSet);
		this.filter = filter;
		this.traverseTripleExpressions = traverseTripleExpressions;
	}

	@Override
	public void visitShape(Shape expr, Object... arguments) {
		if (filter.test(expr))
			getResult().add((C)expr);
		for (ShapeExprRef ext : expr.getBases())
			ext.accept(this, arguments);
		if (traverseTripleExpressions) {
			CollectElementsFromTriple<C> c = new CollectElementsFromTriple<C>(filter,getResult(), traverseTripleExpressions);
			expr.getTripleExpression().accept(c, arguments);
		}
	}

	@Override
	public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
		if (filter.test(expr))
			getResult().add((C)expr);
	}

	@Override
	public void visitShapeExprRef(ShapeExprRef shapeRef, Object[] arguments) {
		if (filter.test(shapeRef))
			getResult().add((C)shapeRef);
	}

	@Override
	public void visitShapeAnd(ShapeAnd expr, Object... arguments) {
		if (filter.test(expr))
			getResult().add((C)expr);
		super.visitShapeAnd(expr, arguments);
	}

	@Override
	public void visitShapeOr(ShapeOr expr, Object... arguments) {
		if (filter.test(expr))
			getResult().add((C)expr);
		super.visitShapeOr(expr, arguments);
	}

	@Override
	public void visitShapeNot(ShapeNot expr, Object... arguments) {
		if (filter.test(expr))
			getResult().add((C)expr);
		super.visitShapeNot(expr, arguments);
	}
}

