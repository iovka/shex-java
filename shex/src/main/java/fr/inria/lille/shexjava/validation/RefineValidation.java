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

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Iovka Boneva
 */
public class RefineValidation extends ValidationAlgorithmAbstract {

	private boolean computed = false;
	private TypingForRefineValidation typing;
	private ShapeEvaluation shapeEvaluation;
	protected SORBEGenerator sorbeGenerator;

	public RefineValidation(ShexSchema schema, Graph graph) {
		super(schema,graph);
		this.sorbeGenerator = new SORBEGenerator();
		this.shapeEvaluation = new ShapeEvaluation(graph, typing, collectorTC, sorbeGenerator);
	}


	@Override
	protected boolean performValidation(RDFTerm focusNode, Label label) {
		computeMaximalTyping();
		cornerCaseValidate(focusNode, label);
		return typing.asTyping().isConformant(focusNode, label);
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
				Iterator<Pair<RDFTerm, ShapeExpr>> typesIt = typing.currentStratumNodeShapePairsIterator();
				while (typesIt.hasNext()) {
					Pair<RDFTerm, ShapeExpr> nl = typesIt.next();
					if (! evaluateShape(nl.one, (Shape) nl.two)) {
						typesIt.remove();
						changed = true;
					}
				}
			} while (changed);
			typing.endStratum();
		}
		// Compute the typing for the user defined shape expression labels
		for (Map.Entry<Label, ShapeExpr> le : schema.getShapeExprsMap().entrySet()) {
			if (! le.getKey().isUserDefined())
				continue;
			for (RDFTerm node : typing.allNodes()) {
				boolean valid = le.getValue() instanceof Shape
						? typing.contains(node, le.getValue())
						: satisfies(node, le.getValue());
				typing.setStatusOfUserDefined (node, le.getKey(), valid ? Status.CONFORMANT : Status.NONCONFORMANT);
			}
		}
		computed = true;
	}

	private boolean satisfies (RDFTerm node, ShapeExpr shapeExpr) {
		return ShapeEvaluation.evaluateWithNeighboursTyping(node, shapeExpr, typing);
	}

	private boolean evaluateShape (RDFTerm node, Shape shape) {
		return shapeEvaluation.evaluate(node, shape);
	}

	// TODO the corner cases are probably simpler: if the node is in the graph then its satisfying shapes can be tested with the typing except if the node is a literal, in which case its neighbourhood is empty
	/** Checks if this is a corner case and computes validity in this case. Also modifies the typing accordingly.
	 * One corner case is when {@param node} is not part of the graph in which case the maximal typing does not contain the node.
	 * The other corner case is when {@param label} refers to a shape expression without shapes (i.e. only node constraints), in which case the maximal typing does not allow to test validity.
	 */
	private void cornerCaseValidate (RDFTerm node, Label label) {
		ShapeExpr expr = schema.getShapeExprsMap().get(label);
		Set<Shape> allShapes = SchemaCollectors.collectAllShapes(expr);
		if (typing.allNodes().contains(node) && ! allShapes.isEmpty())
			// not a corner case
			return;

		if (allShapes.isEmpty())
			this.typing.setStatusOfUserDefined(node, label,
					satisfies(node, schema.getShapeExprsMap().get(label)) ? Status.CONFORMANT : Status.NONCONFORMANT);

		// We must test wethether the empty neighbourhood satisfies the shape expr
		// This can be done with satisfies and a typing that associates to the node only the shapes that are valid for the empty neighbourhood
		DefaultTypingForValidation localTyping = new DefaultTypingForValidation();
		for (Shape shape: allShapes) {
			if (evaluateShape(node, shape))
				localTyping.add(node, shape);
		}
		this.typing.setStatusOfUserDefined(node, label,
				ShapeEvaluation.evaluateWithNeighboursTyping(node, expr, localTyping)
						? Status.CONFORMANT
						: Status.NONCONFORMANT);
	}

}