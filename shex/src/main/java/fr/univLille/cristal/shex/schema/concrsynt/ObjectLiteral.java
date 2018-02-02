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

}
