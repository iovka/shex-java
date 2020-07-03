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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.NodeConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeAnd;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExpr;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExprRef;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeNot;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeOr;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExpr;
import fr.inria.lille.shexjava.util.CommonGraph;
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
/**
 * @author jdusart
 *
 */
public class RefineValidation extends SORBEBasedValidation {
	private boolean computed = false;
	private TypingForRefineValidation typing;

	
	public RefineValidation(ShexSchema schema, Graph graph) {
		super(schema,graph);
	}

	@Override
	public Typing getTyping() {
		return typing;
	}
	
	@Override
	public void resetTyping() {
		this.typing = new TypingForRefineValidation(CommonGraph.getAllNodes(graph), schema);
		computed = false;
	}
	
	/** (non-Javadoc)
	 * @throws Exception 
	 * @see fr.inria.lille.shexjava.validation.ValidationAlgorithm#validate(org.apache.commons.rdf.api.RDFTerm, fr.inria.lille.shexjava.schema.Label)
	 */
	public void validate() {
		computeMaximalTyping(null);
	}
	
	
	protected boolean performValidation(RDFTerm focusNode, Label label) {
		computeMaximalTyping(focusNode);
		return typing.isConformant(focusNode, label);
	}

	private void computeMaximalTyping(RDFTerm focusNode) {
		if (computed)
			return;
		// Compute the maximal typing for the shapes only
		for (int stratum = 0; stratum < schema.getStratification().size(); stratum++) {
			typing.startStratum(schema.getStratification().get(stratum));
			boolean changed;
			do {
				changed = false;
				Iterator<Pair<RDFTerm, Label>> typesIt = typing.currentStratumNodeShapeLabelPairsIterator();
				while (typesIt.hasNext()) {
					Pair<RDFTerm, Label> nl = typesIt.next();
					if (! satisfies(nl,true)) {
						typesIt.remove();
						changed = true;
					}
				}
			} while (changed);
			typing.endStratum();
		}
		// Populate the typing with all the labels from the schema
		// TODO this could also be done in a stratified way
		for (Label label:schema.getShapeExprsMap().keySet()) {
			for (RDFTerm node : CommonGraph.getAllNodes(graph)) {
				typing.setNonShapeLabelStatus(node, label,
						satisfies(new Pair<>(node, label),false)
							? Status.CONFORMANT : Status.NONCONFORMANT
				);
			}
		}
		computed = true;
	}

	/** Tests whether the node satisfies the shape expresion with specified label and with the current typing 
	 *  If validateShape is set to true, then the typing will not be used
	 *  */
	private boolean satisfies(Pair<RDFTerm, Label> nl, boolean validateShape) {
		EvaluateShapeExpressionVisitor shexprEvaluator = new EvaluateShapeExpressionVisitor(validateShape, nl.one);
		schema.getShapeExprsMap().get(nl.two).accept(shexprEvaluator);
		return shexprEvaluator.getResult();
	}

	/** Tests whether the node's neighbourhood matches the shape with the current typing */
	private boolean matches (RDFTerm node, Shape shape) {
		// Since the algorithm first computing the typing with the shape only, in the same fashion as for the recursive algorithm, a localtyping must be computed without any cal to compute shape.
		TripleExpr tripleExpression = this.sorbeGenerator.getSORBETripleExpr(shape.getTripleExpression());
		List<TripleConstraint> constraints = collectorTC.getTCs(tripleExpression);	
		List<Triple> neighbourhood = ValidationUtils.getMatchableNeighbourhood(graph, node, constraints, shape.isClosed());

		// Match using only predicate and recursive test. The following lines is the only big difference with refine validation. 
		TypingForValidation localTyping = new TypingForValidation();
		
		PreMatching preMatching = ValidationUtils.computePreMatching(node, neighbourhood, constraints, shape.getExtraProperties(), null, ValidationUtils.getPredicateOnlyMatcher());
		Map<Triple,List<TripleConstraint>> matchingTC1 = preMatching.getPreMatching();
			
		for(Entry<Triple,List<TripleConstraint>> entry:matchingTC1.entrySet()) {		
			for (TripleConstraint tc:entry.getValue()) {
				RDFTerm destNode = entry.getKey().getObject();
				if (!tc.getProperty().isForward())
					destNode = entry.getKey().getSubject();
	
				if (this.typing.getStatus(destNode, tc.getShapeExpr().getId()).equals(Status.NOTCOMPUTED)) {
					if (this.satisfies(new Pair(destNode, tc.getShapeExpr().getId()),false))
						localTyping.setStatus(destNode, tc.getShapeExpr().getId(),Status.CONFORMANT);	
					else
						localTyping.setStatus(destNode, tc.getShapeExpr().getId(),Status.NONCONFORMANT);	
				} else {
					localTyping.setStatus(destNode, tc.getShapeExpr().getId(), typing.getStatus(destNode, tc.getShapeExpr().getId()));
				}
			}
		}
		return this.findMatching(node, shape, localTyping).getMatching() != null;
	}	

	class EvaluateShapeExpressionVisitor extends ShapeExpressionVisitor<Boolean> {
		private RDFTerm node; 
		private boolean validateShape;

		public EvaluateShapeExpressionVisitor(boolean validateShape, RDFTerm node) {
			this.node = node;
			this.validateShape = validateShape;
		}

		@Override
		public Boolean getResult() {
			if (super.getResult() == null) return false;
			return super.getResult();
		}

		@Override
		public void visitShapeAnd(ShapeAnd expr, Object... arguments) {
			for (ShapeExpr e : expr.getSubExpressions()) {
				e.accept(this);
				if (!getResult()) break;
			}
		}

		@Override
		public void visitShapeOr(ShapeOr expr, Object... arguments) {
			for (ShapeExpr e : expr.getSubExpressions()) {
				e.accept(this);
				if (getResult()) break;
			}
		}

		@Override
		public void visitShapeNot(ShapeNot expr, Object... arguments) {
			expr.getSubExpression().accept(this);
			setResult(!getResult());
		}

		@Override
		public void visitShape(Shape expr, Object... arguments) {
			if (validateShape)
				setResult(matches(node, expr));
			else
				setResult(typing.isConformant(node, expr.getId()));
		}

		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
			setResult(expr.contains(node));
		}

		@Override
		public void visitShapeExprRef(ShapeExprRef ref, Object... arguments){
			ref.getShapeDefinition().accept(this);
		}
	}


	// Typing utils

	private List<Pair<RDFTerm, Label>> addAllLabelsForStratum(int stratum) {
		ArrayList<Pair<RDFTerm, Label>> result = new ArrayList<>();
		Set<Label> labels = schema.getStratification().get(stratum);
		typing.startStratum(labels);
		for (Label label: labels) {
			for (RDFTerm node : CommonGraph.getAllNodes(graph)) {
				result.add(new Pair<>(node, label));
				this.typing.setNonShapeLabelStatus(node, label, Status.CONFORMANT);
			}
		}
		return result;
	}
}
