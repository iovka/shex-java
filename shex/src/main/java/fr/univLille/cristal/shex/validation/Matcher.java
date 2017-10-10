/**
Copyright 2017 University of Lille

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/


package fr.univLille.cristal.shex.validation;

import java.util.ArrayList;
import java.util.List;
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
	public static List<List<TripleConstraint>> collectMatchingTC (List<NeighborTriple> neighbourhood, List<TripleConstraint> constraints, Matcher matcher) {
		
		ArrayList<List<TripleConstraint>> result = new ArrayList<>(neighbourhood.size()); 
		
		for (NeighborTriple triple: neighbourhood) {
			ArrayList<TripleConstraint> matching = new ArrayList<>();
			for (TripleConstraint tc: constraints) {
				if (matcher.apply(triple, tc)) {
					matching.add(tc);
				}
			}
			
			result.add(matching);
		}
		return result;
	}
	
}
