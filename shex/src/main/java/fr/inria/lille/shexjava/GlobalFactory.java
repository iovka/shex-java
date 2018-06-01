package fr.inria.lille.shexjava;

import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.rdf4j.RDF4J;

public class GlobalFactory {
	public static RDF RDFFactory = new RDF4J();
	
	protected GlobalFactory() {
	}

}
