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

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.simple.SimpleRDF;
import org.apache.commons.rdf.simple.Types ;

import fr.inria.lille.shexjava.util.DatatypeUtil;



/**
 * 
 * @author Iovka Boneva
 * @author Jérémie Dusart
 */
public class DatatypeConstraint implements Constraint {
	public static final RDF rdfFactory = new SimpleRDF();
	public static final Set<IRI> validatedDatatype = new HashSet<IRI>(){
		private static final long serialVersionUID = -661008187271209543L;
		{
						add(Types.XSD_INTEGER);
						add(Types.XSD_DECIMAL);
						add(Types.XSD_FLOAT);
						add(Types.XSD_DOUBLE);
						add(Types.XSD_STRING);
						add(Types.XSD_BOOLEAN);
						add(Types.XSD_DATETIME);
						add(Types.XSD_NONPOSITIVEINTEGER);
						add(Types.XSD_NEGATIVEINTEGER);
						add(Types.XSD_LONG);
						add(Types.XSD_INT);
						add(Types.XSD_SHORT);
						add(Types.XSD_BYTE);
						add(Types.XSD_NONNEGATIVEINTEGER);
						add(Types.XSD_UNSIGNEDLONG);
						add(Types.XSD_UNSIGNEDINT);
						add(Types.XSD_UNSIGNEDSHORT);
						add(Types.XSD_UNSIGNEDBYTE);
						add(Types.XSD_POSITIVEINTEGER);
						add(Types.XSD_TIME);
						add(Types.XSD_DATE);
				}};
	
	private IRI datatypeIri;
	
	public DatatypeConstraint(IRI datatypeIri) {
		this.datatypeIri = datatypeIri;
	}

	public IRI getDatatypeIri() {
		return datatypeIri;
	}

	@Override
	public boolean contains(RDFTerm node) {
		if (! (node instanceof Literal)) return false;
		Literal lnode = (Literal) node;
		if (!(datatypeIri.equals(lnode.getDatatype()))) return false;
		if (validatedDatatype.contains(lnode.getDatatype())) {
			return DatatypeUtil.isValidValue(lnode);
		}

		return true;
	}
	
	
	@Override
	public String toString() {
		return "DT:"+datatypeIri.toString();
	}
	
	/** Equals if contains obj contains the same constraint.
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
		DatatypeConstraint other = (DatatypeConstraint) obj;
		return datatypeIri.equals(other.getDatatypeIri());
	}

}
