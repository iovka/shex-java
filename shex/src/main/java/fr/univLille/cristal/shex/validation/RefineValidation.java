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

import java.util.ArrayList;
import java.util.HashMap;
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
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExprRef;
import fr.univLille.cristal.shex.schema.analysis.CollectElementsFromShape;
import fr.univLille.cristal.shex.schema.analysis.CollectElementsFromTriple;
import fr.univLille.cristal.shex.schema.analysis.SchemaRulesStaticAnalysis;
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
	private ShexSchema originalSchema;
	private ShexSchema schemaSorbe;
	private Map<ShapeExprLabel, ShapeExpr> allRules;
	private RefinementTyping typing;
	private Map<TripleExpr,List<TripleConstraint>> collectionTripleConstraints;
	

	public RefineValidation(ShexSchema schema, RDFGraph graph) {
		super();
		this.graph = graph;
		this.originalSchema = schema;
		// TODO: implement conversion of original schema to a SORBE schema
		this.schemaSorbe = schema;
		this.collectionTripleConstraints = new HashMap<TripleExpr,List<TripleConstraint>>();
	}
	
	
	@Override
	public Typing getTyping () {
		return typing;
	}
	
	
	@Override
	public void validate(Value focusNode, ShapeExprLabel label) {
		//System.out.println(schemaSorbe.getShapeMap());
		this.typing = new RefinementTyping(schemaSorbe, graph);

		for (int stratum = 0; stratum < schemaSorbe.getNbStratums(); stratum++) {
			typing.addAllLabelsFrom(stratum, focusNode);
						
			boolean changed;
			do {
				changed = false;
				Iterator<Pair<Value, ShapeExprLabel>> typesIt = typing.typesIterator(stratum);
				while (typesIt.hasNext()) {
					Pair<Value, ShapeExprLabel> nl = typesIt.next();
					if (! isLocallyValid(nl)) {
						typesIt.remove();
						changed = true;
					}
				}
			} while (changed);
		}
	}

	
	private boolean isLocallyValid(Pair<Value, ShapeExprLabel> nl) {
		EvaluateShapeExpressionVisitor visitor = new EvaluateShapeExpressionVisitor(nl.one);
		schemaSorbe.getShapeMap().get(nl.two).accept(visitor);
		return visitor.getResult();
	}
	
	class EvaluateShapeExpressionVisitor extends ShapeExpressionVisitor<Boolean> {
		
		private Value node; 
		private Boolean result;
		
		public EvaluateShapeExpressionVisitor(Value one) {
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
		TripleExpr tripleExpression = shape.getTripleExpression();
				
		List<NeighborTriple> neighbourhood = graph.listAllNeighbours(node);
		List<TripleConstraint> constraints = this.getAllTripleConstraints(tripleExpression);
			
//		System.out.println("IsLocallyValid: ("+node+','+shape.getId()+")");
//		System.out.println("Neighbourhood: "+neighbourhood);
//		System.out.println("List of TC("+constraints.size()+"): "+constraints);

		Matcher matcher = new PredicateAndValueMatcher(this.getTyping()); 
//		Matcher matcher = new PredicateOnlyMatcher(); 
//		Matcher matcher = new PredicateAndShapeRefAndNodeConstraintsOnLiteralsMatcher(typing); 
		
		List<List<TripleConstraint>> matchingTC = Matcher.collectMatchingTC(neighbourhood, constraints, matcher);
		// Drop neighbor that cannot be match to any constraint. If shape is closed then return false.
		Iterator<List<TripleConstraint>> iteMatchingTC = matchingTC.iterator();
		while(iteMatchingTC.hasNext()) {
			List<TripleConstraint> listTC = iteMatchingTC.next();
			if (listTC.isEmpty()) {
				if (shape.isClosed()) {
					return false;
				}
				iteMatchingTC.remove();
			}
		}
		//System.out.println("MatchingTC:"+matchingTC);
		
		// Create a BagIterator for all possible bags induced by the matching triple constraints
		BagIterator bagIt = new BagIterator(matchingTC);
		
		IntervalComputation intervalComputation = new IntervalComputation();
		
		while(bagIt.hasNext()){
			Bag bag = bagIt.next();
			//System.out.println("Bag: "+bag);
			tripleExpression.accept(intervalComputation, bag);
			if (intervalComputation.getResult().contains(1))
				return true;
		}
		//System.out.println();
		return false;
	}
	
	
	// collect all triple constraint in a triple expression and store it to reuse later
	private List<TripleConstraint> getAllTripleConstraints (TripleExpr tripleExpression) {
		if (this.collectionTripleConstraints.containsKey(tripleExpression))
			return this.collectionTripleConstraints.get(tripleExpression);
		
		Set<TripleConstraint> set = new HashSet<TripleConstraint>();
		CollectElementsFromTriple<TripleConstraint> collector = 
				new CollectElementsFromTriple<TripleConstraint>((Object ast) -> (ast instanceof TripleConstraint), 
						                   set,
						                   true);
		tripleExpression.accept(collector);
		
		List<TripleConstraint> result = new ArrayList<TripleConstraint>();
		for (TripleExpr trexpr:set)
			result.add((TripleConstraint) trexpr);
		
		this.collectionTripleConstraints.put(tripleExpression,result);
		return result;
	}
	

}
