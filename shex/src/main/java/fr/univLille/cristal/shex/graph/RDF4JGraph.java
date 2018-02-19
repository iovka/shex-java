package fr.univLille.cristal.shex.graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;

/** Wraps an RDF4J graph as {@link RDFGraph}.
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class RDF4JGraph extends AbstractRDFGraph {
	
	private Model rdf4jModel;
	private Set<Value> allSubjects=null;
	private Set<Value> allObjects=null;
	private Set<Value> allResources=null;
	
	public RDF4JGraph(Model rdf4jModel) {
		super();
		this.rdf4jModel = rdf4jModel;
	}
	
	@Override
	public Set<Value> getAllSubjectNodes() {
		if (allSubjects != null)
			return allSubjects;
		
		allSubjects = new HashSet<Value>();
		allSubjects.addAll(rdf4jModel.subjects());
		return allSubjects;
	}
	
	@Override
	public Set<Value> getAllObjectNodes() {
		if (allObjects != null)
			return allObjects;
		
		allObjects = new HashSet<Value>();
		allObjects.addAll(rdf4jModel.objects());
		return allObjects;
	}
	
	@Override
	public Set<Value> getAllNodes() {
		if (allResources != null)
			return allResources;
		
		allResources = new HashSet<Value>();
		allResources.addAll(rdf4jModel.objects());
		allResources.addAll(rdf4jModel.subjects());
		return allResources;
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

}
