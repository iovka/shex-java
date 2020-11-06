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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.*;
import fr.inria.lille.shexjava.util.CommonGraph;
import fr.inria.lille.shexjava.util.Pair;


/** Implements the Recursive validation algorithm.
 * This algorithm will check only the shape definition necessary. The result is store in a shape map and will contain only the result for the (node, label) the validate function is called by the user. The result obtain  by recursive calls are not store since some can be false positive. To have the store of recursive call, see the algorithm RecursivveValidationWithMemorization.                
 * 
 * @author Jérémie Dusart 
 */
public class RecursiveValidation extends SORBEBasedValidation {
	
	private TypingForValidation typing;
	
	public RecursiveValidation(ShexSchema schema, Graph graph) {
		super(schema,graph);
	}
	
	
	protected boolean performValidation(RDFTerm focusNode, Label label) throws Exception {
		boolean result = recursiveValidation(focusNode,label);
		if (result) {
			this.typing.setStatus(focusNode, label, Status.CONFORMANT);
		} else {
			this.typing.setStatus(focusNode, label, Status.NONCONFORMANT);
		}
		return result;
	}
	
	
	protected boolean recursiveValidation(RDFTerm focusNode, Label label) throws Exception {
		this.typing.setStatus(focusNode, label, Status.CONFORMANT);
		EvaluateShapeExpressionVisitor visitor = new EvaluateShapeExpressionVisitor(focusNode);
		visitor.accept(schema.getShapeExprsMap().get(label));
		this.typing.removeNodeLabel(focusNode, label);
		return visitor.result;
		
	}
	
	@Override
	public Typing getTyping() {
		return typing;
	}
	
	@Override
	public void resetTyping() {
		this.typing = new TypingForValidation();
	}
	
	
	class EvaluateShapeExpressionVisitor  {
		private RDFTerm node; 
		private Boolean result;
		private List<Triple> neighbourhood = null;
		
		public EvaluateShapeExpressionVisitor(RDFTerm one) {
			this.node = one;
		}

		
		public Boolean getResult() {
			if (result == null) return false;
			return result;
		}
		
		public void accept(ShapeExpr expr) throws Exception {
			if (expr instanceof ShapeAnd)
				this.visitShapeAnd((ShapeAnd) expr);
			else if (expr instanceof ShapeOr)
				this.visitShapeOr((ShapeOr) expr);
			else if (expr instanceof ShapeNot)
				this.visitShapeNot((ShapeNot) expr);
			else if (expr instanceof Shape)
				this.visitShape((Shape) expr);
			else if (expr instanceof NodeConstraint)
				this.visitNodeConstraint((NodeConstraint) expr);
			else if (expr instanceof ShapeExprRef)
				this.visitShapeExprRef((ShapeExprRef) expr);
			else if (expr instanceof EmptyShape)
				this.visitEmptyShape((EmptyShape) expr);
			else if (expr instanceof ExtendsShapeExpr)
				this.visitExtendsShapeExpr((ExtendsShapeExpr) expr);
			else
				throw new IllegalArgumentException("EvaluateShapeExpressionVisitor.accept does not recognize " + expr.getClass().toString());
		}
		
		public void visitShapeAnd(ShapeAnd expr) throws Exception {
			for (ShapeExpr e : expr.getSubExpressions()) {
				this.accept(e);
				if (!result) break;
			}
		}

		public void visitShapeOr(ShapeOr expr) throws Exception {
			for (ShapeExpr e : expr.getSubExpressions()) {
				this.accept(e);
				if (result) break;
			}
		}
		
		public void visitShapeNot(ShapeNot expr) throws Exception {
			this.accept(expr.getSubExpression());
			result = !result;
		}
		
		public void visitShape(Shape expr) throws Exception {
			result = isLocallyValid(node, expr,neighbourhood);
		}

		public void visitNodeConstraint(NodeConstraint expr) {
			result = expr.contains(node);
			typing.setStatus(node, expr.getId(), result?Status.CONFORMANT:Status.NONCONFORMANT);
		}

		public void visitShapeExprRef(ShapeExprRef ref) throws Exception {
			this.accept(ref.getShapeDefinition());
		}

		public void visitEmptyShape(EmptyShape expr) throws Exception {
			result = true;
    }
		
		public void visitExtendsShapeExpr(ExtendsShapeExpr expr) throws Exception {
			List<Triple> oldNeigh = neighbourhood;
			BagExtendsIterator iter = getIteratorOfTheNeighbourhoodSplit(expr,neighbourhood,node);			
			while (iter.hasNext()) {
				Pair<List<Triple>, List<Triple>> nextSplit = iter.next();
				neighbourhood = nextSplit.one;
				this.accept(expr.getBaseShapeExpr());
				if (result) {
					neighbourhood = nextSplit.two;
					this.accept(expr.getExtension());
					if (result) {
						neighbourhood=oldNeigh;
						return;
					}
				}
			}			
			neighbourhood=oldNeigh;
		}
	}
	
	private BagExtendsIterator getIteratorOfTheNeighbourhoodSplit(ExtendsShapeExpr esexpr, List<Triple> neighbourhood,RDFTerm node) {
		List<Triple> baseNeigh = selectNeighbourhoodForExtendsValidation(neighbourhood,node);

		List<TripleConstraint> constraints = collectorTC.getTCs(esexpr.getBaseShapeExpr());
		List<Triple> neighBase = ValidationUtils.getMatchableNeighbourhood(baseNeigh,node, constraints, false);
		
		constraints = collectorTC.getTCs(esexpr.getExtension());
		List<Triple> neighExtension =  ValidationUtils.getMatchableNeighbourhood(baseNeigh, node, constraints, false);
		
		List<Triple> unmatchNeigh = baseNeigh.stream().filter(tr->!neighBase.contains(tr) && !neighExtension.contains(tr))
														  .collect(Collectors.toList());
		
		return new BagExtendsIterator(esexpr,unmatchNeigh,neighBase,neighExtension);			

	}
	
	private List<Triple> selectNeighbourhoodForExtendsValidation(List<Triple> neighbourhood,RDFTerm node){
		if (neighbourhood == null) {
			List<Triple> baseNeigh = graph.stream((BlankNodeOrIRI) node,null,null).collect(Collectors.toList());
			graph.stream(null,null,node).forEach(tr -> baseNeigh.add(tr));
			return baseNeigh ;
		} 
		return new ArrayList<>(neighbourhood);
	}
		
	
	private boolean isLocallyValid (RDFTerm node, Shape shape, List<Triple> baseNeighbourhood) throws Exception {
		TripleExpr tripleExpression = this.sorbeGenerator.getSORBETripleExpr(shape);

		List<TripleConstraint> constraints = collectorTC.getTCs(tripleExpression);	

		List<Triple> neighbourhood;
		if (baseNeighbourhood == null)
			neighbourhood = ValidationUtils.getMatchableNeighbourhood(graph, node, constraints, shape.isClosed());
		else
			neighbourhood = ValidationUtils.getMatchableNeighbourhood(baseNeighbourhood,  node, constraints, shape.isClosed());

		// Match using only predicate and recursive test. The following lines is the only big difference with refine validation. 
		TypingForValidation localTyping = new TypingForValidation();
		
		PreMatching preMatching = ValidationUtils.computePreMatching(node, neighbourhood, constraints, shape.getExtraProperties(), ValidationUtils.getPredicateOnlyMatcher());
		Map<Triple,List<TripleConstraint>> matchingTC1 = preMatching.getPreMatching();
			
		for(Entry<Triple,List<TripleConstraint>> entry:matchingTC1.entrySet()) {		
			for (TripleConstraint tc:entry.getValue()) {
				RDFTerm destNode = entry.getKey().getObject();
				if (!tc.getProperty().isForward())
					destNode = entry.getKey().getSubject();
				if (this.typing.getStatus(destNode, tc.getShapeExpr().getId()).equals(Status.NOTCOMPUTED)) {
					if (this.recursiveValidation(destNode, tc.getShapeExpr().getId())) 
						localTyping.setStatus(destNode, tc.getShapeExpr().getId(),Status.CONFORMANT);	
					else
						localTyping.setStatus(destNode, tc.getShapeExpr().getId(),Status.NONCONFORMANT);	
				} else {
					localTyping.setStatus(destNode, tc.getShapeExpr().getId(), typing.getStatus(destNode, tc.getShapeExpr().getId()));
				}

			}			
		}
					
		return this.findMatching(node, shape, localTyping, baseNeighbourhood).getMatching() != null;
	}	

	
	//----------------------------
	// Extends Util
	//----------------------------
	public static List<Triple> getMatchableNeighbourhood(Graph graph, RDFTerm node, List<TripleConstraint> tripleConstraints, boolean shapeIsClosed) {		
		Set<IRI> inversePredicate = new HashSet<>();
		Set<IRI> forwardPredicate = new HashSet<>();
		for (TripleConstraint tc : tripleConstraints)
			if (tc.getProperty().isForward())
				forwardPredicate.add(tc.getProperty().getIri());
			else
				inversePredicate.add(tc.getProperty().getIri());

		ArrayList<Triple> neighbourhood = new ArrayList<>();
		neighbourhood.addAll(CommonGraph.getInNeighboursWithPredicate(graph, node, inversePredicate));
		if (shapeIsClosed)
			neighbourhood.addAll(CommonGraph.getOutNeighbours(graph, node));
		else
			neighbourhood.addAll(CommonGraph.getOutNeighboursWithPredicate(graph, node,forwardPredicate));
		return neighbourhood;
	}
	
	
}
