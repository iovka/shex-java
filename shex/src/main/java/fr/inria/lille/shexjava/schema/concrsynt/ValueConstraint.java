package fr.inria.lille.shexjava.schema.concrsynt;

import java.util.Map;

import org.apache.commons.rdf.api.RDFTerm;

public interface ValueConstraint {
	public boolean contains (RDFTerm node);
	public String toPrettyString();
	public String toPrettyString(Map<String,String> prefixes);
}
