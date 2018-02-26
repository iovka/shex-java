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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.datatypes.XMLDatatypeUtil;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;


/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class DatatypeConstraint implements Constraint {
	public static final Set<IRI> validatedDatatype = new HashSet<>(Arrays.asList(new IRI[] {
					XMLSchema.INTEGER, 
					XMLSchema.DECIMAL,
					XMLSchema.FLOAT,
					XMLSchema.DOUBLE,
					XMLSchema.STRING,
					XMLSchema.BOOLEAN,
					XMLSchema.DATETIME,
					XMLSchema.NON_POSITIVE_INTEGER,
					XMLSchema.NEGATIVE_INTEGER,
					XMLSchema.LONG,
					XMLSchema.INT,
					XMLSchema.SHORT,
					XMLSchema.BYTE,
					XMLSchema.NON_NEGATIVE_INTEGER,
					XMLSchema.UNSIGNED_LONG,
					XMLSchema.UNSIGNED_INT,
					XMLSchema.UNSIGNED_SHORT,
					XMLSchema.UNSIGNED_BYTE,
					XMLSchema.POSITIVE_INTEGER,
					//XMLSchema.TIME) 
					//XMLSchema.DATE);			
			}));
	private IRI datatypeIri;
	
	public DatatypeConstraint(IRI datatypeIri) {
		this.datatypeIri = datatypeIri;
	}

	public IRI getDatatypeIri() {
		return datatypeIri;
	}

	@Override
	public boolean contains(Value node) {
		if (! (node instanceof Literal)) return false;
		Literal lnode = (Literal) node;
		if (!(datatypeIri.equals(lnode.getDatatype()))) return false;
		if (validatedDatatype.contains(lnode.getDatatype())) {
			return XMLDatatypeUtil.isValidValue(lnode.stringValue(), lnode.getDatatype());
		}

		return true;
	}
	
	
	@Override
	public String toString() {
		return "DT:"+datatypeIri.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatatypeConstraint other = (DatatypeConstraint) obj;
		return datatypeIri.equals(other.getDatatypeIri());
	}

}
