package fr.inria.lille.shexjava.validation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;

/** Contains static methods useful in the different validation alogorithms.
 * 
 * @author Iovka Boneva
 * @author Jérémie Dusart
 * 3 août 2018
 */
public class ValidationUtils {
	
	public static Matcher getPredicateOnlyMatcher () {
		return predicateOnlyMatcher;
	}
	
	public static Matcher getPredicateAndValueMatcher (Typing typing) {
		return new MatcherPredicateAndValue2(typing);
	}
	
	public static PreMatching computePreMatching(RDFTerm focusNode, List<Triple> neighbourhood, List<TripleConstraint> tripleConstraints, Matcher matcher) {
		
		LinkedHashMap<Triple,List<TripleConstraint>> matchingTriplesMap = new LinkedHashMap<>(neighbourhood.size());
		ArrayList<Triple> unmatchedTriples = new ArrayList<>();
		
		for (Triple triple: neighbourhood) {
			ArrayList<TripleConstraint> matching = new ArrayList<>();
			for (TripleConstraint tc: tripleConstraints) {
				if (matcher.apply(focusNode, triple, tc)) {
					matching.add(tc);
				}
			}
			if (matching.isEmpty())
				unmatchedTriples.add(triple);
			else 
				matchingTriplesMap.put(triple,matching);
		}
		return new PreMatching(unmatchedTriples, matchingTriplesMap);
	}

	private static Matcher predicateOnlyMatcher = new Matcher() {
		@Override
		public boolean apply(RDFTerm focusNode, Triple triple, TripleConstraint tc) {
			if (tc.getProperty().isForward() && triple.getSubject().ntriplesString().equals(focusNode.ntriplesString())) {
				return tc.getProperty().getIri().ntriplesString().equals(triple.getPredicate().ntriplesString());
			}
			if (!tc.getProperty().isForward() && triple.getObject().ntriplesString().equals(focusNode.ntriplesString()))
				return tc.getProperty().getIri().ntriplesString().equals(triple.getPredicate().ntriplesString());
			return false;
		}
	};
	
	// TODO remove the 2 when the original is removed
	private static class MatcherPredicateAndValue2 extends Matcher {
		private Typing shapeMap;
		
		public MatcherPredicateAndValue2(Typing shapeMap) {
			this.shapeMap = shapeMap;
		}
		
		@Override
		public boolean apply(RDFTerm focusNode, Triple triple, TripleConstraint tc) {
			if (tc.getProperty().isForward() && triple.getSubject().ntriplesString().equals(focusNode.ntriplesString()))
				if (tc.getProperty().getIri().ntriplesString().equals(triple.getPredicate().ntriplesString())) 
					return shapeMap.isConformant(triple.getObject(), tc.getShapeExpr().getId());
			if (!tc.getProperty().isForward() && triple.getObject().ntriplesString().equals(focusNode.ntriplesString()))
				if (tc.getProperty().getIri().ntriplesString().equals(triple.getPredicate().ntriplesString())) 
					return shapeMap.isConformant(triple.getSubject(), tc.getShapeExpr().getId());
			return false;
		}
	}
	
	private ValidationUtils () {}
}
