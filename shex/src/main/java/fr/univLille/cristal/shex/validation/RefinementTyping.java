/**
Copyright 2017 University of Lille

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/


package fr.univLille.cristal.shex.validation;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.graph.RDFGraph;
import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.util.Pair;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class RefinementTyping implements Typing {
	
	private ShexSchema schema;
	private RDFGraph graph;
	private List<Set<Pair<Value, ShapeExprLabel>>> theTyping;
	
	public RefinementTyping(ShexSchema schema, RDFGraph graph) {
		this.schema = schema;
		this.graph = graph;
		this.theTyping = new ArrayList<>(schema.getNbStratums());
		for (int i = 0; i < schema.getNbStratums(); i++) {
			theTyping.add(new HashSet<>());
		}
	}

	public void addAllLabelsFrom(int stratum, Value focusNode) {
		Set<ShapeExprLabel> labels = schema.getStratum(stratum);
		Set<Pair<Value, ShapeExprLabel>> set = theTyping.get(stratum);
		for (ShapeExprLabel label: labels) {
			for (Value res : graph.getAllNodes()) {
				set.add(new Pair<>(res, label));
			}
			if (focusNode != null)
				set.add(new Pair<>(focusNode, label));
		}
	}
	
	public Iterator<Pair<Value, ShapeExprLabel>> typesIterator (int stratum) {
		return theTyping.get(stratum).iterator();
	}
	
	@Override
	public boolean contains (Value node, ShapeExprLabel label) {
		return theTyping.get(schema.hasStratum(label)).contains(new Pair<>(node, label));
	}
	
	
	@Override
	public Set<Pair<Value, ShapeExprLabel>> asSet() {
		Set<Pair<Value, ShapeExprLabel>> set = new HashSet<>();
		for (Set<Pair<Value, ShapeExprLabel>> subset : theTyping)
			set.addAll(subset);
		
		return set;
	}
	
	@Override
	public String toString() {
		return asSet().toString();
	}
}

