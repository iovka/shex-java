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

package fr.univLille.cristal.shex.validation;

import java.util.Iterator;
import java.util.List;

import org.eclipse.rdf4j.model.Resource;

import fr.univLille.cristal.shex.graph.NeighborTriple;
import fr.univLille.cristal.shex.graph.RDFGraph;
import fr.univLille.cristal.shex.schema.abstrsynt.NeighbourhoodConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.NodeConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAndExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpression;
import fr.univLille.cristal.shex.schema.ShapeLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNotExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOrExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeRef;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpression;
import fr.univLille.cristal.shex.schema.analysis.SchemaRulesInstrumentations;
import fr.univLille.cristal.shex.schema.analysis.ShapeExpressionVisitor;
import fr.univLille.cristal.shex.util.Pair;

/** Implements the Refinement validation algorithm.
 * 
 * Refine validation systematically constructs a complete typing for all nodes in the graph and for all shape labels in the schema.
 * It is therefore suited for cases when a complete typing is needed.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public class RefineValidation implements ValidationAlgorithm {

	private RDFGraph graph;
	private ShexSchema schema;
	private RefinementTyping typing;
	private static final Object TRIPLE_CONSTR_LIST__ATTR_KEY = new Object();
	private static final Object NEIGH_CONSTR_USES_INVERSE_PROPERTIES__ATTR_KEY = new Object();
	private static final Object UNFOLDED_TRIPLE_EXPR_ON_NEIGH_CONSTR__ATTR_KEY = new Object();
	
	public RefineValidation(ShexSchema schema, RDFGraph graph) {
		super();
		this.graph = graph;
		this.schema = schema;
		SchemaRulesInstrumentations.setUsesInversePropertiesOnNeighbourhoodConstraints(schema.getRules(), NEIGH_CONSTR_USES_INVERSE_PROPERTIES__ATTR_KEY);
		SchemaRulesInstrumentations.setUnfoldedRepetitions(schema.getRules(), UNFOLDED_TRIPLE_EXPR_ON_NEIGH_CONSTR__ATTR_KEY);
		SchemaRulesInstrumentations.computeAndSetTripleConstraintsOnUnfoldedTripleExpressions(schema.getRules(), TRIPLE_CONSTR_LIST__ATTR_KEY, UNFOLDED_TRIPLE_EXPR_ON_NEIGH_CONSTR__ATTR_KEY);

	}
	
	@Override
	public Typing getTyping () {
		return typing;
	}
	
	@Override
	public void validate(Resource focusNode, ShapeLabel label) {
		this.typing = new RefinementTyping(schema, graph);

		for (int stratum = 0; stratum < schema.getNbStratums(); stratum++) {
			typing.addAllLabelsFrom(stratum, focusNode);
				
			boolean changed;
			do {
				changed = false;
				Iterator<Pair<Resource, ShapeLabel>> typesIt = typing.typesIterator(stratum);
				while (typesIt.hasNext()) {
					Pair<Resource, ShapeLabel> nl = typesIt.next();
					if (! isLocallyValid(nl)) {
						typesIt.remove();
						changed = true;
					}
				}
			} while (changed);
		}
	}

	
	private boolean isLocallyValid(Pair<Resource, ShapeLabel> nl) {
		EvaluateShapeExpressionOnNonLiteralVisitor visitor = new EvaluateShapeExpressionOnNonLiteralVisitor(nl.one);
		schema.getRules().getRule(nl.two).expression.accept(visitor);
		return visitor.getResult();
	}
	
	class EvaluateShapeExpressionOnNonLiteralVisitor extends ShapeExpressionVisitor<Boolean> {
		
		private Resource node; 
		private Boolean result;
		
		public EvaluateShapeExpressionOnNonLiteralVisitor(Resource node) {
			this.node = node;
		}

		@Override
		public Boolean getResult() {
			if (result == null) return false;
			return result;
		}
		
		@Override
		public void visitShapeAnd(ShapeAndExpression expr, Object... arguments) {
			for (ShapeExpression e : expr.getSubExpressions()) {
				e.accept(this);
				if (!result) break;
			}
		}

		@Override
		public void visitShapeOr(ShapeOrExpression expr, Object... arguments) {
			for (ShapeExpression e : expr.getSubExpressions()) {
				e.accept(this);
				if (result) break;
			}
		}
		
		@Override
		public void visitShapeNot(ShapeNotExpression expr, Object... arguments) {
			expr.getSubExpression().accept(this);
			result = !result;
		}
		
		@Override
		public void visitNeighbourhoodConstraint(NeighbourhoodConstraint expr, Object... arguments) {
			result = isLocallyValid(node, expr);
		}

		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
			result = expr.contains(node);
		}

		@Override
		public void visitShapeRef(ShapeRef ref, Object[] arguments) {
			result = typing.contains(node, ref.getLabel());
		}
	}

	
	private boolean isLocallyValid (Resource node, NeighbourhoodConstraint nc) {
		
		TripleExpression tripleExpression = (TripleExpression) nc.getAttributes().getAttribute(UNFOLDED_TRIPLE_EXPR_ON_NEIGH_CONSTR__ATTR_KEY);
		List<NeighborTriple> neighbourhood = getNeighbourhood(node, nc);
		@SuppressWarnings("unchecked")
		List<TripleConstraint> constraints = (List<TripleConstraint>) tripleExpression.getAttributes().getAttribute(TRIPLE_CONSTR_LIST__ATTR_KEY);
		
		Matcher matcher = new PredicateAndShapeRefAndNodeConstraintsOnLiteralsMatcher(typing); 
		
		List<List<TripleConstraint>> matchingTC = Matcher.collectMatchingTC(neighbourhood, constraints, matcher);
		
		// Create a BagIterator for all possible bags induced by the matching triple constraints
		BagIterator bagIt = new BagIterator(matchingTC);
		
		IntervalComputation intervalComputation = new IntervalComputation(TRIPLE_CONSTR_LIST__ATTR_KEY);
		
		while(bagIt.hasNext()){
			Bag bag = bagIt.next();
			tripleExpression.accept(intervalComputation, bag);
			if (intervalComputation.getResult().contains(1))
				return true;
		}

		return false;
	}

	private List<NeighborTriple> getNeighbourhood(Resource node, NeighbourhoodConstraint nc) {
		if ((Boolean) (nc.getAttributes().getAttribute(NEIGH_CONSTR_USES_INVERSE_PROPERTIES__ATTR_KEY)))
			return graph.listAllNeighbours(node);
		else 
			return graph.listOutNeighbours(node);
	}	
}
