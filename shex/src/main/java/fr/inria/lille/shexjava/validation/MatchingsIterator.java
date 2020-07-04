/* ******************************************************************************
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

import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import org.apache.commons.rdf.api.Triple;

import java.util.*;

/** Starting from a map that with every {@link Triple} associates a set (or list) of matching objects, allows to iterate over all possible ways to match every triple with a unique such object.
 * This is equivalent to computing a Cartesian product of the sets associated with the triples.
 * Typically used with {@param T} being a {@link TripleConstraint}, or also an expression that is extended by a shape.
 *
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class MatchingsIterator<T> implements Iterator<Matching<T>>{

	/** The triples in the neighbourhood are used as index, their order in the list is important. */
	private List<Triple> neighbourhood;
	/** allMatches.get(i) contains all triple constraints matched with the triple neighbourhood.get(i) */
	private List<List<T>> allMatching;

	/** Used for the iteration: sizes[i] = allMatches.get(i).getSize() */
	private int[] sizes;
	/** Used for the iteration: 0 <= currentIndexes[i] < sizes[i] */
	private int[] currentIndexes;

	/** Constructs the iterable object starting from the {@param preMatching} map, by restricting it only to those triples in {@param domain}. */
	public MatchingsIterator(Map<Triple, List<T>> preMatching, List<Triple> domain) {
		neighbourhood = new ArrayList<>(domain.size());
		allMatching = new ArrayList<>(domain.size());

		for (Triple e : domain) {
			neighbourhood.add(e);
			allMatching.add(preMatching.get(e));
		}
		currentIndexes = new int[allMatching.size()+1]; // Adding an artificial first column allows to write more easily all the operations
		sizes = new int[allMatching.size()+1];
		for (int i = 0; i < currentIndexes.length-1; i++) {
			currentIndexes[i+1] = 0;
			sizes[i+1] = allMatching.get(i).size();
		}
		currentIndexes[0] = 0;
		sizes[0] = 1;
	}

	/** Constructs the iterable object which domain is the whole key set of {@param preMatching}. */
	public MatchingsIterator(Map<Triple, List<T>> preMatching) {
		this(preMatching, new ArrayList<>(preMatching.keySet()));
	}

	@Override
	public boolean hasNext() {
		for (int i = 0; i < currentIndexes.length; i++)
			if (currentIndexes[i] >= sizes[i])
				return false;
		return true;
	}
	
	private void goToNext () {
		int i = currentIndexes.length - 1;
		boolean incrementsToZero = true;
		while (i > 0 && incrementsToZero) {
			currentIndexes[i] = (currentIndexes[i]+1) % sizes[i];
			incrementsToZero = currentIndexes[i]==0;
			i--;
		}
		if (i == 0 && incrementsToZero)
			currentIndexes[0]++;
	}

	@Override
	public Matching<T> next() {
		if (! hasNext())
			throw new NoSuchElementException();
		
		Matching<T> next = new Matching<>();
		for (int i = 1; i < currentIndexes.length; i++) {
			next.put(neighbourhood.get(i-1), allMatching.get(i-1).get(currentIndexes[i]));
		}
		
		goToNext();
		
		return next;
	}
}
