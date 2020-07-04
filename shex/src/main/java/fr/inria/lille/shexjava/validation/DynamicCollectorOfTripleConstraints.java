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

import java.util.*;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.abstrsynt.*;
import fr.inria.lille.shexjava.schema.abstrsynt.visitors.CollectTripleConstraintsSE;
import fr.inria.lille.shexjava.schema.abstrsynt.visitors.CollectTripleConstraintsTE;
import fr.inria.lille.shexjava.schema.analysis.TripleExpressionVisitor;
import fr.inria.lille.shexjava.util.Pair;
import org.apache.commons.collections.map.HashedMap;

/** Allows to collect the triple constraints that appear in a shape and the shape structure w.r.t. EXTENDS.
 * Memorizes already computed results. 
 *
 * @author Iovka Boneva
 * @author Jérémie Dusart
 */
public class DynamicCollectorOfTripleConstraints {

	private Map<Object, List<TripleConstraint>> collectedTCs = new HashMap<>();
	private Map<Pair<TripleConstraint, Shape>, Object> parentsMap = new HashedMap();

	public Object getParentInShape (TripleConstraint tc, Shape shape) {
		return parentsMap.get(new Pair(tc, shape));
	}

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

	public List<TripleConstraint> getTCs (Shape shape) {
		List<TripleConstraint> tripleConstraints = collectedTCs.get(shape);

		boolean parentIsTrivial = shape.getExtended().isEmpty();
		Deque<Object> parents = null;
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

	private void updateParentStructure (List<TripleConstraint> tripleConstraints, Shape shape, TripleExpr commonParent) {
		for (TripleConstraint tc : tripleConstraints)
			this.parentsMap.put(new Pair(tc,shape), commonParent);
	}

	private void updateParentStructure (Map<TripleConstraint, Deque<Object>> parentsMap) {
		for (Map.Entry<TripleConstraint, Deque<Object>> e : parentsMap.entrySet()) {
			TripleConstraint tc = e.getKey();
			Deque<Object> parents = e.getValue();

			while (! parents.isEmpty()) {
				Object parent = parents.pop();
				Object shape = (Shape) parents.pop();
				this.parentsMap.put(new Pair(tc, shape), parent);
			}
		}
	}
}
