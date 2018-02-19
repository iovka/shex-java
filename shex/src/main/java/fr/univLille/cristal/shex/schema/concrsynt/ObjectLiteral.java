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

import java.util.Optional;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class ObjectLiteral implements SetOfNodes {

	private String value;
	private String language;
	private IRI type;
	
	public ObjectLiteral (String value, String language, IRI type) {
		this.value = value;
		this.language = language;
		this.type = type;
	}
		
	@Override
	public boolean contains(Value node) {
		if (! (node instanceof Literal))
			return false;
		Literal l = (Literal) node;
		if (! l.stringValue().equals(value))
			return false;
		if (language != null) {
			Optional<String> lang = l.getLanguage();
			if (! lang.isPresent())
				return false;
			if (! lang.get().equals(language))
				return false;	
		}
		if (type != null && !l.getDatatype().equals(type))
			return false;
		
		return true;
	}
	
	public String toString(){
		return "Literal=("+value+","+language+","+type+")";
	}

}
