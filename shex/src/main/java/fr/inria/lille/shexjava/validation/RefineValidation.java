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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Value;

import fr.inria.lille.shexjava.graph.NeighborTriple;
import fr.inria.lille.shexjava.graph.RDFGraph;
import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.NodeConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeAnd;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExpr;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExprRef;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExternal;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeNot;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeOr;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExpr;
import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;
import fr.inria.lille.shexjava.util.Pair;

/** Implements the Refinement validation algorithm.
 * 
 * Refine validation systematically constructs a complete typing for all nodes in the graph and for a set of selected shape in the schema. See in typing for the selected shape.
 * It is therefore suited for cases when a complete typing is needed. The typing is computed at the first call of validate.
 * 
 * @author Jérémie Dusart
 * @author Iovka Boneva
 * @author Antonin Durey
 * 
 */
public class RefineValidation implements ValidationAlgorithm {
	private RDFGraph graph;
	private SORBEGenerator sorbeGenerator;
	private ShexSchema schema;
	private RefinementTyping typing = null;
	private Set<Label> extraShape;
	private DynamicCollectorOfTripleConstraint collectorTC;
	

	public RefineValidation(ShexSchema schema, RDFGraph graph) {
		super();
		this.graph = graph;
		this.schema = schema;
		this.sorbeGenerator = new SORBEGenerator();
		this.collectorTC = new DynamicCollectorOfTripleConstraint();
		this.extraShape=Collections.emptySet();
		this.extraShape=Collections.emptySet();
	}
	
	public RefineValidation(ShexSchema schema, RDFGraph graph,Set<Label> extraShape) {
		super();
		this.graph = graph;
		this.schema = schema;
		this.sorbeGenerator = new SORBEGenerator();
		this.collectorTC = new DynamicCollectorOfTripleConstraint();
		this.extraShape=extraShape;
	}
	
	@Override
	public Typing getTyping () {
		return typing;
	}
	
	/** Reset typing to null.
	 * 
	 */
	public void resetTyping() {
		this.typing = null;
	}
	
	@Override
	public boolean validate(Value focusNode, Label label)  throws Exception {
		if (typing == null) {
			this.typing = new RefinementTyping(schema, graph, extraShape);
			for (int stratum = 0; stratum < schema.getNbStratums(); stratum++) {
				typing.addAllLabelsFrom(stratum, focusNode);
							
				boolean changed;
				do {
					changed = false;
					Iterator<Pair<Value, Label>> typesIt = typing.typesIterator(stratum);
					while (typesIt.hasNext()) {
						Pair<Value, Label> nl = typesIt.next();
						
						if (! isLocallyValid(nl)) {
							typesIt.remove();
							changed = true;
						}
					}
				} while (changed);
			}
		}		
		if (focusNode==null || label==null)
			return false;
		if (!schema.getShapeMap().containsKey(label))
			throw new Exception("Unknown label: "+label);
		return typing.contains(focusNode, label);
	}

	
	private boolean isLocallyValid(Pair<Value, Label> nl) {
		EvaluateShapeExpressionVisitor visitor = new EvaluateShapeExpressionVisitor(nl.one);
		schema.getShapeMap().get(nl.two).accept(visitor);
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
		TripleExpr tripleExpression = this.sorbeGenerator.getSORBETripleExpr(shape);
		Iterator<NeighborTriple> tmp ;

		List<TripleConstraint> constraints = collectorTC.getResult(tripleExpression);
		if (constraints.size() == 0) {
			if (!shape.isClosed()) {
				return true;
			} else {
				tmp = graph.itOutNeighbours(node);
				if (! tmp.hasNext()) {
					return true;
				} else {
					return false;
				}
			}
		}
		
		Set<IRI> inversePredicate = new HashSet<IRI>();
		Set<IRI> forwardPredicate = new HashSet<IRI>();
		for (TripleConstraint tc:constraints) {
			if (tc.getProperty().isForward()) {
				forwardPredicate.add(tc.getProperty().getIri());
			}else {
				inversePredicate.add(tc.getProperty().getIri());
			}
		}
	
		List<NeighborTriple> neighbourhood = new ArrayList<NeighborTriple>();
		tmp = graph.itInNeighboursWithPredicate(node, inversePredicate);
		while(tmp.hasNext()) neighbourhood.add(tmp.next());
		if (shape.isClosed()) {
			tmp = graph.itOutNeighbours(node);
			while(tmp.hasNext()) neighbourhood.add(tmp.next());
		} else {
			tmp = graph.itOutNeighboursWithPredicate(node,forwardPredicate);
			while(tmp.hasNext()) neighbourhood.add(tmp.next());
		}
		
		Matcher matcher = new MatcherPredicateAndValue(this.getTyping()); 
		LinkedHashMap<NeighborTriple,List<TripleConstraint>> matchingTC = Matcher.collectMatchingTC(neighbourhood, constraints, matcher);
		// Check that the neighbor that cannot be match to a constraint are in extra
		Iterator<Map.Entry<NeighborTriple,List<TripleConstraint>>> iteMatchingTC = matchingTC.entrySet().iterator();
		while(iteMatchingTC.hasNext()) {
			Entry<NeighborTriple, List<TripleConstraint>> listTC = iteMatchingTC.next();
			if (listTC.getValue().isEmpty()) {
				if (! shape.getExtraProperties().contains(listTC.getKey().getPredicate())){
					return false;
				}
				iteMatchingTC.remove();
			}
		}

		// Create a BagIterator for all possible bags induced by the matching triple constraints
		List<List<TripleConstraint>> listMatchingTC = new ArrayList<List<TripleConstraint>>();
		for(NeighborTriple nt:matchingTC.keySet())
			listMatchingTC.add(matchingTC.get(nt));
		BagIterator bagIt = new BagIterator(listMatchingTC);

		IntervalComputation intervalComputation = new IntervalComputation(this.collectorTC);
		
		while(bagIt.hasNext()){
			Bag bag = bagIt.next();
			tripleExpression.accept(intervalComputation, bag, this);
			if (intervalComputation.getResult().contains(1)) {
				return true;
			}
		}

		return false;
	}	

}
