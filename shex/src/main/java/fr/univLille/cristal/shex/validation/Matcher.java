package fr.univLille.cristal.shex.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import fr.univLille.cristal.shex.graph.NeighborTriple;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;

/** Defines a custom condition on whether a neighbor triple matches a triple constraint.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public interface Matcher extends BiFunction<NeighborTriple, TripleConstraint, Boolean> {

	/** Constructs a list that for all neighbor triple contains the list of triple constraints that the triple matches according to the matcher given as parameter.
	 * 
	 * @param neighbourhood
	 * @param constraints
	 * @param matcher
	 * @return
	 */
	public static Map<NeighborTriple,List<TripleConstraint>> collectMatchingTC (List<NeighborTriple> neighbourhood, List<TripleConstraint> constraints, Matcher matcher) {
		
		Map<NeighborTriple,List<TripleConstraint>> result = new HashMap<>(neighbourhood.size()); 
		
		for (NeighborTriple triple: neighbourhood) {
			ArrayList<TripleConstraint> matching = new ArrayList<>();
			for (TripleConstraint tc: constraints) {
				if (matcher.apply(triple, tc)) {
					matching.add(tc);
				}
			}
			
			result.put(triple,matching);
		}
		return result;
	}
	
}
