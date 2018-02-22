package fr.univLille.cristal.shex.graph;

import java.util.Iterator;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.util.JenaToRDF4J;
import fr.univLille.cristal.shex.util.RDF4JToJena;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.ResIterator;

public class JenaGraph extends AbstractRDFGraph {
	protected Model jenaModel;
	
	
	public JenaGraph(Model jenaModel) {
		this.jenaModel = jenaModel;
	}


	@Override
	public Iterator<Value> listAllObjectNodes() {
		return new Iterator<Value>() {
			NodeIterator it; { it = jenaModel.listObjects();	}
			
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public Value next() {
				return JenaToRDF4J.convertJenaRDFNodeToValue(it.next());
			}
		};
	}


	@Override
	public Iterator<Value> listAllSubjectNodes() {
		return new Iterator<Value>() {
			ResIterator it; { it = jenaModel.listSubjects();	}
			
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public Value next() {
				return JenaToRDF4J.convertJenaRDFNodeToValue(it.next());
			}
		};
	}


	@Override
	protected Iterator<NeighborTriple> itOutNeighbours(Value focusNode, IRI predicate) {
		return new Iterator<NeighborTriple>() {
			NodeIterator it; { it = jenaModel.listObjectsOfProperty(RDF4JToJena.convertRDF4JValueToJenaRDFNode(focusNode),
																	RDF4JToJena.convertRDF4JIRIToJenaProperty(predicate));	}
			TCProperty prop; {prop = TCProperty.createFwProperty(predicate);}
			
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public NeighborTriple next() {
				return new NeighborTriple(focusNode,prop,JenaToRDF4J.convertJenaRDFNodeToValue(it.next()));
			}
		};
	}


	@Override
	protected Iterator<NeighborTriple> itInNeighbours(Value focusNode, IRI predicate) {
		return new Iterator<NeighborTriple>() {
			ResIterator it; { it = jenaModel.listResourcesWithProperty(RDF4JToJena.convertRDF4JIRIToJenaProperty(predicate),
																	   RDF4JToJena.convertRDF4JValueToJenaRDFNode(focusNode));	}
			TCProperty prop; {prop = TCProperty.createFwProperty(predicate);}
			
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public NeighborTriple next() {
				return new NeighborTriple(JenaToRDF4J.convertJenaRDFNodeToValue(it.next()),prop,focusNode);
			}
		};
	}

	

	
	
	
}
