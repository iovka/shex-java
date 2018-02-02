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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.EachOf;
import fr.univLille.cristal.shex.schema.abstrsynt.EmptyTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.NodeConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.OneOf;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAnd;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExternal;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNot;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOr;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExprRef;


/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class SchemaRulesStaticAnalysis {

	private static SchemaRulesStaticAnalysis staticInstance = new SchemaRulesStaticAnalysis();
	public static SchemaRulesStaticAnalysis getInstance () {
		return staticInstance;
	}


	// -------------------------------------------------------------------------
	// COMMON Shape
	// -------------------------------------------------------------------------

	public static Set<ShapeExpr> collectAllShapes (Map<ShapeExprLabel, ShapeExpr> rules) { 
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


	public static Set<ShapeExprRef> collectAllShapeRefs (Map<ShapeExprLabel, ShapeExpr> rules) { 
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
	// COMMON Triple
	// -------------------------------------------------------------------------

	public static Set<TripleExpr> collectAllTriples (Map<ShapeExprLabel, ShapeExpr> rules) { 
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

	public static Set<TripleExpr> collectAllTriplesRef (Map<ShapeExprLabel, ShapeExpr> rules) { 
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

	//---------------------------------------------------------------------------
	// Collectors
	//---------------------------------------------------------------------------


}

class CollectElementsFromShape<C> extends ShapeExpressionVisitor<Set<C>> {

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

class CollectElementsFromTriple<C> extends TripleExpressionVisitor<Set<C>>{

	private Set<C> set;
	private Predicate<Object> filter;
	private boolean traverseShapeExpressions;

	public CollectElementsFromTriple(Predicate<Object> filter, Set<C> collectionSet, boolean traverseShapeExpressions) {
		this.set = collectionSet;
		this.filter = filter;
		this.traverseShapeExpressions = traverseShapeExpressions;
	}

	@Override
	public Set<C> getResult() {
		return set;
	}

	@Override
	public void visitTripleConstraint(TripleConstraint tc, Object... arguments) {
		if (filter.test(tc))
			set.add((C)tc);
		if (traverseShapeExpressions) {
			CollectElementsFromShape<C> c = new CollectElementsFromShape<C>(filter, set, traverseShapeExpressions);
			tc.getShapeExpr().accept(c, arguments);
		}
	}

	@Override
	public void visitEmpty(EmptyTripleExpression expr, Object[] arguments) {
		if (filter.test(expr))
			set.add((C)expr);
	}

	@Override
	public void visitEachOf(EachOf expr, Object... arguments) {
		if (filter.test(expr))
			set.add((C)expr);
		super.visitEachOf(expr, arguments);
	}

	@Override
	public void visitOneOf(OneOf expr, Object... arguments) {
		if (filter.test(expr))
			set.add((C)expr);
		super.visitOneOf(expr, arguments);
	}


	@Override
	public void visitRepeated(RepeatedTripleExpression expr, Object[] arguments) {
		if (filter.test(expr))
			set.add((C)expr);
		super.visitRepeated(expr, arguments);
	}

	@Override
	public void visitTripleExprReference(TripleExprRef expr, Object... arguments) {
		if (filter.test(expr))
			set.add((C)expr);
	}
}
