/*******************************************************************************
 * Copyright (C) 2018  Université de Lille - Inria
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
package fr.univLille.cristal.shex.schema.abstrsynt;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Value;

public class Annotation {
	private IRI predicate;
	private Value objectValue;
	
	
	public Annotation(IRI predicate, Value objectValue) {
		super();
		this.predicate = predicate;
		this.objectValue = objectValue;
	}

	public IRI getPredicate() {
		return predicate;
	}


	public Value getObjectValue() {
		return objectValue;
	}
	
	public String toString() {
		return predicate+" "+objectValue;
	}

}
