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

import fr.inria.lille.shexjava.schema.abstrsynt.*;

import java.util.Set;
import java.util.function.Predicate;

public class CollectElementsFromTriple<C> extends TripleExpressionVisitor<Set<C>>{

	private Predicate<Object> filter;
	private boolean traverseShapeExpressions;

	public CollectElementsFromTriple(Predicate<Object> filter, Set<C> collectionSet, boolean traverseShapeExpressions) {
		setResult(collectionSet);
		this.filter = filter;
		this.traverseShapeExpressions = traverseShapeExpressions;
	}

	@Override
	public void visitTripleConstraint(TripleConstraint tc, Object... arguments) {
		if (filter.test(tc))
			getResult().add((C)tc);
		if (traverseShapeExpressions) {
			CollectElementsFromShape<C> c = new CollectElementsFromShape<C>(filter, getResult(), traverseShapeExpressions);
			tc.getShapeExpr().accept(c, arguments);
		}
	}

	@Override
	public void visitEmpty(EmptyTripleExpression expr, Object[] arguments) {
		if (filter.test(expr))
			getResult().add((C)expr);
	}

	@Override
	public void visitEachOf(EachOf expr, Object... arguments) {
		if (filter.test(expr))
			getResult().add((C)expr);
		super.visitEachOf(expr, arguments);
	}

	@Override
	public void visitOneOf(OneOf expr, Object... arguments) {
		if (filter.test(expr))
			getResult().add((C)expr);
		super.visitOneOf(expr, arguments);
	}


	@Override
	public void visitRepeated(RepeatedTripleExpression expr, Object[] arguments) {
		if (filter.test(expr))
			getResult().add((C)expr);
		super.visitRepeated(expr, arguments);
	}

	@Override
	public void visitTripleExprReference(TripleExprRef expr, Object... arguments) {
		if (filter.test(expr))
			getResult().add((C)expr);
	}
}
