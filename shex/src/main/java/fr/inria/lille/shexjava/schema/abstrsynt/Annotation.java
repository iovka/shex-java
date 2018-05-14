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
import org.apache.commons.rdf.api.RDFTerm;

/**
 * @author Jérémie Dusart
 */
public class Annotation {
	private IRI predicate;
	private RDFTerm objectRDFTerm;
	
	
	public Annotation(IRI predicate, RDFTerm objectRDFTerm) {
		super();
		this.predicate = predicate;
		this.objectRDFTerm = objectRDFTerm;
	}

	public IRI getPredicate() {
		return predicate;
	}


	public RDFTerm getObjectValue() {
		return objectRDFTerm;
	}
	
	public String toString() {
		return predicate+" "+objectRDFTerm;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Annotation other = (Annotation) obj;
		if (predicate ==null)
			if (other.getPredicate()!=null)
				return false;
		else
			if (! predicate.equals(other.getPredicate()))
				return false;
		if (objectRDFTerm ==null)
			if (other.getObjectValue()!=null)
				return false;
		else
			if (! objectRDFTerm.equals(other.getObjectValue()))
				return false;
		return true;
	}
}
