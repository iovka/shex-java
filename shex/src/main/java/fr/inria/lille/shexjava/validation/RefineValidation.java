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
import java.util.List;
import java.util.Set;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;

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
import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;
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
public class RefineValidation extends SORBEBasedValidation {
	
	private Set<RDFTerm> allGraphNodes;
	private boolean computed = false;
	// TODO: this could be a particular implementation for RefineValidation, in which the nonconformant status is the default one. Careful to nodes that are not in the graph though.
	private TypingForValidation typing;

	
	// TODO A quoi ça sert ?
	private Set<Label> selectedShapes;
	private Set<Label> extraShapes;
	

	public RefineValidation(ShexSchema schema, Graph graph) {
		this(schema, graph, Collections.emptySet());
	}
	
	public RefineValidation(ShexSchema schema, Graph graph, Set<Label> extraShapes) {
		super(schema,graph);
		this.allGraphNodes = CommonGraph.getAllNodes(graph);
		this.extraShapes=extraShapes;
		initSelectedShape();
	}
	
	// TODO pourquoi est-ce que c'est nécessaire ?
	private void initSelectedShape() {
		this.selectedShapes = new HashSet<>(extraShapes);
		this.selectedShapes.addAll(schema.getRules().keySet());
		for (ShapeExpr expr:schema.getShapeExprsMap().values())
			if (expr instanceof ShapeExprRef) 
				selectedShapes.add(((ShapeExprRef) expr).getShapeDefinition().getId());
		for (TripleExpr expr:schema.getTripleExprsMap().values())
			if (expr instanceof TripleConstraint)
				selectedShapes.add(((TripleConstraint) expr).getShapeExpr().getId());
	}
	
	
	@Override
	public Typing getTyping() {
		return typing;
	}
	
	@Override
	public void resetTyping() {
		this.typing = new TypingForValidation();
		computed = false;
	}
	
	@Override
	public boolean validate (RDFTerm focusNode, Label label) {

		computeMaximalTyping(focusNode);

		if (focusNode != null && ! allGraphNodes.contains(focusNode)) {
			addToTypingNodeNotInTheGraph(focusNode, label);
		}
		
		// TODO why is that ?
		if (focusNode==null || label==null)
			return false;
		if (!schema.getShapeExprsMap().containsKey(label))
			throw new IllegalArgumentException("Unknown label: "+label);
		return typing.isConformant(focusNode, label);
	}

	private void addToTypingNodeNotInTheGraph(RDFTerm focusNode, Label label) {
		if (this.typing.getStatus(focusNode, label) == Status.NOTCOMPUTED)
			if (satisfies(new Pair<>(focusNode, label)))
				this.typing.setStatus(focusNode, label, Status.CONFORMANT);
			else
				this.typing.setStatus(focusNode, label, Status.NONCONFORMANT);
	}

	private void computeMaximalTyping(RDFTerm focusNode) {
		if (computed)
			return;
		for (int stratum = 0; stratum < schema.getStratification().size(); stratum++) {
			List<Pair<RDFTerm, Label>> elements = addAllLabelsForStratum(stratum,focusNode);		
			boolean changed;
			do {
				changed = false;
				Iterator<Pair<RDFTerm, Label>> typesIt = elements.iterator();
				while (typesIt.hasNext()) {
					Pair<RDFTerm, Label> nl = typesIt.next();
					if (! satisfies(nl)) {
						typesIt.remove();
						typing.setStatus(nl.one, nl.two, Status.NONCONFORMANT);
						changed = true;
					}
				}
			} while (changed);
		}
		System.out.println(typing.getStatusMap());
		computed = true;
	}

	// TODO this should take the expr and not the label as parameter
	/** Tests whether the node satisfies the shape expresion with specified label and with the current typing */
	private boolean satisfies(Pair<RDFTerm, Label> nl) {
		shexprEvaluator.setNode(nl.one);
		schema.getShapeExprsMap().get(nl.two).accept(shexprEvaluator);
		return shexprEvaluator.getResult();
	}
	
	/** Tests whether the node's neighbourhood matches the shape with the current typing */
	private boolean matches (RDFTerm node, Shape shape) {
		return null != this.findMatching(node, shape, this.getTyping()).getMatching();
	}	
	
	private EvaluateShapeExpressionVisitor shexprEvaluator = new EvaluateShapeExpressionVisitor();
		
	class EvaluateShapeExpressionVisitor extends ShapeExpressionVisitor<Boolean> {		
		private RDFTerm node; 
		private Boolean result;
	
		void setNode (RDFTerm node) {
			this.node = node;
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
			result = matches(node, expr);
		}

		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
			result = expr.contains(node);
		}

		@Override
		public void visitShapeExprRef(ShapeExprRef ref, Object[] arguments) {
			result = typing.isConformant(node, ref.getLabel());
		}
	}
	


	
	// Typing utils
	
	private List<Pair<RDFTerm, Label>> addAllLabelsForStratum(int stratum, RDFTerm focusNode) {
		ArrayList<Pair<RDFTerm, Label>> result = new ArrayList<>();
		Set<Label> labels = schema.getStratification().get(stratum); // TODO does it mean that all the labels, even those of the triple constraints, have a stratum ?
		// TODO According to the spec, the stratification is a set of Shape
		// TODO why to we need to add the triple constraints to the selected shapes ? Can't we simply implement the abstract algorithm ?
		for (Label label: labels) {
			if (selectedShapes.contains(label)) {
				for (RDFTerm node : allGraphNodes) {		
					result.add(new Pair<>(node, label));
					this.typing.setStatus(node, label, Status.CONFORMANT);
				}
				// TODO why do we need to add the focus node here ? In case it does not belong to the graph ?
				if (focusNode !=null) {
					result.add(new Pair<>(focusNode, label));
					this.typing.setStatus(focusNode, label, Status.CONFORMANT);
				}
			}
		}
		return result;
	}
}
