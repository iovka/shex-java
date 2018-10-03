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
package fr.inria.lille.shexjava.schema;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.api.IRI;

import fr.inria.lille.shexjava.util.RDFPrintUtils;

/** Label class for shex schema. A label is either an IRI or a BlankNode. This class is used to label triple expression and shape expression. 
 * 
 * @author Iovka Boneva
 * @author Jérémie Dusart
 */
public class Label {
	// Exactly one of these is non null
	private final IRI iri;
	private final BlankNode bnode;
	private final boolean isGenerated;

	
	/** isGenerated is set to false.
	 * @param iri
	 */
	public Label (IRI iri) {
		this.iri = iri;
		this.bnode = null;
		this.isGenerated = false;
	}
	
	/** isGenerated is set to false.
	 * @param bnode
	 */
	public Label (BlankNode bnode) {
		this.bnode = bnode;
		this.iri = null;
		this.isGenerated = false;
	}
	
	public Label (IRI iri,boolean generated) {
		this.iri = iri;
		this.bnode = null;
		this.isGenerated = generated;
	}
	
	public Label (BlankNode bnode,boolean generated) {
		this.bnode = bnode;
		this.iri = null;
		this.isGenerated = generated;
	}
	
	public boolean isIri() {
		return this.iri!=null;
	}
	
	public boolean isBlankNode() {
		return this.bnode!=null;
	}
	
	public boolean isGenerated() {
		return this.isGenerated;
	}
	
	public String stringValue() {
		if (bnode!=null)
			return bnode.ntriplesString();
		return iri.getIRIString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (bnode != null) result = prime * result + bnode.hashCode();
		if (iri != null) result = prime * result + iri.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Label other = (Label) obj;
		if (this.isGenerated !=other.isGenerated())
			return false;		
		if (bnode == null) {
			if (other.bnode != null)
				return false;
		} else if (!bnode.equals(other.bnode))
			return false;
		if (iri == null) {
			if (other.iri != null)
				return false;
		} else if (!iri.equals(other.iri))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return toPrettyString(Collections.emptyMap());
	}
	
	public String toPrettyString() {
		return toPrettyString(Collections.emptyMap());
	}
	
	public String toPrettyString(Map<String,String> prefixes) {
		if (iri != null) 
			return RDFPrintUtils.toPrettyString(iri, prefixes);
		else 
			return RDFPrintUtils.toPrettyString(bnode, prefixes);
	}
}
