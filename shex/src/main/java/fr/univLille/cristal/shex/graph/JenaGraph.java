package fr.univLille.cristal.shex.graph;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.util.RDFFactory;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.StmtIterator;

public class JenaGraph extends AbstractRDFGraph {
	private final static RDFFactory RDF_FACTORY = RDFFactory.getInstance();
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
				return convertJenaRDFNodeToValue(it.next());
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
				return convertJenaRDFNodeToValue(it.next());
			}
		};
	}


	@Override
	protected Iterator<NeighborTriple> itOutNeighbours(Value focusNode, IRI predicate) {
		return new Iterator<NeighborTriple>() {
			NodeIterator it; { if (convertRDF4JValueToJenaResource(focusNode)==null) {
						it = null;
					}else {
						it = jenaModel.listObjectsOfProperty(convertRDF4JValueToJenaResource(focusNode),
															convertRDF4JIRIToJenaProperty(predicate));	
						}
					}
			TCProperty prop; {prop = TCProperty.createFwProperty(predicate);}
			
			@Override
			public boolean hasNext() {
				if (it == null)
					return false;
				return it.hasNext();
			}

			@Override
			public NeighborTriple next() {
				if (it == null)
					throw new NoSuchElementException();
				return new NeighborTriple(focusNode,prop,convertJenaRDFNodeToValue(it.next()));
			}
		};
	}


	@Override
	protected Iterator<NeighborTriple> itInNeighbours(Value focusNode, IRI predicate) {
		return new Iterator<NeighborTriple>() {
			ResIterator it; { it = jenaModel.listResourcesWithProperty(convertRDF4JIRIToJenaProperty(predicate),
																	   convertRDF4JValueToJenaRDFNode(focusNode));	}
			TCProperty prop; {prop = TCProperty.createFwProperty(predicate);}
			
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public NeighborTriple next() {
				return new NeighborTriple(convertJenaRDFNodeToValue(it.next()),prop,focusNode);
			}
		};
	}

	
	//----------------------------------------------
	// Conversion RDF4J to Jena
	//----------------------------------------------
	
	public org.apache.jena.rdf.model.Property convertRDF4JIRIToJenaProperty(IRI predicate) {
		return jenaModel.createProperty(predicate.stringValue());
	}
	
	public org.apache.jena.rdf.model.Resource convertRDF4JValueToJenaResource(Value value) {
		if (value instanceof IRI)
			return jenaModel.createResource(value.stringValue());
		if (value instanceof BNode)
			return jenaModel.createResource(value.stringValue());
		if (value instanceof Literal)
			return null;
		System.out.println("Need to convert from RDF4J: "+value.getClass()+" > "+value);
		
		return null;
	}
	
	public org.apache.jena.rdf.model.RDFNode convertRDF4JValueToJenaRDFNode(Value value) {
		if (value instanceof Literal) {
			Literal lvalue = (Literal) value;
			if (lvalue.getLanguage().isPresent())
				return jenaModel.createLiteral(lvalue.stringValue(), lvalue.getLanguage().get());
			return jenaModel.createTypedLiteral(lvalue.stringValue(), lvalue.getDatatype().stringValue());
		} else {
			return convertRDF4JValueToJenaResource(value);
		}
	}
	
	//----------------------------------------------
	// Conversion Jena to RDF4J
	//----------------------------------------------
	
	public Value convertJenaRDFNodeToValue(org.apache.jena.rdf.model.RDFNode jenaRes) {
		if (jenaRes.isResource()) {
			return convertJenaRDFNodeToResource((org.apache.jena.rdf.model.Resource) jenaRes);
		}else {
			return convertJenaRDFNodeToLiteral((org.apache.jena.rdf.model.Literal) jenaRes);
		}
	}
	
	public Resource convertJenaRDFNodeToResource(org.apache.jena.rdf.model.Resource jenaRes) {
		if (jenaRes.isAnon())
			return RDF_FACTORY.createBNode();
		if (jenaRes.getNameSpace().equals("_:"))
			return RDF_FACTORY.createBNode(jenaRes.getLocalName());
		return RDF_FACTORY.createIRI(jenaRes.getURI());
	}
	
	public static Literal convertJenaRDFNodeToLiteral(org.apache.jena.rdf.model.Literal jenaLit) {
		String value = jenaLit.getLexicalForm();
		String lang = jenaLit.getLanguage();
		if (!lang.equals(""))
			return RDF_FACTORY.createLiteral(value, lang);
		return  RDF_FACTORY.createLiteral(value, RDF_FACTORY.createIRI(jenaLit.getDatatypeURI()));			
	}
	
	@Override
	public String toString() {
		return jenaModel.toString();
	}
}
