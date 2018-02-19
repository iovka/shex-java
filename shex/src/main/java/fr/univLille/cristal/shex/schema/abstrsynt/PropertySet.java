package fr.univLille.cristal.shex.schema.abstrsynt;

import java.util.Set;

import fr.univLille.cristal.shex.graph.TCProperty;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public interface PropertySet {

	public boolean contains (TCProperty p);
	
	public Set<TCProperty> getAsFiniteSet();
	public Set<TCProperty> getComplementAsFiniteSet();
	
	
}
