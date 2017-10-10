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

package fr.univLille.cristal.shex.graph;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.jena.rdf.model.ResourceFactory;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

/** Wraps a Jena Model to be used as {@linkplain RDFGraph}.
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class JenaGraph extends AbstractRDFGraph {

	private Set<Resource> setResources;
	private org.apache.jena.rdf.model.Model jenaModel;
	
	public JenaGraph (org.apache.jena.rdf.model.Model jenaModel) {
		this.jenaModel = jenaModel;
	}
	
	private Resource asResource (org.apache.jena.rdf.model.Resource res) {
		if (res.isAnon()) 
			return SimpleValueFactory.getInstance().createBNode(res.toString());
		else
			return SimpleValueFactory.getInstance().createIRI(res.toString());
	}
	
	private IRI asIRI (org.apache.jena.rdf.model.Property prop) {
		return SimpleValueFactory.getInstance().createIRI(prop.getURI());
	}
	
	private Value asValue (org.apache.jena.rdf.model.RDFNode node) {
		if (node.isAnon())
			return SimpleValueFactory.getInstance().createBNode(node.toString());
		else if (node.isLiteral()) {
			org.apache.jena.rdf.model.Literal lit = node.asLiteral();
			return SimpleValueFactory.getInstance().createLiteral(lit.getLexicalForm(), SimpleValueFactory.getInstance().createIRI(lit.getDatatypeURI()));
		} else 
			return SimpleValueFactory.getInstance().createIRI(node.asResource().toString());
	}
	
	@Override
	public Set<Resource> getAllResources() {
		if (setResources == null) {
			setResources = new HashSet<>();
			org.apache.jena.rdf.model.ResIterator rit = jenaModel.listSubjects();
			while (rit.hasNext()) {
				setResources.add(asResource(rit.next()));
			}
			org.apache.jena.rdf.model.NodeIterator nit = jenaModel.listObjects();
			while (nit.hasNext()) {
				Value node = asValue(nit.next());
				if (node instanceof Resource) setResources.add((Resource) node);
			}
			setResources = Collections.unmodifiableSet(setResources);
		}
		return setResources;
	}

	@Override
	protected Iterator<NeighborTriple> itOutNeighbours(Resource focusNode) {
		return new Iterator<NeighborTriple>() {

			org.apache.jena.rdf.model.Resource focusAsJenaResource = ResourceFactory.createResource(focusNode.stringValue());
			
			Iterator<org.apache.jena.rdf.model.Statement> itFw = 
					jenaModel.listStatements(focusAsJenaResource, null, (org.apache.jena.rdf.model.RDFNode) null);
			
			@Override
			public boolean hasNext() {
				return itFw.hasNext();
			}
			
			@Override
			public NeighborTriple next() {
				return newFwTriple(itFw.next());
			}
			
		};
	}

	@Override
	protected Iterator<NeighborTriple> itInNeighbours(Value focusNode) {
		return new Iterator<NeighborTriple>() {
			
			org.apache.jena.rdf.model.RDFNode focusAsJenaNode = (org.apache.jena.rdf.model.RDFNode) ((Encapsulator<?>) focusNode).internalValue;
			

			Iterator<org.apache.jena.rdf.model.Statement> itBw = jenaModel.listStatements(null, null, focusAsJenaNode);
			
			@Override
			public boolean hasNext() {
				return itBw.hasNext();
			}
			
			@Override
			public NeighborTriple next() {
				return newBwTriple(itBw.next());
			}
			
		};
	}
	
	private NeighborTriple newFwTriple (org.apache.jena.rdf.model.Statement stmt) {
		
		Value focus = asResource(stmt.getSubject());
		Value opposite = asValue(stmt.getObject());
		TCProperty prop = TCProperty.createFwProperty(asIRI(stmt.getPredicate()));
		return new NeighborTriple(focus, prop, opposite);
	
	}
	
	private NeighborTriple newBwTriple (org.apache.jena.rdf.model.Statement stmt) {
		Value opposite = asResource(stmt.getSubject());
		Value focus = asValue(stmt.getObject());
		TCProperty prop = TCProperty.createInvProperty(asIRI(stmt.getPredicate()));
		return new NeighborTriple(focus, prop, opposite);
	}

	@Override
	public String toString() {
		return jenaModel.toString();
	}
	
}
