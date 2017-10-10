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

package fr.univLille.cristal.shex.schema.concrsynt;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.abstrsynt.PropertySet;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class ForwardComplementPropertySet implements PropertySet {
	
	private Set<TCProperty> complement;
	
	public ForwardComplementPropertySet(Set<TCProperty> complementSet) {
		Set<TCProperty> forwardOnly = complementSet.stream().filter(prop -> prop.isForward()).collect(Collectors.toSet());
		this.complement = Collections.unmodifiableSet(forwardOnly);
	}

	@Override
	public boolean contains(TCProperty p) {
		return p.isForward() && ! complement.contains(p);
	}
	
	@Override
	public String toString() {
		return String.format("FwdCompl(%s)", complement.toString());
	}

	@Override
	public Set<TCProperty> getAsFiniteSet() {
		return null;
	}

	@Override
	public Set<TCProperty> getComplementAsFiniteSet() {
		return complement;
	}

}
