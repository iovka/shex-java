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
import fr.inria.lille.shexjava.schema.abstrsynt.EachOf;
import fr.inria.lille.shexjava.schema.abstrsynt.EmptyTripleExpression;
import fr.inria.lille.shexjava.schema.abstrsynt.OneOf;
import fr.inria.lille.shexjava.schema.abstrsynt.RepeatedTripleExpression;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExpr;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExprRef;
import fr.inria.lille.shexjava.schema.abstrsynt.visitors.CollectTripleConstraintsTE;
import fr.inria.lille.shexjava.schema.analysis.TripleExpressionVisitor;

/** Allows to compute the triple constraints that appear in a shape.
 * Memorizes already computed results. 
 *
 * @author Iovka Boneva
 * @author Jérémie Dusart
 */
public class DynamicCollectorOfTripleConstraints {

	private Map<Label, List<TripleConstraint>> collectedTCs = new HashMap<>();
	
	public List<TripleConstraint> getTCs (TripleExpr texpr) {
		List<TripleConstraint> result = collectedTCs.get(texpr.getId());
		if (result == null) {
			CollectTripleConstraintsTE collector = new CollectTripleConstraintsTE();
			texpr.accept(collector);
			result = collector.getResult();
			collectedTCs.put(texpr.getId(), result);
		}
		return result;
	}
}
