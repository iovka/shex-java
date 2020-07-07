package fr.inria.lille.shexjava.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.util.Pair;

/** Data structure. 
 * Similarly to {@link PreMatching}, defines a tri-partition of a set of triples.
 * Unlike {@link PreMatching}, the matched triples are given as a list of {@link Pair <{@link Triple}, {@link Label}>}.
 * 
 * @author Iovka Boneva
 * 6 août 2018
 */
public class LocalMatching {
	// TODO: see whether we want to implement it differently
	// TODO having null for the matching to indicate that there is no matching is not satisfactory

	private Map<Triple, Label> matching;
	private List<Triple> unmatched;
	private List<Triple> matchedToExtra;	
	
	public LocalMatching (Map<Triple, TripleConstraint> matching, List<Triple> matchedToExtra, List<Triple> unmatched) {
		this.matching = new HashMap<>(matching.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getId())));
		this.matchedToExtra = matchedToExtra;
		this.unmatched = unmatched;
	}


	public final Map<Triple, Label> getMatching() {
		return matching;
	}

	public final List<Triple> getUnmatched() {
		return unmatched;
	}

	public final List<Triple> getMatchedToExtra() {
		return matchedToExtra;
	}
	
	
	
	
}
