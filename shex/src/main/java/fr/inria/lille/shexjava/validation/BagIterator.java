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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;

/** Starting from a map that with every {@link Triple} associates a set of matching {@link TripleConstraint}s, allows to iterate over all possible ways to match every triple with a unique constraint.
 * For each such matching, the iterator returns the corresponding bag that with every triple constraint associates the number of matching triples. 
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class BagIterator implements Iterator<Bag>{

	/** The triples in the neighbourhood are used as index, their order in the list is important. */
	private List<Triple> neighbourhood;
	/** allMatches.get(i) contains all triple constraints matched with the triple neighbourhood.get(i) */
	private List<List<TripleConstraint>> allMatches;
	
	/** Used for the iteration: sizes[i] = allMatches.get(i).getSize() */
	private int[] sizes;
	/** Used for the iteration: 0 <= currentIndexes[i] < sizes[i] */
	private int[] currentIndexes;



	//	In the constructor, the following field will be initialize:
	//	 - neighbourhood the set of triples over which all matchings will be enumerated
	//	 - allMatches allMatches.get(i) contains the triple constraints matching with neighbourhood.get(i)
	//
	public BagIterator (Map<Triple, List<TripleConstraint>> preMatching) {
		neighbourhood = new ArrayList<>();
		allMatches = new ArrayList<>();

		for (Map.Entry<Triple, List<TripleConstraint>> e: preMatching.entrySet()) {
			neighbourhood.add(e.getKey());
			allMatches.add(e.getValue());
		}
		currentIndexes = new int[allMatches.size()+1]; // Adding an artificial first column allows to write more easily all the operations
		sizes = new int[allMatches.size()+1];
		for (int i = 0; i < currentIndexes.length-1; i++) {
			currentIndexes[i+1] = 0;
			sizes[i+1] = allMatches.get(i).size();
		}
		currentIndexes[0] = 0;
		sizes[0] = 1;
	}

	public BagIterator(PreMatching preMatching) {
		this(preMatching.getPreMatching());
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
	public Bag next() {
		if (! hasNext())
			throw new NoSuchElementException();
		
		Bag next = new Bag();
		for (int i = 1; i < currentIndexes.length; i++) {
			next.increment(allMatches.get(i-1).get(currentIndexes[i]));
		}
		
		goToNext();
		
		return next;
	}

	// TODO better implementation of getCurrentBag, like this (to be debugged)
	/*
	public Map<Triple, Label> getCurrentMatch() {
		Map<Triple, Label> currentMatch = new HashMap<>(neighbourhood.size()-1);

		for (int i = 1; i < neighbourhood.size(); i++) {
			currentMatch.put(neighbourhood.get(i), allMatches.get(i).get(currentIndexes[i]).getId());
		}
		return currentMatch;
	}
	*/


	public Map<Triple, Label> getCurrentBag(){
		Map<Triple, Label> currentMatch = new HashMap<>();

		Iterator<List<TripleConstraint>> ite = allMatches.iterator();
		Iterator<Triple> iteNeigh = neighbourhood.iterator();
		for (int i = 1; i < currentIndexes.length; i++) {
			List<TripleConstraint> tmp = ite.next();
			Triple tmp2 = iteNeigh.next();
			currentMatch.put(tmp2,tmp.get(currentIndexes[i]).getId());
		}

		return currentMatch;
	}

}
