package fr.inria.lille.shexjava.validation;

import java.util.List;
import java.util.Map;

import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;

/** A data structure. 
 * Represents a bi-partition of a set of triples (typically the neighbourhood of some node) between triples that match some triple constraints, and triples that do not. 
 * 
 * @author Iovka Boneva
 * 3 août 2018
 */
public class PreMatching {

	private List<Triple> unmatchedTriples;
	private Map<Triple, List<TripleConstraint>> preMatching;
	
	public PreMatching(List<Triple> unmatchedTriples, Map<Triple, List<TripleConstraint>> preMatching) {
		super();
		this.unmatchedTriples = unmatchedTriples;
		this.preMatching = preMatching;
	}

	/** The triples that match none of the triple constraints. */
	public final List<Triple> getUnmatchedTriples() {
		return unmatchedTriples;
	}

	/** With every triple associates the triple constraints that this triple matches. */
	public final Map<Triple, List<TripleConstraint>> getPreMatching() {
		return preMatching;
	}
	
	
	
	
}
