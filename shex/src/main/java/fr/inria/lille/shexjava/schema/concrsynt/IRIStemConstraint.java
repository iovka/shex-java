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
package fr.inria.lille.shexjava.schema.concrsynt;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDFTerm;

/**
 * @author Jérémie Dusart
 *
 */
public class IRIStemConstraint implements Constraint {
	private String iriStem;
	
	public IRIStemConstraint(String iriStem) {
		this.iriStem = iriStem;
	}

	@Override
	public boolean contains(RDFTerm node) {
		if (! (node instanceof IRI))
			return false;
		
		IRI inode = (IRI) node;		
		return inode.getIRIString().startsWith(iriStem);
	}

	public String toString() {
		return "IRIstem="+iriStem;
	}
	
	@Override
	public String toPrettyString() {
		return this.toString();
	}

	public String getIriStem() {
		return iriStem;
	}
	
	/** Equals if obj has the same stem.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IRIStemConstraint other = (IRIStemConstraint) obj;
		
		if (iriStem==null)
			if (other.getIriStem() != null)
				return false;				
		
		return iriStem.equals(other.getIriStem());
	}
}
