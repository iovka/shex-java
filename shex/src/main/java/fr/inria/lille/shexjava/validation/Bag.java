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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import org.apache.commons.rdf.api.Triple;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class Bag {

	public static Bag fromMatching (Map<Triple, TripleConstraint> matching) {
		Bag bag = new Bag();
		for (TripleConstraint tc : matching.values())
			bag.increment(tc);
		return bag;
	}

	private Map<TripleConstraint, Integer> theMap;

	public Bag(){
		theMap = new HashMap<>();
	}

	public void increment(TripleConstraint tripleConstraint){
		if (theMap.containsKey(tripleConstraint))
			theMap.put(tripleConstraint, theMap.get(tripleConstraint) + 1);
		else
			theMap.put(tripleConstraint, 1);
	}

	public int getMult(TripleConstraint tripleConstraint){
		if (theMap.containsKey(tripleConstraint))
			return theMap.get(tripleConstraint);
		else 
			return 0;
	}

	public Set<TripleConstraint> alphabet() {
		return Collections.unmodifiableSet(theMap.keySet());
	}

	public String toString(){
		return "Bag[" + theMap.toString() + "]";
	}
	
	protected Map<TripleConstraint, Integer> getMap() {
		return Collections.unmodifiableMap(theMap);
	}
}
