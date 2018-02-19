package fr.univLille.cristal.shex.schema.concrsynt;

import java.util.Collections;
import java.util.Set;

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.abstrsynt.PropertySet;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class SingletonPropertySet implements PropertySet {

	private final TCProperty prop;
	
	public SingletonPropertySet(TCProperty prop) {
		this.prop = prop;
	}
	
	public TCProperty getProperty(){
		return prop;
	}
	
	@Override
	public boolean contains(TCProperty p) {
		return prop.equals(p);
	}

	@Override
	public String toString() {
		return prop + "";
	}

	@Override
	public Set<TCProperty> getAsFiniteSet() {
		return Collections.singleton(prop);
	}

	@Override
	public Set<TCProperty> getComplementAsFiniteSet() {
		return null;
	}

}
