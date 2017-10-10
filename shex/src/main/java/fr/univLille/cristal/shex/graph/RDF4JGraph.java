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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.util.Models;

/** Wraps an RDF4J graph as {@link RDFGraph}.
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class RDF4JGraph extends AbstractRDFGraph {
	
	private Model rdf4jModel;
	private Set<Resource> allResources;
	
	public RDF4JGraph(Model rdf4jModel) {
		super();
		this.rdf4jModel = rdf4jModel;
	}

	@Override
	public Set<Resource> getAllResources() {
		if (allResources != null)
			return allResources;
		
		allResources = new HashSet<>();
		allResources.addAll(Models.objectResources(rdf4jModel));
		allResources.addAll(Models.subjectBNodes(rdf4jModel));
		allResources.addAll(Models.subjectIRIs(rdf4jModel));
		return allResources;
	}

	@Override
	protected Iterator<NeighborTriple> itOutNeighbours(Resource focusNode) {
		return new Iterator<NeighborTriple>() {

			Iterator<Statement> it = rdf4jModel.filter(focusNode, null, null).iterator();
			
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public NeighborTriple next() {
				return newFwTriple(it.next());
			}
		};
	}

	@Override
	protected Iterator<NeighborTriple> itInNeighbours(Value focusNode) {
		return new Iterator<NeighborTriple>() {

			Iterator<Statement> it = rdf4jModel.filter(null, null, focusNode).iterator();
				
			
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public NeighborTriple next() {
				return newBwdTriple(it.next());
			}
		};
	}


	private NeighborTriple newFwTriple(Statement st) {
		return new NeighborTriple(st.getSubject(), TCProperty.createFwProperty(st.getPredicate()), st.getObject());
	}
		
	private NeighborTriple newBwdTriple(Statement st) {
		return new NeighborTriple(st.getObject(), TCProperty.createInvProperty(st.getPredicate()), st.getSubject());
	}
}
