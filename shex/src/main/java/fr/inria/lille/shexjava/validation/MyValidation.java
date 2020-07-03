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

import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.*;
import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import java.util.List;
import java.util.Map;

/**
 * @author Iovka Boneva
 */
public class MyValidation {

	protected Graph graph;
	protected ShexSchema schema;
	protected DynamicCollectorOfTripleConstraints collectorTC;
	protected SORBEGenerator sorbeGenerator;

	/** Checks whether the set of triples satisfies a triple expression with a given typing.
	 * The typing must be complete for all opposite nodes of the triples and all shapes that are involved in the triple constraints.
	 *
	 * @param triples
	 * @param focusNode
	 * @param tripleExpr
	 * @param typing
	 * @return
	 */
	private boolean matches (List<Triple> triples,
							 RDFTerm focusNode,
							 TripleExpr tripleExpr,
							 Typing typing) {

		// Enumerate all possible matchings from the triples to the triple constraints and look for a valid matching among them

		TripleExpr sorbeTripleExpr = this.sorbeGenerator.getSORBETripleExpr(tripleExpr); // FIXME
		List<TripleConstraint> tripleConstraints = collectorTC.getTCs(sorbeTripleExpr);
		Map<Triple, List<TripleConstraint>> matchingTriples =
				ValidationUtils.computePreMatching(triples, focusNode, tripleConstraints, typing, ValidationUtils.predicateAndValueMatcher);
		MyMatchingsIterator mit = new MyMatchingsIterator(matchingTriples);
		MyMatching result = null;
		while (result == null && mit.hasNext()) {
			MyMatching matching = mit.next();
			if (isLocallyValid(matching, sorbeTripleExpr)) {
				result = matching;
			}
		}
		// TODO we might want to use the matching, in this case it should be translated to a matching on the non sorbe triple expression
		return result != null;
	}

	/** Checks whether the set of triples satisfies a shape expression with a given typing.
	 * The typing must be complete for all opposite nodes of the triples and all shapes that are involved in the triple constraints.
	 *
	 * @param triples
	 * @param focusNode
	 * @param shapeExpr
	 * @param typing
	 * @return
	 */
	private boolean satisfies (List<Triple> triples,
							   RDFTerm focusNode,
							   ShapeExpr shapeExpr,
							   Typing typing) {

		EvaluateShapeExprOnNeighbourhoodVisitor eval = new EvaluateShapeExprOnNeighbourhoodVisitor(triples, focusNode, typing);
		shapeExpr.accept(eval);
		return eval.getResult();
	}

	private boolean satisfies (RDFTerm focusNode, Shape shape, Typing typing) {
		// Get the neighbourhood
		List<TripleConstraint> tripleConstraints = null; // FIXME
		//List<Triple> neighbourhood = ValidationUtils.getMatchableNeighbourhood(graph, focusNode, constraints, shape.isClosed());

		// Split the neighbourhood between matching, extra and unmatched

		// Split between the different components of shape

		// call satisfies(neighbourhood) on all the components

		throw new UnsupportedOperationException("not yet implemented");
	}


	/** Tests whether the matching satisfies the triple expression locally, w/o inspecting whether each of the triple's opposite node satisfies
	 * the value of the triple constraint. */
	private boolean isLocallyValid (MyMatching matching, TripleExpr sorbeTripleExpr) {
		IntervalComputation intervalComputation = new IntervalComputation(collectorTC);
		sorbeTripleExpr.accept(intervalComputation, matching.toBag());
		return intervalComputation.getResult().contains(1);
	}

	abstract class AbstractEvaluateShapeExprVistor extends ShapeExpressionVisitor<Boolean> {

		protected final RDFTerm focusNode;
		protected Boolean result = null;

		AbstractEvaluateShapeExprVistor(RDFTerm focusNode) {
			this.focusNode = focusNode;
		}

		@Override
		public Boolean getResult() {
			return result;
		}

		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
			result = expr.contains(focusNode);
		}

		@Override
		public void visitShapeExprRef(ShapeExprRef shapeRef, Object... arguments) {
			shapeRef.getShapeDefinition().accept(this, arguments);
		}

		@Override
		public void visitShapeAnd(ShapeAnd expr, Object... arguments) {
			for (ShapeExpr e : expr.getSubExpressions()) {
				e.accept(this, arguments);
				if (!result) break;
			}
		}

		@Override
		public void visitShapeOr(ShapeOr expr, Object... arguments) {
			for (ShapeExpr e : expr.getSubExpressions()) {
				e.accept(this, arguments);
				if (result) break;
			}
		}

		@Override
		public void visitShapeNot(ShapeNot expr, Object... arguments) {
			expr.getSubExpression().accept(this);
			result = !result;
		}
	}

	class EvaluateShapeExprOnNeighbourhoodVisitor extends AbstractEvaluateShapeExprVistor {

		private List<Triple> neighbourhood;
		private Typing typing;

		public EvaluateShapeExprOnNeighbourhoodVisitor(List<Triple> neighbourhood, RDFTerm focusNode, Typing typing) {
			super(focusNode);
			this.neighbourhood = neighbourhood;
			this.typing = typing;
		}
		@Override
		public void visitShape(Shape expr, Object... arguments) {
			// The closed and extra qualifiers are ignored here
			result = matches(neighbourhood, focusNode, expr.getTripleExpression(), typing);
		}
	}

	class EvaluateShapeExprOnNodeVisitor extends AbstractEvaluateShapeExprVistor {

		EvaluateShapeExprOnNodeVisitor(RDFTerm focusNode) {
			super(focusNode);
		}

		@Override
		public void visitShape(Shape expr, Object... arguments) {
			throw new UnsupportedOperationException("not yet implemented");
		}
	}

}
