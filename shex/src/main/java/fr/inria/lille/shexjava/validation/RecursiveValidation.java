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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

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
import fr.inria.lille.shexjava.schema.abstrsynt.TCProperty;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExpr;
import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;
import fr.inria.lille.shexjava.util.CommonGraph;
import fr.inria.lille.shexjava.util.Pair;


/** Implements the Recursive validation algorithm.
 * This algorithm will check only the shape definition necessary, but can return false positive.
 * 
 * @author Jérémie Dusart 
 */
public class RecursiveValidation implements ValidationAlgorithm {
	private Graph graph;
	private SORBEGenerator sorbeGenerator;
	private ShexSchema schema;
	private Typing typing;
	
	private DynamicCollectorOfTripleConstraint collectorTC;
	
	
	public RecursiveValidation(ShexSchema schema, Graph graph) {
		super();
		this.graph = graph;
		this.sorbeGenerator = new SORBEGenerator();
		this.schema = schema;
		this.collectorTC = new DynamicCollectorOfTripleConstraint();
		this.typing = new Typing();
	}
	
	public void resetTyping() {
		this.typing = new Typing();
	}
	
	@Override
	public Typing getTyping() {
		return typing;
	}	
	
	@Override
	public boolean validate(RDFTerm focusNode, Label label) throws Exception {
		if (label == null || !schema.getShapeMap().containsKey(label))
			throw new Exception("Unknown label: "+label);
		this.resetTyping();
		boolean result = recursiveValidation(focusNode,label);
		if (result) {
			this.typing.setStatus(focusNode, label, TypingStatus.CONFORMANT);
		}
		return result;
	}
	
	protected boolean recursiveValidation(RDFTerm focusNode, Label label) {
		this.typing.setStatus(focusNode, label, TypingStatus.CONFORMANT);
		EvaluateShapeExpressionVisitor visitor = new EvaluateShapeExpressionVisitor(focusNode);
		schema.getShapeMap().get(label).accept(visitor);
		this.typing.removeNodeLabel(focusNode, label);
		return visitor.result;
		
	}
	
	class EvaluateShapeExpressionVisitor extends ShapeExpressionVisitor<Boolean> {
		private RDFTerm node; 
		private Boolean result;
		
		public EvaluateShapeExpressionVisitor(RDFTerm one) {
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
			ref.getShapeDefinition().accept(this);
		}

		@Override
		public void visitShapeExternal(ShapeExternal shapeExt, Object[] arguments) {
			throw new UnsupportedOperationException("Not yet implemented.");
		}
	}
	
	
	private boolean isLocallyValid (RDFTerm node, Shape shape) {
		TripleExpr tripleExpression = this.sorbeGenerator.getSORBETripleExpr(shape);

		List<TripleConstraint> constraints = collectorTC.getResult(tripleExpression);
		if (constraints.size() == 0) {
			if (!shape.isClosed())
				return true;
			else {
				if (CommonGraph.getOutNeighbours(graph, node).size()==0)
					return true;
				 else
					return false;
			}
		}
		
		
		Set<IRI> inversePredicate = new HashSet<IRI>();
		Set<IRI> forwardPredicate = new HashSet<IRI>();
		for (TripleConstraint tc:constraints)
			if (tc.getProperty().isForward())
				forwardPredicate.add(tc.getProperty().getIri());
			else
				inversePredicate.add(tc.getProperty().getIri());

		
		ArrayList<Triple> neighbourhood = new ArrayList<>();
		neighbourhood.addAll(CommonGraph.getInNeighboursWithPredicate(graph, node, inversePredicate));
		if (shape.isClosed())
			neighbourhood.addAll(CommonGraph.getOutNeighbours(graph, node));
		else
			neighbourhood.addAll(CommonGraph.getOutNeighboursWithPredicate(graph, node,forwardPredicate));


		// Match using only predicate and recursive test. The following line are the only big difference with refine validation
		Set<Pair<RDFTerm, Label>> dependencies = new HashSet<Pair<RDFTerm, Label>>();
		Matcher matcher = new MatcherPredicateOnly();
		LinkedHashMap<Triple,List<TripleConstraint>> matchingTC1 = matcher.collectMatchingTC(node, neighbourhood, constraints);	

		for(Entry<Triple,List<TripleConstraint>> entry:matchingTC1.entrySet()) {
			if (entry.getValue().isEmpty()) {
				boolean success = false;
				for (TCProperty extra : shape.getExtraProperties())
					if (extra.getIri().equals(entry.getKey().getPredicate()))
						success = true;
				if (!success) {
					cleanTyping(dependencies);
					return false;
				}
			}
			
			for (TripleConstraint tc:entry.getValue()) {
				RDFTerm destNode = entry.getKey().getObject();
				if (!tc.getProperty().isForward())
					destNode = entry.getKey().getSubject();
	
				if (this.typing.getStatus(destNode, tc.getShapeExpr().getId()).equals(TypingStatus.NOTCOMPUTED)) {
					dependencies.add(new Pair<>(destNode, tc.getShapeExpr().getId()));
					if (this.recursiveValidation(destNode, tc.getShapeExpr().getId())) 
						typing.setStatus(destNode, tc.getShapeExpr().getId(),TypingStatus.CONFORMANT);	
					else
						typing.setStatus(destNode, tc.getShapeExpr().getId(),TypingStatus.NONCONFORMANT);	
				}
			}

		}
		
		// end of the big diference with refine
		
		// Add the detected node value to the typing
		Matcher matcher2 = new MatcherPredicateAndValue(this.getTyping()); 
		LinkedHashMap<Triple,List<TripleConstraint>> matchingTC2 = matcher2.collectMatchingTC(node, neighbourhood, constraints);

		// Check that the neighbor that cannot be match to a constraint are in extra
		Iterator<Map.Entry<Triple,List<TripleConstraint>>> iteMatchingTC = matchingTC2.entrySet().iterator();
		while(iteMatchingTC.hasNext()) {
			Entry<Triple, List<TripleConstraint>> listTC = iteMatchingTC.next();
			if (listTC.getValue().isEmpty()) {
				boolean success = false;
				for (TCProperty extra : shape.getExtraProperties())
					if (extra.getIri().equals(listTC.getKey().getPredicate()))
						success = true;
				if (!success) {
					cleanTyping(dependencies);
					return false;
				}
				iteMatchingTC.remove();
			}
		}
		
		// Create a BagIterator for all possible bags induced by the matching triple constraints
		ArrayList<List<TripleConstraint>> listMatchingTC = new ArrayList<List<TripleConstraint>>();
		for(Triple nt:matchingTC2.keySet())
			listMatchingTC.add(matchingTC2.get(nt));
		
		BagIterator bagIt = new BagIterator(neighbourhood,listMatchingTC);

		IntervalComputation intervalComputation = new IntervalComputation(this.collectorTC);
		
		while(bagIt.hasNext()){
			Bag bag = bagIt.next();
			tripleExpression.accept(intervalComputation, bag, this);
			if (intervalComputation.getResult().contains(1)) {
				List<Pair<Triple,Label>> result = new ArrayList<Pair<Triple,Label>>();
				for (Pair<Triple,Label> pair:bagIt.getCurrentBag())
					result.add(new Pair<Triple,Label>(pair.one,SORBEGenerator.removeSORBESuffixe(pair.two)));
				typing.setMatch(node, shape.getId(), result);
				cleanTyping(dependencies);
				return true;
			}
		}
		cleanTyping(dependencies);
		
		return false;
	}	

	// Typing util
	
	public void cleanTyping(Set<Pair<RDFTerm, Label>> dependencies) {
		for (Pair<RDFTerm, Label> pair:dependencies) {
			this.typing.removeNodeLabel(pair.one, pair.two);
		}
	}
}
