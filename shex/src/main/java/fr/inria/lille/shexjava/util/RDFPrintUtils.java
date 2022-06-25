package fr.inria.lille.shexjava.util;

import java.util.Map;

import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.api.RDFTerm;

public class RDFPrintUtils {

	public static String toPrettyString(RDFTerm node,Map<String, String> prefixes) {
		if (node instanceof BlankNode)
			return node.ntriplesString();
		if (node instanceof IRI) {
			IRI iri = (IRI) node;
			if (iri.getIRIString().toLowerCase().equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"))
				return "a";
			String bestPrefix = null;
			for (String prefix:prefixes.keySet()) {
				if (iri.getIRIString().startsWith(prefixes.get(prefix)))
					if (bestPrefix == null || prefixes.get(bestPrefix).length()<prefixes.get(prefix).length())
						bestPrefix = prefix;
			}
			if (bestPrefix!=null)
				return bestPrefix+(iri.getIRIString().substring(prefixes.get(bestPrefix).length()));

			return iri.getIRIString();
		}
		Literal lit = (Literal) node;
		if (lit.getLanguageTag().isPresent())
			return node.ntriplesString();
		String type = toPrettyString(lit.getDatatype(), prefixes);
		return "\""+lit.getLexicalForm()+"\"^^"+type;
	}
}
