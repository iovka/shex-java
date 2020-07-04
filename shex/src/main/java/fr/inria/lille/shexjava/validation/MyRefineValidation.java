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

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.*;
import fr.inria.lille.shexjava.schema.analysis.SchemaCollectors;
import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;
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
public class MyRefineValidation extends ValidationAlgorithmAbstract {

	private boolean computed = false;
    private TypingForRefineValidation typing;
    protected SORBEGenerator sorbeGenerator;

    public MyRefineValidation(ShexSchema schema, Graph graph) {
		super(schema,graph);
		this.sorbeGenerator = new SORBEGenerator(schema.getRdfFactory());
	}

    @Override
    protected boolean performValidation(RDFTerm focusNode, Label label) throws Exception {
        computeMaximalTyping();

        Boolean valid = cornerCaseValidate(focusNode, label);
        if (valid != null) {
            typing.setNonShapeLabelStatus(focusNode, label, valid ? Status.CONFORMANT : Status.NONCONFORMANT);
        }
        return typing.isConformant(focusNode, label);
    }

    @Override
	public Typing getTyping() {
		return typing;
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
			typing.startStratum(schema.getStratification().get(stratum));
			boolean changed;
			do {
				changed = false;
				Iterator<Pair<RDFTerm, Label>> typesIt = typing.currentStratumNodeShapeLabelPairsIterator();
				while (typesIt.hasNext()) {
                    Pair<RDFTerm, Label> nl = typesIt.next();
                    Shape shape = (Shape) schema.getShapeExprsMap().get(nl.two);
				    if (! evaluateShape(nl.one, shape)) {
						typesIt.remove();
						changed = true;
					}
				}
			} while (changed);
			typing.endStratum();
		}
        // Compute the typing for the non shape labels
        // TODO to be optimized. Now it goes through all labels, should be limited to those that are on the current stratum
        for (Label label : schema.getShapeExprsMap().keySet()) {
            for (RDFTerm node : typing.allNodes()) {
                typing.setNonShapeLabelStatus(node, label,
                        satisfies(node, schema.getShapeExprsMap().get(label)) ? Status.CONFORMANT : Status.NONCONFORMANT);
            }
        }
		computed = true;
	}

    private boolean satisfies (RDFTerm node, ShapeExpr shapeExpr) {
        EvaluateShapeExprVistor eval = new EvaluateShapeExprVistor(node, typing);
        shapeExpr.accept(eval);
        return eval.getResult();
    }

    private boolean evaluateShape (RDFTerm node, Shape shape) {
        MyShapeEvaluation eval = new MyShapeEvaluation(graph, node, shape, typing, collectorTC, sorbeGenerator);
        return eval.evaluate();
    }

    /** Checks if a corner case and computes validity in this case.
     *
     * @param node
     * @param label
     * @return null if non corner case, a non null boolean with validity result otherwise
     */
	private Boolean cornerCaseValidate (RDFTerm node, Label label) {
	    ShapeExpr expr = schema.getShapeExprsMap().get(label);
	    Set<Shape> allShapes = SchemaCollectors.collectAllShapes(expr);
        if (typing.allNodes().contains(node) && ! allShapes.isEmpty())
            // not a corner case
            return null;

        if (allShapes.isEmpty())
            return satisfies(node, expr);

        // We must test wethether the empty neighbourhood satisfies the shape expr
        // This can be done with satisfies and a typing that associates to the node only the shapes that contain the empty neighbourhood
        DefaultTyping localTyping = new DefaultTyping();
        for (Shape shape: allShapes) {
            if (evaluateShape(node, shape))
                localTyping.setStatus(node, shape.getId(), Status.CONFORMANT);
        }
        EvaluateShapeExprVistor eval = new EvaluateShapeExprVistor(node, localTyping);
        expr.accept(eval);
        return eval.getResult();
    }




	// TODO COPY PASTE with MyShapeEvaluation
    class EvaluateShapeExprVistor extends ShapeExpressionVisitor<Boolean> {

        private final RDFTerm node;
        private final Typing typing;
        EvaluateShapeExprVistor(RDFTerm node, Typing shapesTyping) {
            this.node = node;
            this.typing = shapesTyping;
        }

        @Override
        public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
            setResult(expr.contains(node));
        }

        @Override
        public void visitShape(Shape expr, Object... arguments) {
            setResult(typing.isConformant(node, expr.getId()));
        }

        @Override
        public void visitShapeExprRef(ShapeExprRef shapeRef, Object... arguments) {
            shapeRef.getShapeDefinition().accept(this, arguments);
        }

        @Override
        public void visitShapeAnd(ShapeAnd expr, Object... arguments) {
            for (ShapeExpr e : expr.getSubExpressions()) {
                e.accept(this, arguments);
                if (!getResult()) break;
            }
        }

        @Override
        public void visitShapeOr(ShapeOr expr, Object... arguments) {
            for (ShapeExpr e : expr.getSubExpressions()) {
                e.accept(this, arguments);
                if (getResult()) break;
            }
        }

        @Override
        public void visitShapeNot(ShapeNot expr, Object... arguments) {
            expr.getSubExpression().accept(this);
            setResult(!getResult());
        }

    }





}
