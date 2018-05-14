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
package fr.inria.lille.shexjava.schema.abstrsynt;

import org.apache.commons.rdf.api.IRI;

/** Represents a property with an orientation which is forward or backward.
 * Used as predicate in a {@link NeighborTriple}.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public class TCProperty{

	private IRI iri;
	private boolean isFwd;
	
	/** Creates a new forward property.
	 * 
	 * @param iri
	 * @return the create TCProperty
	 */
	public static TCProperty createFwProperty (IRI iri) {
		return new TCProperty(iri, true);
	}
	
	/** Creates a new backward property.
	 * 
	 * @param iri
	 * @return  the create TCProperty
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
	 * @return true if the TCProperty is forward
	 */
	public boolean isForward() {
		return isFwd;
	}
	
	/** Get the IRI. 
	 * 
	 * @return the encapsulated property. 
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
