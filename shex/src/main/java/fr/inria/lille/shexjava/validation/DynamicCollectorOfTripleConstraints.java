/* ******************************************************************************
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

import java.util.*;


import fr.inria.lille.shexjava.schema.abstrsynt.*;
import fr.inria.lille.shexjava.schema.analysis.CollectTripleConstraintsSE;
import fr.inria.lille.shexjava.schema.analysis.CollectTripleConstraintsTE;
import fr.inria.lille.shexjava.util.Pair;

/** Allows to collect the triple constraints that appear in a shape and the shape structure w.r.t. EXTENDS.
 * Memorizes already computed results.
 *
 * @author Iovka Boneva
 * @author Jérémie Dusart
 */
public class DynamicCollectorOfTripleConstraints {

	/** Memorizes the triple constraints for every expression after a call to {@link #getTCs}. */
	private Map<Object, List<TripleConstraint>> collectedTCs = new HashMap<>();
	/** With (tripleConstraint,shape) associates the sub-expression of shape to which tripleConstraint belongs.
	 * Such sub-expression can be either the triple expression of the shape, or a shape reference that is extended by this shape.*/
	private Map<Pair<TripleConstraint, Shape>, Expression> parentsMap = new HashMap<>();

	/** The sub-expression of {@param shape} to which {@param tc} belongs.
	 * This sub-expression is either the triple expression of the shape, or a shape reference that is extended by this shape.
	 * Is defined only in case {@param tc} was in the result of an earlier call to {@see #getTCs}.
	 */
	public Expression getParentInShape (TripleConstraint tc, Shape shape) {
		return parentsMap.get(new Pair<>(tc, shape));
	}

	/** Collects the triple constraints of a triple expression. */
	public List<TripleConstraint> getTCs (TripleExpr texpr) {
		List<TripleConstraint> result = collectedTCs.get(texpr);
		if (result == null) {
			CollectTripleConstraintsTE collector = new CollectTripleConstraintsTE();
			texpr.accept(collector);
			result = collector.getResult().one;
			collectedTCs.put(texpr, result);
		}
		return result;
	}

	/** Collects the triple constraints of a shape and determines the parent sub-expression in {@param shape} of all such sub-expressions.
	 * The parent can be retrieved subsequently using {@see #getParentInShape}. */
	public List<TripleConstraint> getTCs (Shape shape) {
		List<TripleConstraint> tripleConstraints = collectedTCs.get(shape);

		boolean parentIsTrivial = shape.getExtended().isEmpty();
		Deque<Expression> parents = null;
		if (! parentIsTrivial) parents = new ArrayDeque<>();

		if (tripleConstraints == null) {
			CollectTripleConstraintsSE collector = new CollectTripleConstraintsSE();
			if (parentIsTrivial) shape.accept(collector);
			else shape.accept(collector, parents);
			tripleConstraints = collector.getResult().one;
			collectedTCs.put(shape, tripleConstraints);
			if (parentIsTrivial)
				updateParentStructure(tripleConstraints, shape, shape.getTripleExpression());
			else
				updateParentStructure(collector.getResult().two);
		}
		return tripleConstraints;
	}

	/** Memorizes the unique {@param commonParent} of a all {@param tripleConstraint}s in a {@param shape} without extends. */
	private void updateParentStructure (List<TripleConstraint> tripleConstraints, Shape shape, TripleExpr commonParent) {
		for (TripleConstraint tc : tripleConstraints)
			this.parentsMap.put(new Pair<>(tc,shape), commonParent);
	}

	/** For every triple constraint in the input map, memorizes all its parents w.r.t. the different shapes under with it appears. */
	private void updateParentStructure (Map<TripleConstraint, Deque<Expression>> parentsMap) {
		for (Map.Entry<TripleConstraint, Deque<Expression>> e : parentsMap.entrySet()) {
			TripleConstraint tc = e.getKey();
			Deque<Expression> parents = e.getValue();

			while (! parents.isEmpty()) {
				Expression parent = parents.pop();
				Shape shape = (Shape) parents.pop();
				this.parentsMap.put(new Pair<>(tc, shape), parent);
			}
		}
	}
}