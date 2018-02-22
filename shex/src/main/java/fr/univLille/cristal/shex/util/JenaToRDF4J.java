package fr.univLille.cristal.shex.util;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;

public class JenaToRDF4J {
	private final static RDFFactory RDF_FACTORY = RDFFactory.getInstance();

	public static Value convertJenaRDFNodeToValue(org.apache.jena.rdf.model.RDFNode jenaRes) {
		if (jenaRes.isResource()) {
			return convertJenaRDFNodeToResource((org.apache.jena.rdf.model.Resource) jenaRes);
		}else {
			return convertJenaRDFNodeToLiteral((org.apache.jena.rdf.model.Literal) jenaRes);
		}
	}
	
	public static Resource convertJenaRDFNodeToResource(org.apache.jena.rdf.model.Resource jenaRes) {
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
}
