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
import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExpr;
import fr.inria.lille.shexjava.util.Pair;
import org.apache.commons.rdf.api.RDFTerm;

import java.util.*;
import java.util.stream.Collectors;

/** Suited for @link {@link RefineValidation} that starts by computing the validity of all {@link Shape}s following the stratification structure of the schema.
 * @author Iovka Boneva
 */
public class TypingForRefineValidation implements MyTypingForValidation {

    private ShexSchema schema;

    /** The labels of the already validated stratums. */
    private Set<Label> validatedShapeLabels = new HashSet<>();
    /** The (node, shape) associations for the already validated stratums */
    private Map<RDFTerm, Set<Shape>> validShapesMap;

    /** The labels on the currently computed stratum. */
    private Set<Label> currentStratumShapeLabels = new HashSet<>();
    /** (node, shape) pairs on the current stratum. */
    private DefaultTypingForValidation currentStratumNodeShapePairs;

    /* Status for the user defined labels. */
    private DefaultTyping shapeExpressionsTyping = new DefaultTyping();

    /** Creates a typing for storing the types for the given nodes for labels of the given schema.
     *
     * @param nodes
     * @param schema
     */
    public TypingForRefineValidation (Set<RDFTerm> nodes, ShexSchema schema) {
        this.schema = schema;
        validShapesMap = new HashMap<>(nodes.size());
        currentStratumNodeShapePairs = new DefaultTypingForValidation();
        for (RDFTerm node : nodes) {
            validShapesMap.put(node, new HashSet<>());
        }
    }

    /** Tests whether the shape is currently associated with the given node. */
    @Override
    public boolean contains(RDFTerm node, ShapeExpr shapeExpr) {
        Set<Shape> nodeShapes = validShapesMap.get(node);
        if (nodeShapes != null && nodeShapes.contains(shapeExpr))
            return true;
        if (currentStratumNodeShapePairs.contains(node, shapeExpr))
            return true;
        return false;
    }

    /** Not supported for refine validation, use {@link #startStratum(int)} for adding node-shape associations.
     * @throws UnsupportedOperationException
     */
    @Override
    public void add (RDFTerm node, ShapeExpr shapeExpr) {
        throw new UnsupportedOperationException("Not supported.");
    }
    /** Not supported for refine validation, use {@link #currentStratumNodeShapePairsIterator()} for removing node-shape associations.
     * @throws UnsupportedOperationException
     */
    @Override
    public void remove(RDFTerm node, ShapeExpr shapeExpr) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public void setStatusOfUserDefined(RDFTerm node, Label shapeExprLabel, Status status) {
        shapeExpressionsTyping.setStatus(node, shapeExprLabel, status);
    }

    private Typing typingView = new Typing() {
        @Override
        public Status getStatus(RDFTerm node, Label label) {
            if (validatedShapeLabels.contains(label) && allNodes().contains(node))
                return validShapesMap.get(node).contains(schema.getShapeExprsMap().get(label))
                        ? Status.CONFORMANT
                        : Status.NONCONFORMANT;
            else
                return shapeExpressionsTyping.getStatus(node,label);
        }

        @Override
        public Map<Pair<RDFTerm, Label>, Status> getStatusMap() {
            // FIXME compute status map lazily. Maybe consider only the significant (node, label) pairs
            throw new UnsupportedOperationException("not yet implemented");
        }
    };

    public Typing asTyping() {
        return typingView;
    }

    // ---------------------------------------------------------------------------------
    // Methods specific to refine validation
    // ---------------------------------------------------------------------------------
    /** The set of nodes on which the typing is defined.
     * @return
     */
    Set<RDFTerm> allNodes () {
        return validShapesMap.keySet();
    }

    /** To be called when validation of a stratum starts.
     * Adds the given stratum shape labels to the types of all nodes. */
     void startStratum (int stratum) {
         currentStratumShapeLabels.addAll(schema.getStratification().get(stratum));

         Set<Shape> stratumShapes = schema.getStratification().get(stratum).stream()
                 .map(l -> (Shape) (schema.getShapeExprsMap().get(l))).collect(Collectors.toSet());
         for (RDFTerm node : validShapesMap.keySet())
             for (Shape shape : stratumShapes)
                currentStratumNodeShapePairs.add(node, shape);
    }

    /** To be called when a validation of a stratum ends. */
    void endStratum () {
        validatedShapeLabels.addAll(currentStratumShapeLabels);
        currentStratumShapeLabels.clear();
        for (Pair<RDFTerm, ShapeExpr> nl : currentStratumNodeShapePairs) {
            validShapesMap.get(nl.one).add((Shape)nl.two);
        }
        currentStratumNodeShapePairs.clear();
    }

    /** Iterates over the node-shape pairs of the current stratum. */
    Iterator<Pair<RDFTerm, ShapeExpr>> currentStratumNodeShapePairsIterator() {
        return currentStratumNodeShapePairs.iterator();
    }
}
