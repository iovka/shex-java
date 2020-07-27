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
    private Map<RDFTerm, Set<Shape>> validShapesMap;
    private Set<Pair<RDFTerm, Shape>> currentStratumNodeShapeLabelPairs;
    /** The shape labels for which validShapesMap constitutes a complete typing. */
    private Set<Label> computedStratumsShapeLabels = new HashSet<>();
    private Set<Label> currentStratumShapeLabels = new HashSet<>();

    /* Used for the Typing view. */
    private DefaultTyping otherLabelsTyping = new DefaultTyping();

    /** Creates a typing for storing the types for the given nodes for labels of the given schema.
     *
     * @param nodes
     * @param schema
     */
    public TypingForRefineValidation (Set<RDFTerm> nodes, ShexSchema schema) {
        this.schema = schema;
        validShapesMap = new HashMap<>(nodes.size());
        currentStratumNodeShapeLabelPairs = new LinkedHashSet<>(nodes.size() * 2); // This is a guess for the maximal size of the set
        for (RDFTerm node : nodes) {
            validShapesMap.put(node, new HashSet<>());
        }
    }

    /** Tests whether the shape is currently associated with the given node. */
    @Override
    public boolean containsShape (RDFTerm node, Shape shape) {
        Set<Shape> nodeShapes = validShapesMap.get(node);
        if (nodeShapes != null && nodeShapes.contains(shape))
            return true;
        if (currentStratumNodeShapeLabelPairs.contains(new Pair<>(node, shape)))
            return true;
        return false;
    }

    @Override
    public boolean containsShapeExpr(RDFTerm node, ShapeExpr shapeExpr) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /** Not supported for refine validation, use {@link #startStratum(int)} for adding node-shape associations.
     * @throws UnsupportedOperationException
     */
    @Override
    public void addShape(RDFTerm node, Shape shape) {
        throw new UnsupportedOperationException("Not supported.");
    }
    /** Not supported for refine validation, use {@link #currentStratumNodeShapeLabelPairsIterator()} for removing node-shape associations.
     * @throws UnsupportedOperationException
     */
    @Override
    public void removeShape(RDFTerm node, Shape shape) {
        throw new UnsupportedOperationException("Not supported.");
    }


    @Override
    public void setStatus(RDFTerm node, Label shapeExprLabel, Status status) {
        otherLabelsTyping.setStatus(node, shapeExprLabel, status);
    }

    private Typing typingView = new Typing() {
        @Override
        public Status getStatus(RDFTerm node, Label label) {
            if (computedStratumsShapeLabels.contains(label) && allNodes().contains(node))
                return validShapesMap.get(node).contains(schema.getShapeExprsMap().get(label))
                        ? Status.CONFORMANT
                        : Status.NONCONFORMANT;
            else
                return otherLabelsTyping.getStatus(node,label);
        }

        @Override
        public Map<Pair<RDFTerm, Label>, Status> getStatusMap() {
            // FIXME compute status map lazily. Maybe consider only the significant (node, label) pairs
            throw new UnsupportedOperationException("not yet implemented");
        }
    };

    @Override
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
         currentStratumShapeLabels.addAll(schema.getStratification().get(stratum).stream()
                 .filter(l -> !schema.getShapeExprsMap().get(l).isAbstract()).collect(Collectors.toSet()));

         Set<Shape> stratumShapes = currentStratumShapeLabels.stream()
                 .map(l -> (Shape) schema.getShapeExprsMap().get(l)).collect(Collectors.toSet());
         for (RDFTerm node : validShapesMap.keySet())
             for (Shape shape : stratumShapes)
                currentStratumNodeShapeLabelPairs.add(new Pair(node, shape));
    }

    /** To be called when a validation of a stratum ends. */
    void endStratum () {
        computedStratumsShapeLabels.addAll(currentStratumShapeLabels);
        currentStratumShapeLabels.clear();
        for (Pair<RDFTerm, Shape> nl : currentStratumNodeShapeLabelPairs) {
            validShapesMap.get(nl.one).add(nl.two);
        }
        currentStratumNodeShapeLabelPairs.clear();
    }

    /** Iterates over the node-label pairs of the current stratum. */
    Iterator<Pair<RDFTerm, Shape>> currentStratumNodeShapeLabelPairsIterator() {
        return currentStratumNodeShapeLabelPairs.iterator();
    }
}
