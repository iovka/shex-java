/* ******************************************************************************
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

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.*;
import fr.inria.lille.shexjava.schema.analysis.SchemaCollectors;
import fr.inria.lille.shexjava.util.CommonGraph;
import fr.inria.lille.shexjava.util.Pair;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Iovka Boneva
 */
public class RefineValidation extends ValidationAlgorithmAbstract {

	private boolean computed = false;
	private TypingForRefineValidation typing;
	protected SORBEGenerator sorbeGenerator;

	public RefineValidation(ShexSchema schema, Graph graph) {
		super(schema,graph);
		this.sorbeGenerator = new SORBEGenerator();
	}

	@Override
	protected boolean performValidation(RDFTerm focusNode, Label label) {
		computeMaximalTyping();
		Status status = typing.asTyping().getStatus(focusNode, label);
		if (Status.NOTCOMPUTED.equals(status)) {
			status = cornerCaseValidate(focusNode, label);
			typing.setStatus(focusNode, label, status);
		}
		return Status.CONFORMANT.equals(status);
	}

	@Override
	public Typing getTyping() {
		return typing.asTyping();
	}

	@Override
	public void resetTyping() {
		this.typing = new TypingForRefineValidation(CommonGraph.getAllNonLiteralNodes(graph), schema);
		computed = false;
	}

	public void validate() {
		computeMaximalTyping();
	}

	private void computeMaximalTyping() {
		if (computed)
			return;
		// Compute the maximal typing for the shapes only
		for (int stratum = 0; stratum < schema.getStratification().size(); stratum++) {
			typing.startStratum(stratum);
			boolean changed;
			do {
				changed = false;
				Iterator<Pair<RDFTerm, Shape>> typesIt = typing.currentStratumNodeShapeLabelPairsIterator();
				while (typesIt.hasNext()) {
					Pair<RDFTerm, Shape> nl = typesIt.next();
					if (! evaluateShape(nl.one, nl.two)) {
						typesIt.remove();
						changed = true;
					}
				}
			} while (changed);
			typing.endStratum();
		}
		// Compute the typing for the user defined shape expressions
		for (Label label : schema.getShapeExprsMap().keySet()) {
			if (! label.isUserDefined()) continue;
			ShapeExpr expr = schema.getShapeExprsMap().get(label);
			for (RDFTerm node : typing.allNodes()) {
				typing.setStatus(node, label,
						satisfies(node, expr) ? Status.CONFORMANT : Status.NONCONFORMANT);
			}
		}
		computed = true;
	}

	private boolean satisfies (RDFTerm node, ShapeExpr shapeExpr) {
		EvaluateShapeExprVistor eval = new EvaluateShapeExprVistor(typing, schema.getConcreteSubShapesMap());
		shapeExpr.accept(eval, node);
		return eval.getResult();
	}

	private boolean evaluateShape (RDFTerm node, Shape shape) {
		ShapeEvaluation eval = new ShapeEvaluation(graph, schema, node, shape, typing, collectorTC, sorbeGenerator);
		return eval.evaluate();
	}

	// TODO the corner cases are probably simpler: if the node is in the graph then its satisfying shapes can be tested with the typing except if the node is a literal, in which case its neighbourhood is empty
	/** Checks if this is a corner case and computes validity in this case. Also modifies the typing accordingly.
	 * One corner case is when {@param node} is not part of the graph in which case the maximal typing does not contain the node.
	 * The other corner case is when {@param label} refers to a shape expression without shapes (i.e. only node constraints), in which case the maximal typing does not allow to test validity.
	 */
	private Status cornerCaseValidate (RDFTerm node, Label label) {
		if (! schema.getShapeExprsMap().containsKey(label))
			return Status.NOTCOMPUTED; // Nothing to do

		if (typing.allNodes().contains(node))
			throw new IllegalStateException("should not happen");

		// Associate with the node the sub-shapes of label's expression that match the node
		ShapeExpr expr = schema.getShapeExprsMap().get(label);
		Set<Shape> allShapes = SchemaCollectors.collectAllShapes(expr);
		SimpleTypingForValidation localTyping = new SimpleTypingForValidation();
		for (Shape shape: allShapes) {
			if (evaluateShape(node, shape))
				localTyping.addShape(node, shape);
		}
		EvaluateShapeExprVistor eval = new EvaluateShapeExprVistor(localTyping, schema.getConcreteSubShapesMap());
		expr.accept(eval, node);
		return eval.getResult() ? Status.CONFORMANT : Status.NONCONFORMANT;
	}

	/** Allows to evaluate a node against a shape expression while using the typing given at construction time to evaluate the Shapes. */
	static class EvaluateShapeExprVistor extends ShapeEvaluation.AbstractShapeExprEvaluator {

		private final MyTypingForValidation localTyping;
		EvaluateShapeExprVistor(MyTypingForValidation localTyping, Map<ShapeExpr, Set<Shape>> subShapesMap) {
			super(subShapesMap);
			this.localTyping = localTyping;
		}

		@Override
		public void visitShape(Shape expr, Object... arguments) {
			RDFTerm node = (RDFTerm) arguments[0];
			setResult(localTyping.containsShape(node, expr));
		}
	}

	/* Backed by a set of node-shape pairs.. */
	private static class SimpleTypingForValidation implements MyTypingForValidation {

		private Set<Pair<RDFTerm, Shape>> nodesShapes = new HashSet<>();

		@Override
		public boolean containsShape(RDFTerm node, Shape shape) {
			return nodesShapes.contains(new Pair<>(node, shape));
		}
		@Override
		public void addShape(RDFTerm node, Shape shape) {
			this.nodesShapes.add(new Pair<>(node, shape));
		}

		@Override
		public Typing asTyping() {
			throw new UnsupportedOperationException("Not supported");
		}

		@Override
		public boolean containsShapeExpr(RDFTerm node, ShapeExpr shapeExpr) {
			throw new UnsupportedOperationException("Not supported");
		}

		@Override
		public void removeShape(RDFTerm node, Shape shape) {
			throw new UnsupportedOperationException("Not supported");
		}

		@Override
		public void setStatus(RDFTerm node, Label shapeExprLabel, Status status) {
			throw new UnsupportedOperationException("Not supported");
		}
	}

	private static MyTypingForValidation emptyTyping = new MyTypingForValidation() {
		@Override
		public Typing asTyping() {
			throw new UnsupportedOperationException("Not supported");
		}

		@Override
		public boolean containsShape(RDFTerm node, Shape shape) {
			return false;
		}

		@Override
		public boolean containsShapeExpr(RDFTerm node, ShapeExpr shapeExpr) {
			return false;
		}

		@Override
		public void addShape(RDFTerm node, Shape shape) {
			throw new UnsupportedOperationException("Not supported");
		}

		@Override
		public void removeShape(RDFTerm node, Shape shape) {
			throw new UnsupportedOperationException("Not supported");
		}

		@Override
		public void setStatus(RDFTerm node, Label shapeExprLabel, Status status) {
			throw new UnsupportedOperationException("Not supported");
		}
	};

}