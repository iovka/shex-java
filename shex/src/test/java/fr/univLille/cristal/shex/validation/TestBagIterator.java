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

import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.tc;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TestBagIterator {
	
	@Test
	public void testEmptyNeighbourhood() {
		List<List<TripleConstraint>> emptyNeighbourhoodList = new ArrayList<>();
		BagIterator it =  new BagIterator(emptyNeighbourhoodList);
		
		assertTrue(it.hasNext());
		Bag bag = it.next();
		assertTrue(bag.getMap().isEmpty());
		assertFalse(it.hasNext());
	}

	@Test
	public void testOneEmptyMatch_1() {
		TripleConstraint tca = (TripleConstraint) tc("a :: .");
		TripleConstraint tcb = (TripleConstraint) tc("b :: .");
		
		List<TripleConstraint> matchOne = new ArrayList<>();
		matchOne.add(tca); matchOne.add(tcb);
		List<List<TripleConstraint>> allMatchesList = new ArrayList<>();
		allMatchesList.add(new ArrayList<>());
		allMatchesList.add(matchOne);
		
		BagIterator it = new BagIterator(allMatchesList);
		assertFalse(it.hasNext());
	}
	
	@Test
	public void testOneEmptyMatch_2() {
		TripleConstraint tca = (TripleConstraint) tc("a :: .");
		TripleConstraint tcb = (TripleConstraint) tc("b :: .");
		
		List<TripleConstraint> matchOne = new ArrayList<>();
		matchOne.add(tca); matchOne.add(tcb);
		List<List<TripleConstraint>> allMatchesList = new ArrayList<>();
		allMatchesList.add(matchOne);
		allMatchesList.add(new ArrayList<>());
		
		BagIterator it = new BagIterator(allMatchesList);
		assertFalse(it.hasNext());
	}
	
	@Test
	public void testGeneralCase() {
		TripleConstraint tca = (TripleConstraint) tc("a :: .");
		TripleConstraint tcb = (TripleConstraint) tc("b :: .");
		TripleConstraint tcc = (TripleConstraint) tc("c :: .");
		
		List<TripleConstraint> matchOne = new ArrayList<>();
		matchOne.add(tca); matchOne.add(tcb);
		List<TripleConstraint> matchTwo = new ArrayList<>();
		matchTwo.add(tca); matchTwo.add(tcc);
		
		List<List<TripleConstraint>> allMatchesList = new ArrayList<>();
		allMatchesList.add(matchOne);
		allMatchesList.add(matchTwo);
		
		BagIterator it = new BagIterator(allMatchesList);
		List<Bag> allBags = new ArrayList<>();
		while (it.hasNext()) {
			allBags.add(it.next());
		}
		assertEquals(4, allBags.size());
		for (Bag bag: allBags) {
			boolean isOneOf = false;
			if (bag.getMult(tca) == 2 && bag.getMult(tcb) == 0 && bag.getMult(tcc) == 0)
				isOneOf = true;
			if (bag.getMult(tca) == 1 && bag.getMult(tcb) == 1 && bag.getMult(tcc) == 0)
				isOneOf = true;
			if (bag.getMult(tca) == 1 && bag.getMult(tcb) == 0 && bag.getMult(tcc) == 1)
				isOneOf = true;
			if (bag.getMult(tca) == 0 && bag.getMult(tcb) == 1 && bag.getMult(tcc) == 1)
				isOneOf = true;
			assertTrue(isOneOf);
		}
	}
	
	
}
