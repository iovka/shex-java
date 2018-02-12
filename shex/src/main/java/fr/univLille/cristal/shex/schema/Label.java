/**
Copyright 2017 University of Lille

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/

package fr.univLille.cristal.shex.schema;

import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.IRI;

/**
 * 
 * @author Iovka Boneva
 * 11 oct. 2017
 */
public abstract class Label {
	// Exactly one of these is non null
	private final IRI iri;
	private final BNode bnode;
	private final boolean generated;

	public Label (IRI iri) {
		this.iri = iri;
		this.bnode = null;
		this.generated = false;
	}
	
	public Label (BNode bnode) {
		this.bnode = bnode;
		this.iri = null;
		this.generated = false;
	}
	
	public Label (IRI iri,boolean generated) {
		this.iri = iri;
		this.bnode = null;
		this.generated = generated;
	}
	
	public Label (BNode bnode,boolean generated) {
		this.bnode = bnode;
		this.iri = null;
		this.generated = generated;
	}
	
	public boolean isIri() {
		return this.iri!=null;
	}
	
	public boolean isBNode() {
		return this.bnode!=null;
	}
	
	public boolean isGenerated() {
		return this.generated;
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
		if (iri != null) return iri.toString();
		else return bnode.toString();
	}
}
