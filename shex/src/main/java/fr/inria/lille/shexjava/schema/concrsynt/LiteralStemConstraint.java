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

import java.util.Collections;
import java.util.Map;

import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.api.RDFTerm;

/**
 * @author Jérémie Dusart
 *
 */
public class LiteralStemConstraint implements ValueConstraint {
	private String litStem;
	
	public LiteralStemConstraint(String litStem) {
		this.litStem = litStem;
	}

	@Override
	public boolean contains(RDFTerm node) {
		if (! (node instanceof Literal))
			return false;
		Literal lnode = (Literal) node;
		return lnode.getLexicalForm().startsWith(litStem);
	}
	
	@Override
	public String toString() {
		return toPrettyString(Collections.emptyMap());
	}
	
	@Override
	public String toPrettyString() {
		return toPrettyString(Collections.emptyMap());
	}
	
	@Override
	public String toPrettyString(Map<String,String> prefixes) {
		return "\""+litStem+"\"~";
	}

	public String getLitStem() {
		return litStem;
	}

	/** Equals if obj has the same litStem.
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
		LiteralStemConstraint other = (LiteralStemConstraint) obj;
		
		if (litStem==null)
			if (other.getLitStem() != null)
				return false;				
		
		return litStem.equals(other.getLitStem());
	}
}
