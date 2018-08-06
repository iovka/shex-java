package fr.inria.lille.shexjava.validation;

import java.util.List;

import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.Pair;

/** Data structure. 
 * Similarly to {@link PreMatching}, defines a tri-partition of a set of triples.
 * Unlike {@link PreMatching}, the matched triples are given as a list of {@link Pair<{@link Triple}, {@link Label}>}, which contains only the triples that were effectively matched to some triple constraint and the label of that triple constraint.
 * 
 * @author Iovka Boneva
 * 6 août 2018
 */
public class LocalMatching {
	// TODO: see whether we want to implement it differently

	private List<Pair<Triple, Label>> matching;
	private List<Triple> unmatched;
	private List<Triple> matchedToExtra;	
	
	public LocalMatching (List<Pair<Triple, Label>> matching, List<Triple> matchedToExtra, List<Triple> unmatched) {
		this.matching = matching;
		this.matchedToExtra = matchedToExtra;
		this.unmatched = unmatched;
	}


	public final List<Pair<Triple, Label>> getMatching() {
		return matching;
	}

	public final List<Triple> getUnmatched() {
		return unmatched;
	}

	public final List<Triple> getMatchedToExtra() {
		return matchedToExtra;
	}
	
	
	
	
}
