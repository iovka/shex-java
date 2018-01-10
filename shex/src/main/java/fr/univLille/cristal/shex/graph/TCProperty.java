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

package fr.univLille.cristal.shex.graph;

import org.eclipse.rdf4j.model.IRI;

/** Represents a property with an orientation which is forward or backward.
 * Used as predicate in a {@link NeighborTriple}.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public class TCProperty {

	private IRI iri;
	private boolean isFwd;
	
	/** Creates a new forward property.
	 * 
	 * @param iri
	 * @return
	 */
	public static TCProperty createFwProperty (IRI iri) {
		return new TCProperty(iri, true);
	}
	
	/** Creates a new backward property.
	 * 
	 * @param iri
	 * @return
	 */
	public static TCProperty createInvProperty (IRI iri) {
		return new TCProperty(iri, false);
	}
	
	private TCProperty (IRI iri, boolean isFwd) {
		this.iri = iri;
		this.isFwd = isFwd;
	}

	/** Tests whether the property is forward.
	 * 
	 * @return
	 */
	public boolean isForward() {
		return isFwd;
	}
	
	/** The encapsulated property. 
	 * 
	 * @return
	 */
	public IRI getIri() {
		return iri;
	}
	
	@Override
	public String toString() {
		return (isFwd ? "" : "^") + iri; 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isFwd ? 1231 : 1237);
		result = prime * result
				+ ((iri == null) ? 0 : iri.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TCProperty other = (TCProperty) obj;
		if (isFwd != other.isFwd)
			return false;
		if (iri == null) {
			if (other.iri != null)
				return false;
		} else if (!iri.equals(other.iri))
			return false;
		return true;
	}
	
	
}
