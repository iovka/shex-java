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

package fr.univLille.cristal.shex.validation;

import org.eclipse.rdf4j.model.Resource;

import fr.univLille.cristal.shex.graph.RDFGraph;
import fr.univLille.cristal.shex.schema.ShapeLabel;
import fr.univLille.cristal.shex.schema.ShexSchema;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class RecursiveValidation implements ValidationAlgorithm {

	public RecursiveValidation(ShexSchema schem, RDFGraph graph) {
		throw new UnsupportedOperationException("not yet implemented");
	}
	
	@Override
	public void validate(Resource focusNode, ShapeLabel label) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	@Override
	public Typing getTyping() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not yet implemented.");
	}

}
