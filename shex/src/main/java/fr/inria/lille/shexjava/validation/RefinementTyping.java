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

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExpr;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExprRef;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExpr;
import fr.inria.lille.shexjava.util.CommonGraph;
import fr.inria.lille.shexjava.util.Pair;

/** The shape selected by default are the shapes of the set rules, the shapes that appear in a shape ref and the shapes that appear in a triple constraint.
 * 
 * @author Iovka Boneva
 */
public class RefinementTyping implements Typing {
	private ShexSchema schema;
	private Graph graph;
	private List<Set<Pair<RDFTerm, Label>>> theTyping;
	private Map<Label,Set<RDFTerm>> shapeTyping;
	private Map<RDFTerm,Set<Label>> valueTyping;
	
	private Set<Label> selectedShape;
	
	private Map<Pair<RDFTerm, Label>,List<Pair<Triple,Label>>> matching;

	public RefinementTyping(ShexSchema schema, Graph graph) {
		matching = new HashMap<Pair<RDFTerm, Label>,List<Pair<Triple,Label>>>();
		this.schema = schema;
		this.graph = graph;
		this.theTyping = new ArrayList<>(schema.getNbStratums());
		for (int i = 0; i < schema.getNbStratums(); i++) {
			theTyping.add(new HashSet<>());
		}
		initSelectedShape(Collections.emptySet());
	}
	
	public RefinementTyping(ShexSchema schema, Graph graph, Set<Label> extraShapes) {
		matching = new HashMap<Pair<RDFTerm, Label>,List<Pair<Triple,Label>>>();
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
	

	public void addAllLabelsFrom(int stratum, RDFTerm focusNode) {
		Set<Label> labels = schema.getStratum(stratum);
		Set<Pair<RDFTerm, Label>> set = theTyping.get(stratum);
		for (Label label: labels) {
			if (selectedShape.contains(label)) {
				for( RDFTerm node:CommonGraph.getAllNodes(graph))			
					set.add(new Pair<>(node, label));
				if (focusNode != null)
					set.add(new Pair<>(focusNode, label));
			}
		}
	}
	
	
	public Iterator<Pair<RDFTerm, Label>> typesIterator (int stratum) {
		return theTyping.get(stratum).iterator();
	}
	
	
	@Override
	public boolean contains (RDFTerm node, Label label) {
		return theTyping.get(schema.hasStratum(label)).contains(new Pair<>(node, label));
	}
	
	
	@Override
	public Set<Pair<RDFTerm, Label>> asSet() {
		Set<Pair<RDFTerm, Label>> set = new HashSet<>();
		for (Set<Pair<RDFTerm, Label>> subset : theTyping)
			set.addAll(subset);
		return set;
	}
	
	@Override
	public List<Pair<Triple, Label>> getMatch(RDFTerm node, Label label) {
		if (matching.containsKey(new Pair<RDFTerm, Label>(node,label)))
			return matching.get(new Pair<RDFTerm, Label>(node,label));		
		return null;
	}

	@Override
	public void setMatch(RDFTerm node, Label label, List<Pair<Triple, Label>> match) {
		matching.put(new Pair<RDFTerm, Label>(node,label), match);	
	}

	@Override
	public void removeMatch(RDFTerm node, Label label) {
		matching.remove(new Pair<RDFTerm, Label>(node,label));	
	}
	
	@Override
	public String toString() {
		return asSet().toString();
	}
}

