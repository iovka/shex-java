package fr.univLille.cristal.shex.validation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class Bag {

	private Map<TripleConstraint, Integer> theMap;

	public Bag(){
		theMap = new HashMap<TripleConstraint, Integer>();
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
