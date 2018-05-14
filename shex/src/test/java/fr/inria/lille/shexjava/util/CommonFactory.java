package fr.inria.lille.shexjava.util;

import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.simple.SimpleRDF;

public class CommonFactory extends SimpleRDF{

	@Override
	public BlankNode createBlankNode(final String name) {
        return new MyBlankNodeImpl(name);
}
	
}
