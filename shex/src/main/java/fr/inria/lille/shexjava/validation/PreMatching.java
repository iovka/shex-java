package fr.inria.lille.shexjava.validation;

import java.util.List;
import java.util.Map;

import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;

/** A data structure. 
 * Represents a tri-partition of a set of triples (typically the neighbourhood of some node).
 * Triples that match some of the triple constraints are those that appear as keys in {@link #getPreMatching()} and are associated with non-empty list of triple constraints.
 * Triples that match some of the extra properties are in {@link #getMatchedToExtra()}.
 * Triples that match neither some triple constraint nor the extra properties are in {@link #getUnmatched()}.
 * 
 * @author Iovka Boneva
 * 3 août 2018
 */
public class PreMatching {

	private List<Triple> unmatched;
	private List<Triple> matchedToExtra;
	private Map<Triple, List<TripleConstraint>> preMatchingMap;
	
	public PreMatching(Map<Triple, List<TripleConstraint>> preMatching, List<Triple> matchedToExtra, List<Triple> unmatchedTriples) {
		super();
		this.unmatched = unmatchedTriples;
		this.preMatchingMap = preMatching;
		this.matchedToExtra = matchedToExtra;
	}

	/** The triples that match none of the triple constraints. */
	public final List<Triple> getUnmatched() {
		return unmatched;
	}

	/** With every triple associates the triple constraints that this triple matches. */
	public final Map<Triple, List<TripleConstraint>> getPreMatching() {
		return preMatchingMap;
	}

	public final List<Triple> getMatchedToExtra() {
		return matchedToExtra;
	}
	
	
	
	
}
