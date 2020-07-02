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
import fr.inria.lille.shexjava.util.Pair;
import org.apache.commons.rdf.api.RDFTerm;

import java.util.*;

/** Implementation of typing that explicitly stores all conformant (node, label).
 * Suited for refine validation, where there are no non computed (node,label) pairs when label is a label for a shape.
 *
 * @author Iovka Boneva
 */
public class TypingForRefineValidation implements Typing {

    private ShexSchema schema;
    private Map<RDFTerm, Set<Label>> shapeLabels;
    private DefaultTyping otherLabelsTyping = new DefaultTyping();
    private Set<Pair<RDFTerm, Label>> currentStratumNodeShapeLabelPairs;

    /** Creates a typing for storing the types for the given nodes for labels of the given schema.
     *
     * @param nodes
     * @param schema
     */
    public TypingForRefineValidation (Set<RDFTerm> nodes, ShexSchema schema) {
        this.schema = schema;
        shapeLabels = new HashMap<>(nodes.size());
        currentStratumNodeShapeLabelPairs = new LinkedHashSet<>(nodes.size() * 2); // This is a guess for the maximal size of the set
        for (RDFTerm node : nodes) {
            shapeLabels.put(node, new HashSet<>());
        }
    }

    /** To be called when validation of a stratum starts.
     * Adds the given stratum shape labels to the types of all nodes. */
     void startStratum (Set<Label> stratumShapeLabels) {
         // From the order in which these are added may depend the efficiency of the algorithm
         // Here we choose to group them by node
         for (RDFTerm node : shapeLabels.keySet())
             for (Label label : stratumShapeLabels)
                currentStratumNodeShapeLabelPairs.add(new Pair(node, label));
    }

    /** To be called when a validation of a stratum ends. */
    void endStratum () {
        for (Pair<RDFTerm, Label> nl : currentStratumNodeShapeLabelPairs) {
            shapeLabels.get(nl.one).add(nl.two);
        }
    }

    /** Iterates over the node-label pairs of the current stratum. */
    Iterator<Pair<RDFTerm, Label>> currentStratumNodeShapeLabelPairsIterator() {
        return currentStratumNodeShapeLabelPairs.iterator();
    }

    /** Sets the status to a (node, nonShapeLabel) pair.
     * @param node
     * @param nonShapeLabel should be a shape expression label from the schema given at construction (correct behaviour is not guaranteed otherwise)
     * @param status
     */
    void setNonShapeLabelStatus(RDFTerm node, Label nonShapeLabel, Status status) {
        otherLabelsTyping.setStatus(node, nonShapeLabel, status);
    }

    @Override
    public Status getStatus(RDFTerm node, Label label) {
        if (schema.getShapeExprsMap().get(label) instanceof Shape) {
            Set<Label> labels = shapeLabels.get(node);
            if (labels == null) return Status.NOTCOMPUTED;
            else if (labels.contains(label)) return Status.CONFORMANT;
            else if (currentStratumNodeShapeLabelPairs.contains(new Pair(node, label))) return Status.CONFORMANT;
            else return Status.NONCONFORMANT;
        } else {
            return otherLabelsTyping.getStatus(node, label);
        }
    }

    @Override
    public Map<Pair<RDFTerm, Label>, Status> getStatusMap() {
        // FIXME compute status map lazily. Maybe consider only the significant (node, label) pairs
        throw new UnsupportedOperationException("not yet implemented");
    }

}
