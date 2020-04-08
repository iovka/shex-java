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

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
		this.resetTyping();
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
			if (expr instanceof ShapeOr)
				this.visitShapeOr((ShapeOr) expr);
			if (expr instanceof ShapeNot)
				this.visitShapeNot((ShapeNot) expr);
			if (expr instanceof Shape)
				this.visitShape((Shape) expr);
			if (expr instanceof NodeConstraint)
				this.visitNodeConstraint((NodeConstraint) expr);
			if (expr instanceof ShapeExprRef)
				this.visitShapeExprRef((ShapeExprRef) expr);
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
			result = isLocallyValid(node, expr);
		}

		public void visitNodeConstraint(NodeConstraint expr) {
			result = expr.contains(node);
			typing.setStatus(node, expr.getId(), result?Status.CONFORMANT:Status.NONCONFORMANT);
		}

		public void visitShapeExprRef(ShapeExprRef ref) throws Exception {
			this.accept(ref.getShapeDefinition());
		}
	}
	
	
	private boolean isLocallyValid (RDFTerm node, Shape shape) throws Exception {
		TripleExpr tripleExpression = this.sorbeGenerator.getSORBETripleExpr(shape);

		List<TripleConstraint> constraints = collectorTC.getTCs(tripleExpression);	
		List<Triple> neighbourhood = ValidationUtils.getMatchableNeighbourhood(graph, node, constraints, shape.isClosed());

		// Match using only predicate and recursive test. The following lines is the only big difference with refine validation. 
		TypingForValidation localTyping = new TypingForValidation();
		
		PreMatching preMatching = ValidationUtils.computePreMatching(node, neighbourhood, constraints, shape.getExtraProperties(), ValidationUtils.getPredicateOnlyMatcher());
		Map<Triple,List<TripleConstraint>> matchingTC1 = preMatching.getPreMatching();
			
		for(Map.Entry<Triple,List<TripleConstraint>> entry:matchingTC1.entrySet()) {
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
				
		return this.findMatching(node, shape, localTyping).getMatching() != null;
	}	

	
}
