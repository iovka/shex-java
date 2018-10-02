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

import java.util.Map;

import org.apache.commons.rdf.api.RDFTerm;

/**
 * @author Jérémie Dusart
 *
 */
public class WildcardConstraint implements Constraint {
	
	
	/** Return always true.
	 * @see fr.inria.lille.shexjava.schema.concrsynt.Constraint#contains(org.apache.commons.rdf.api.RDFTerm)
	 */
	@Override
	public boolean contains(RDFTerm node) {
		return true;
	}
	
	@Override
	public String toString() {
		return "WILDCARD";
	}
	
	@Override
	public String toPrettyString() {
		return this.toString();
	}
	
	@Override
	public String toPrettyString(Map<String,String> prefixes) {
		return this.toString();
	}
	
	/** Equals if obj is an instance of WildcardConstraint.
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
		return true;
	}
}
