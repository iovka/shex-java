package fr.inria.lille.shexjava.validation;

import java.util.List;
import java.util.Map;

import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;

/** A data structure. 
 * Represents a tri-partition of a set of triples (typically the neighbourhood of some node).
 * All the tirples appear as keys in {@link #getPreMatching}.
 * Triples that match some triples constraints are those are associated with a non-empty list of triple constraints in {@link #getPreMatching()}.
 * The remaining triples (i.e. those associated with an empty list in {@link #getPreMatching()} are split between those in {@link #getMatchedToExtra()} that match some extra property, and those in {@link #getUnmatched()} that match no exhca property. 
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
