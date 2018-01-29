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

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.graph.NeighborTriple;
import fr.univLille.cristal.shex.graph.RDFGraph;
import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.NodeConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAnd;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNot;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExternal;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;
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
	private Map<ShapeExprLabel, ShapeExpr> allRules;
	private RefinementTyping typing;
	
	// FIXME: apply the new kind of instrumentations, and use them
	public RefineValidation(ShexSchema schema, RDFGraph graph) {
		super();
		this.graph = graph;
		this.schema = schema;
//		// I believe that the change that I performed in the schema make the following useless
//		// Maybe not since additionnal rules are only the roots...
//		InstrumentationAdditionalShapeDefinitions.getInstance().apply(schema);
//		// The following compute if a shape contains an inverse property
//		InstrumentationUsesInversePropertiesOnShapes.getInstance().apply(schema);
//		// Collect all the rules
//		allRules = (Map<ShapeExprLabel, ShapeExpr>) schema.getDynamicAttributes().get(InstrumentationAdditionalShapeDefinitions.getInstance().getKey());
//		InstrumentationShapesWithTripleExressionsForValidation.getInstance().apply(allRules);
//		
//		
//		// collect all the shape that have a triple expression
//		// -> enumerate all shape and compute if type is shape
//		// The goal is easy acces to all the TC definition but we lose the structure
//		Set<Shape> allShapes = new HashSet<>();
//		CollectASTElementsS<Shape> collector = new CollectASTElementsS<Shape>((ASTElement ast) -> ast.getClass().equals(Shape.class), 
//																					allShapes, false);
//		//For each triple expression compute the list of dependent tripleconstraint
//		for (Shape shape : collector.getResult()) {
//			InstrumentationMentionedPropertiesOnShapes.getInstance().apply(shape);
//			TripleExpr texpr = shape.getTripleExpression();
//			ComputeListsOfTripleConstraintsVisitor visitor = new ComputeListsOfTripleConstraintsVisitor();
//			texpr.accept(visitor);
//			Map<TripleExpr, List<TripleConstraint>> map = visitor.getResult();
//			InstrumentationListsOfTripleConstraintsOnTripleExpressions.getInstance().apply(texpr, map);			
//		}
	}
	
	@Override
	public Typing getTyping () {
		return typing;
	}
	
	@Override
	public void validate(Value focusNode, ShapeExprLabel label) {
		this.typing = new RefinementTyping(schema, graph);

		for (int stratum = 0; stratum < schema.getNbStratums(); stratum++) {
			typing.addAllLabelsFrom(stratum, focusNode);
						
			boolean changed;
			do {
				changed = false;
				Iterator<Pair<Value, ShapeExprLabel>> typesIt = typing.typesIterator(stratum);
				while (typesIt.hasNext()) {
					Pair<Value, ShapeExprLabel> nl = typesIt.next();
					System.out.println("Testing: "+nl);
//					if (! isLocallyValid(nl)) {
//						typesIt.remove();
//						changed = true;
//					}
				}
			} while (changed);
		}
	}

	
	private boolean isLocallyValid(Pair<Value, ShapeExprLabel> nl) {
		EvaluateShapeExpressionOnNonLiteralVisitor visitor = new EvaluateShapeExpressionOnNonLiteralVisitor(nl.one);
		schema.getRules().get(nl.two).accept(visitor);
		return visitor.getResult();
	}
	
	class EvaluateShapeExpressionOnNonLiteralVisitor extends ShapeExpressionVisitor<Boolean> {
		
		private Value node; 
		private Boolean result;
		
		public EvaluateShapeExpressionOnNonLiteralVisitor(Value one) {
			this.node = one;
		}

		@Override
		public Boolean getResult() {
			if (result == null) return false;
			return result;
		}
		
		@Override
		public void visitShapeAnd(ShapeAnd expr, Object... arguments) {
			for (ShapeExpr e : expr.getSubExpressions()) {
				e.accept(this);
				if (!result) break;
			}
		}

		@Override
		public void visitShapeOr(ShapeOr expr, Object... arguments) {
			for (ShapeExpr e : expr.getSubExpressions()) {
				e.accept(this);
				if (result) break;
			}
		}
		
		@Override
		public void visitShapeNot(ShapeNot expr, Object... arguments) {
			expr.getSubExpression().accept(this);
			result = !result;
		}
		
		@Override
		public void visitShape(Shape expr, Object... arguments) {
			result = isLocallyValid(node, expr);
		}

		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
			result = expr.contains(node);
		}

		@Override
		public void visitShapeExprRef(ShapeExprRef ref, Object[] arguments) {
			result = typing.contains(node, ref.getLabel());
		}

		@Override
		public void visitShapeExternal(ShapeExternal shapeExt, Object[] arguments) {
			throw new UnsupportedOperationException("Not yet implemented.");
		}
	}
	
	private boolean isLocallyValid (Value node, Shape shape) {
		
//		NonRefTripleExpr tripleExpression = (NonRefTripleExpr) shape.getDynamicAttributes().get(InstrumentationShapesWithTripleExressionsForValidation.getInstance().getKey());
//		List<NeighborTriple> neighbourhood = getNeighbourhood(node, shape);
//		@SuppressWarnings("unchecked")
//		List<TripleConstraint> constraints = (List<TripleConstraint>) tripleExpression.getDynamicAttributes().get(InstrumentationListsOfTripleConstraintsOnTripleExpressions.getInstance().getKey());
//		
//		Matcher matcher = new PredicateAndShapeRefAndNodeConstraintsOnLiteralsMatcher(typing); 
//		
//		List<List<TripleConstraint>> matchingTC = Matcher.collectMatchingTC(neighbourhood, constraints, matcher);
//		
//		// Create a BagIterator for all possible bags induced by the matching triple constraints
//		BagIterator bagIt = new BagIterator(matchingTC);
//		
//		IntervalComputation intervalComputation = new IntervalComputation();
//		
//		while(bagIt.hasNext()){
//			Bag bag = bagIt.next();
//			tripleExpression.accept(intervalComputation, bag);
//			if (intervalComputation.getResult().contains(1))
//				return true;
//		}

		return false;
	}

//	private List<NeighborTriple> getNeighbourhood(Resource node, Shape shape) {
//		if ((Boolean) (shape.getDynamicAttributes().get(InstrumentationUsesInversePropertiesOnShapes.getInstance().getKey())))
//			return graph.listAllNeighbours(node);
//		else 
//			return graph.listOutNeighbours(node);
//	}	
}
