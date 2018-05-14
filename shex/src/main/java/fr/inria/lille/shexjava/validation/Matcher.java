/*******************************************************************************
 * Copyright (C) 2018 Université de Lille - Inria
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package fr.inria.lille.shexjava.validation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;

/** Defines a custom condition on whether a neighbor triple matches a triple constraint.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public abstract class Matcher {

	/** Constructs a list that for all neighbor triple contains the list of triple constraints that the triple matches according to the matcher given as parameter.
	 * 
	 * @param neighbourhood
	 * @param constraints
	 * @param matcher
	 */
	public LinkedHashMap<Triple,List<TripleConstraint>> collectMatchingTC (RDFTerm focusNode, List<Triple> neighbourhood, List<TripleConstraint> constraints) {
		
		LinkedHashMap<Triple,List<TripleConstraint>> result = new LinkedHashMap<>(neighbourhood.size()); 
		
		for (Triple triple: neighbourhood) {
			ArrayList<TripleConstraint> matching = new ArrayList<>();
			for (TripleConstraint tc: constraints) {
				if (apply(focusNode,triple, tc)) {
					matching.add(tc);
				}
			}	
			result.put(triple,matching);
		}
		return result;
	}
	
	public abstract boolean apply(RDFTerm focusNode, Triple triple, TripleConstraint tc);
	
	
}
