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
package fr.univLille.cristal.shex.schema.concrsynt;

import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;

public class LanguageStemConstraint implements Constraint {
	private String langStem;
	
	public LanguageStemConstraint(String langTag) {
		this.langStem = langTag;
	}

	@Override
	public boolean contains(Value node) {
		if (! (node instanceof Literal))
			return false;
		
		Literal lnode = (Literal) node;
		if (!lnode.getLanguage().isPresent())
			return false;

		String lang = lnode.getLanguage().get();
		
		return lang.startsWith(langStem);
	}
	
	public String toString() {
		return "Langstem="+langStem;
	}
}
