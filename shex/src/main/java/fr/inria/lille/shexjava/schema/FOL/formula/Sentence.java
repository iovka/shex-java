package fr.inria.lille.shexjava.schema.FOL.formula;

import java.util.Map;
import java.util.Set;

import org.apache.commons.rdf.api.RDFTerm;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.Pair;

public interface Sentence {
	/** This function return 0 if the result is false, 1 if the result is true, 2 if the affectation is incomplete. 
	 * @throws Exception if operator on a literal with a datatype not supported.
	 * 
	 */
	public int evaluate(Map<Variable, RDFTerm> affectations,
							Set<Pair<RDFTerm, Label>> shapes,
							Set<Pair<Pair<RDFTerm, RDFTerm>, Label>> triples) throws Exception;
}
