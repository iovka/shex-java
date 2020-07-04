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
import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;
import fr.inria.lille.shexjava.util.CommonGraph;
import fr.inria.lille.shexjava.util.Pair;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;

import java.util.Iterator;

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
        computeMaximalTyping(focusNode);
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
		computeMaximalTyping(null);
	}

	private void computeMaximalTyping(RDFTerm focusNode) {
		if (computed)
			return;
        typing.ensureKnowsNode(focusNode);
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
				    MyShapeEvaluation eval = new MyShapeEvaluation(graph, nl.one, shape, typing, collectorTC, sorbeGenerator);
				    if (! eval.evaluate()) {
						typesIt.remove();
						changed = true;
					}
				}
			} while (changed);
			typing.endStratum();

			// Compute the typing for the non shape labels
            // TODO to be optimized. Now it goes through all labels, should be limited to those that are on the current stratum
            for (Label label : schema.getShapeExprsMap().keySet()) {
                for (RDFTerm node : typing.allNodes()) {
                    EvaluateShapeExprVistor eval = new EvaluateShapeExprVistor(node, typing);
                    schema.getShapeExprsMap().get(label).accept(eval);
                    typing.setNonShapeLabelStatus(node, label,
                            eval.getResult() ? Status.CONFORMANT : Status.NONCONFORMANT);
                }
            }
		}
		computed = true;
	}


	// TODO COPY PASTE with MyShapeEvaluation
    class EvaluateShapeExprVistor extends ShapeExpressionVisitor<Boolean> {

        private final RDFTerm focusNode;
        private final Typing typing;
        EvaluateShapeExprVistor(RDFTerm focusNode, Typing shapesTyping) {
            this.focusNode = focusNode;
            this.typing = shapesTyping;
        }

        @Override
        public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
            setResult(expr.contains(focusNode));
        }

        @Override
        public void visitShape(Shape expr, Object... arguments) {
            setResult(typing.isConformant(focusNode, expr.getId()));
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
