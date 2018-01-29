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

import java.util.Set;

import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.util.Pair;

/** A set of associations (resource, shape labels).
 * Is produced as result of a validation, see {@link ValidationAlgorithm}
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public interface Typing {

	/** Checks whether a node-label association belongs to the typing. 
	 * 
	 * @param node
	 * @param label
	 * @return
	 */
	public boolean contains(Value node, ShapeExprLabel label);
	
	/** Returns the typing as a set of pairs (node, label).
	 * 
	 * @return
	 */
	public Set<Pair<Value, ShapeExprLabel>> asSet(); // For testing purposes

}
