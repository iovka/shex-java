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

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class BagIterator implements Iterator<Bag>{

	private List<List<TripleConstraint>> allMatches;
	private int[] currentIndexes;
	private int[] sizes;
	
	public BagIterator(List<List<TripleConstraint>> allMatches) {
		this.allMatches = allMatches;
		currentIndexes = new int[allMatches.size()+1]; // Adding an artificial first column allows to write more easily all the operations
		sizes = new int[allMatches.size()+1];
		for (int i = 0; i < currentIndexes.length-1; i++) {
			currentIndexes[i+1] = 0;
			sizes[i+1] = allMatches.get(i).size();
		}
		currentIndexes[0] = 0;
		sizes[0] = 1;
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
}
