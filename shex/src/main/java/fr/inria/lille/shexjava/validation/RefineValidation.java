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
	private Set<Label> selectedShape;
	private Set<Label> extraShapes;
	private boolean computed = false;

	public RefineValidation(ShexSchema schema, Graph graph) {
		super(schema,graph);
		this.extraShapes=Collections.emptySet();
		initSelectedShape();
	}
	
	public RefineValidation(ShexSchema schema, Graph graph,Set<Label> extraShapes) {
		super(schema,graph);
		this.extraShapes=extraShapes;
		initSelectedShape();
	}
	
	protected void initSelectedShape() {
		this.selectedShape = new HashSet<Label>(extraShapes);
		this.selectedShape.addAll(schema.getRules().keySet());
		for (ShapeExpr expr:schema.getShapeExprsMap().values())
			if (expr instanceof ShapeExprRef) 
				selectedShape.add(((ShapeExprRef) expr).getShapeDefinition().getId());
		for (TripleExpr expr:schema.getTripleExprsMap().values())
			if (expr instanceof TripleConstraint)
				selectedShape.add(((TripleConstraint) expr).getShapeExpr().getId());
	}
	
	@Override
	public void resetTyping() {
		this.typing = new Typing();
		computed = false;
	}
	
	@Override
	public boolean validate(RDFTerm focusNode, Label label)  throws Exception {
		if (!computed) {
			for (int stratum = 0; stratum < schema.getStratification().size(); stratum++) {
				List<Pair<RDFTerm, Label>> elements = addAllLabelsForStratum(stratum,focusNode);		
				//System.out.println(elements);
				boolean changed;
				do {
					changed = false;
					Iterator<Pair<RDFTerm, Label>> typesIt = elements.iterator();
					while (typesIt.hasNext()) {
						Pair<RDFTerm, Label> nl = typesIt.next();
						if (! isLocallyValid(nl)) {
							typesIt.remove();
							typing.setStatus(nl.one, nl.two, Status.NONCONFORMANT);
							changed = true;
						}
					}
				} while (changed);
			}
			computed=true;
		}		
		if (focusNode==null || label==null)
			return false;
		if (!schema.getShapeExprsMap().containsKey(label))
			throw new Exception("Unknown label: "+label);
		return typing.isConformant(focusNode, label);
	}

	
	private boolean isLocallyValid(Pair<RDFTerm, Label> nl) {
		EvaluateShapeExpressionVisitor visitor = new EvaluateShapeExpressionVisitor(nl.one);
		schema.getShapeExprsMap().get(nl.two).accept(visitor);
		return visitor.getResult();
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
			result = typing.isConformant(node, ref.getLabel());
		}

		@Override
		public void visitShapeExternal(ShapeExternal shapeExt, Object[] arguments) {
			throw new UnsupportedOperationException("Not yet implemented.");
		}
	}
	
	
	private boolean isLocallyValid (RDFTerm node, Shape shape) {
		List<Pair<Triple,Label>> result = this.findMatching(node, shape, this.getTyping());
		if (result == null) {
			return false;
		}
		return true;
	}	

	
	// Typing utils
	
	protected List<Pair<RDFTerm, Label>> addAllLabelsForStratum(int stratum,RDFTerm focusNode) {
		ArrayList<Pair<RDFTerm, Label>> result = new ArrayList<Pair<RDFTerm, Label>>();
		Set<Label> labels = schema.getStratification().get(stratum);
		for (Label label: labels) {
			if (selectedShape.contains(label)) {
				for( RDFTerm node:CommonGraph.getAllNodes(graph)) {		
					result.add(new Pair<>(node, label));
					this.typing.setStatus(node, label, Status.CONFORMANT);
				}
				if (focusNode !=null) {
					result.add(new Pair<>(focusNode, label));
					this.typing.setStatus(focusNode, label, Status.CONFORMANT);
				}
			}
		}
		return result;
	}

	@Override
	public void addMatchingCollector(MatchingCollector m) {
		// TODO Auto-generated method stub
		
	}
	
}
