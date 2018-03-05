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
package fr.univLille.cristal.shex.graph;

import java.util.Iterator;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;

/** Wraps an RDF4J graph as {@link RDFGraph}.
 * 
 * @author Iovka Boneva
 * @author Jérémie Dusart
 */
public class RDF4JGraph extends AbstractRDFGraph {
	
	private Model rdf4jModel;
	
	public RDF4JGraph(Model rdf4jModel) {
		super();
		this.rdf4jModel = rdf4jModel;
	}
	
	@Override
	public Iterator<Value> listAllSubjectNodes() {
		return new Iterator<Value>() {
			Iterator<Resource> it; { it = rdf4jModel.subjects().iterator();	}
			
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public Value next() {
				return it.next();
			}
		};
	}
	
	@Override
	public Iterator<Value> listAllObjectNodes() {
		return rdf4jModel.objects().iterator();
	}
	
	

	@Override
	protected Iterator<NeighborTriple> itOutNeighbours(Value focusNode,IRI predicate) {
		if (focusNode instanceof Resource) {
			return new Iterator<NeighborTriple>() {
	
				Iterator<Statement> it = rdf4jModel.filter((Resource) focusNode, predicate, null).iterator();
				
				@Override
				public boolean hasNext() {
					return it.hasNext();
				}
	
				@Override
				public NeighborTriple next() {
					return newFwTriple(it.next());
				}
			};
		}else {
			return new Iterator<NeighborTriple>() {				
				@Override
				public boolean hasNext() {
					return false;
				}
	
				@Override
				public NeighborTriple next() {
					return null;
				}
			};
		}
	}

	@Override
	protected Iterator<NeighborTriple> itInNeighbours(Value focusNode,IRI predicate) {
		return new Iterator<NeighborTriple>() {

			Iterator<Statement> it = rdf4jModel.filter(null, predicate, focusNode).iterator();
				
			
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
	
	@Override
	public String toString() {
		return rdf4jModel.toString();
	}

}
