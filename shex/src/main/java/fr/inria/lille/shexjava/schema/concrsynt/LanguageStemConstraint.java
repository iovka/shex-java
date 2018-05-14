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

import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.api.RDFTerm;

/**
 * @author Jérémie Dusart
 *
 */
public class LanguageStemConstraint implements Constraint {
	private String langStem;
	
	public LanguageStemConstraint(String langTag) {
		this.langStem = langTag;
	}

	@Override
	public boolean contains(RDFTerm node) {
		if (! (node instanceof Literal))
			return false;
		
		Literal lnode = (Literal) node;
		if (!lnode.getLanguageTag().isPresent())
			return false;

		String lang = lnode.getLanguageTag().get();
		
		return lang.startsWith(langStem);
	}
	
	public String toString() {
		return "Langstem="+langStem;
	}

	public String getLangStem() {
		return langStem;
	}
	
	/** Equals if obj has the same langStem.
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
		LanguageStemConstraint other = (LanguageStemConstraint) obj;
		
		if (langStem==null)
			if (other.getLangStem() != null)
				return false;				
		
		return langStem.equals(other.getLangStem());
	}
}
