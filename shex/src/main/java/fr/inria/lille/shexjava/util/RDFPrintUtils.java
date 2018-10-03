package fr.inria.lille.shexjava.util;

import java.util.Map;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDFTerm;

public class RDFPrintUtils {
	public static String toPrettyString(RDFTerm node,Map<String, String> prefixes) {
		if (node instanceof IRI) {
			IRI iri = (IRI) node;
			if (iri.getIRIString().toLowerCase().equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"))
				return "a";
			for (String prefix:prefixes.keySet()) {
				if (iri.getIRIString().startsWith(prefixes.get(prefix)))
					return prefix+(iri.getIRIString().substring(prefixes.get(prefix).length()));
			}
			return iri.ntriplesString();
		}
		return node.ntriplesString();
	}
}
