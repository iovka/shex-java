package fr.inria.lille.shexjava.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.Pair;

public class MatchingCollector {
	private Map<Pair<RDFTerm, Label>,List<Pair<Triple,Label>>> matching;

	public MatchingCollector() {
		matching = new HashMap<>();
	}
	
	public void setMatch(RDFTerm node, Label label, List<Pair<Triple, Label>> match) {
		matching.put(new Pair<RDFTerm, Label>(node,label), match);	
	}
	
	public List<Pair<Triple, Label>> getMatch(RDFTerm node, Label label) {
		return matching.get(new Pair<RDFTerm, Label>(node,label));		
	}

}
