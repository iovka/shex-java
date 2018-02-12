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

package fr.univLille.cristal.shex.schema.concrsynt;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.datatypes.XMLDatatypeUtil;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class DatatypeSetOfNodes implements SetOfNodes {
	
	private IRI datatypeIri;
	
	public DatatypeSetOfNodes(IRI datatypeIri) {
		this.datatypeIri = datatypeIri;
	}

	@Override
	public boolean contains(Value node) {
		if (! (node instanceof Literal)) return false;
		Literal lnode = (Literal) node;
		if (!(datatypeIri.equals(lnode.getDatatype()))) return false;
		if ((XMLDatatypeUtil.isBuiltInDatatype(lnode.getDatatype()))) {
			return XMLDatatypeUtil.isValidValue(lnode.stringValue(), lnode.getDatatype());
		}

		return true;
	}
	
	@Override
	public String toString() {
		return datatypeIri.toString();
	}

}
