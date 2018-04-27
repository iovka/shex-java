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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.inria.lille.shexjava.graph.NeighborTriple;
import fr.inria.lille.shexjava.graph.RDFGraph;
import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExpr;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExprRef;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExpr;
import fr.inria.lille.shexjava.util.Pair;

/** The shape selected by default are the shapes of the set rules, the shapes that appear in a shape ref and the shapes that appear in a triple constraint.
 * 
 * @author Iovka Boneva
 */
public class RefinementTyping implements Typing {
	private ShexSchema schema;
	private RDFGraph graph;
	
	private List<Set<Pair<Value, Label>>> theTyping;
	private Map<Label,Set<Value>> shapeTyping;
	private Map<Value,Set<Label>> valueTyping;
	
	private Set<Label> selectedShape;
	
	private Map<Pair<Value, Label>,List<Pair<NeighborTriple,Label>>> matching;

	public RefinementTyping(ShexSchema schema, RDFGraph graph) {
		matching = new HashMap<Pair<Value, Label>,List<Pair<NeighborTriple,Label>>>();
		this.schema = schema;
		this.graph = graph;
		this.theTyping = new ArrayList<>(schema.getNbStratums());
		for (int i = 0; i < schema.getNbStratums(); i++) {
			theTyping.add(new HashSet<>());
		}
		initSelectedShape(Collections.emptySet());
	}
	
	public RefinementTyping(ShexSchema schema, RDFGraph graph, Set<Label> extraShapes) {
		matching = new HashMap<Pair<Value, Label>,List<Pair<NeighborTriple,Label>>>();
		this.schema = schema;
		this.graph = graph;
		this.theTyping = new ArrayList<>(schema.getNbStratums());
		for (int i = 0; i < schema.getNbStratums(); i++) {
			theTyping.add(new HashSet<>());
		}
		initSelectedShape(extraShapes);
	}
	
	protected void initSelectedShape(Set<Label> extraLabel) {
		this.selectedShape = new HashSet<Label>(extraLabel);
		this.selectedShape.addAll(schema.getRules().keySet());
		for (ShapeExpr expr:schema.getShapeMap().values())
			if (expr instanceof ShapeExprRef) 
				selectedShape.add(((ShapeExprRef) expr).getShapeDefinition().getId());
		for (TripleExpr expr:schema.getTripleMap().values())
			if (expr instanceof TripleConstraint)
				selectedShape.add(((TripleConstraint) expr).getShapeExpr().getId());
	}
	
	
	public Set<Label> getSelectedShape(){
		return this.selectedShape;
	}
	

	public void addAllLabelsFrom(int stratum, Value focusNode) {
		Set<Label> labels = schema.getStratum(stratum);
		Set<Pair<Value, Label>> set = theTyping.get(stratum);
		for (Label label: labels) {
			if (selectedShape.contains(label)) {
				Iterator<Value> ite = graph.listAllNodes();
				while(ite.hasNext()) {
					set.add(new Pair<>(ite.next(), label));
				}
				if (focusNode != null)
					set.add(new Pair<>(focusNode, label));
			}
		}
	}
	

	public Iterator<Pair<Value, Label>> typesIterator (int stratum) {
		return theTyping.get(stratum).iterator();
	}
	
	
	@Override
	public boolean contains (Value node, Label label) {
		return theTyping.get(schema.hasStratum(label)).contains(new Pair<>(node, label));
	}
	
	
	@Override
	public Set<Pair<Value, Label>> asSet() {
		Set<Pair<Value, Label>> set = new HashSet<>();
		for (Set<Pair<Value, Label>> subset : theTyping)
			set.addAll(subset);
		return set;
	}
	
	@Override
	public List<Pair<NeighborTriple, Label>> getMatch(Value node, Label label) {
		if (matching.containsKey(new Pair<Value, Label>(node,label)))
			return matching.get(new Pair<Value, Label>(node,label));		
		return null;
	}

	@Override
	public void setMatch(Value node, Label label, List<Pair<NeighborTriple, Label>> match) {
		matching.put(new Pair<Value, Label>(node,label), match);	
	}

	@Override
	public void removeMatch(Value node, Label label) {
		matching.remove(new Pair<Value, Label>(node,label));	
	}
	
	@Override
	public String toString() {
		return asSet().toString();
	}
}

