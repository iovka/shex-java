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

import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExpr;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExprRef;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import java.util.*;
import java.util.stream.Collectors;

/** Allows to evaluate a shape againt a node.
 * @author Iovka Boneva
 */
public class MyShapeEvaluation {
    private Graph graph;
    private ShexSchema schema;
    private RDFTerm focusNode;
    private Shape topShape;
    private Typing neighboursTyping;
    private DynamicCollectorOfTripleConstraints collectorTC;

    private List<Triple> neighbourhood;
    private PreMatching preMatching;
    private Map<Object, List<TripleConstraint>> tcsOfExpressionMap;

    public MyShapeEvaluation(Graph graph, ShexSchema schema,
                             RDFTerm focusNode, Shape shape, Typing neighboursTyping,
                             DynamicCollectorOfTripleConstraints collectorTC) {
        this.graph = graph;
        this.schema = schema;
        this.focusNode = focusNode;
        this.topShape = shape;
        this.neighboursTyping = neighboursTyping;
        this.collectorTC = collectorTC;


    }

    private void init () {
        // Collect the triple constraints and store their parent structure
        List<TripleConstraint> allTripleConstraints = collectorTC.getTCs(topShape);
        neighbourhood = ValidationUtils.getMatchableNeighbourhood(graph, focusNode, allTripleConstraints, topShape.isClosed());

        preMatching = ValidationUtils.computePreMatching(focusNode, neighbourhood, allTripleConstraints,
                topShape.getExtraProperties(), neighboursTyping, ValidationUtils.predicateAndValueMatcher);
    }



    public boolean evaluate () {
        init();

        if (! preMatching.getUnmatched().isEmpty() && topShape.isClosed())
            return false;

        // TODO when do we check that those matched to extra are correct ? This can be done by the matcher (if it uses the typing), or should be done afterwards

        return evaluate(neighbourhood, topShape);
    }


    Map<Triple, Set<Object>> matchingSubExpressionsOfShape (
            List<Triple> triples,
            Map<Triple, List<TripleConstraint>> matchingTripleConstraints,
            Shape shape) {

        Map<Triple, Set<Object>> tripleToSubExpr = new HashMap<>(matchingTripleConstraints.size());
        for (Triple triple: triples) {
            for (TripleConstraint tc : matchingTripleConstraints.get(triple)) {
                Set<Object> set = tripleToSubExpr.get(triple);
                if (set == null) {
                    set = new HashSet<>();
                    tripleToSubExpr.put(triple, set);
                }
                Object parent = collectorTC.getParentInShape(tc, shape);
                if (parent != null) set.add(parent);
            }
        }
        return tripleToSubExpr;
    }

    private boolean evaluate (List<Triple> triples, Shape shape) {
        // For every triple, collect the sub-expressions of shape to which it can be matched
        throw new UnsupportedOperationException("not yet implemented");
    }

}
