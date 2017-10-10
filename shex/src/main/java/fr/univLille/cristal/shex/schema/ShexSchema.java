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

import java.util.Collections;
import java.util.Set;

import fr.univLille.cristal.shex.schema.abstrsynt.SchemaRules;

/** A ShEx schema.
 * 
 * An instance of this class is represents a well-defined schema, that is, all shape labels are defined, and the set of rules is stratified.
 * The stratification is a most refined stratification.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public class ShexSchema {

	private SchemaRules rules;
	
	public ShexSchema (SchemaRules rules) {
		this.rules = rules;
	}
	
	@Override
	public String toString() {
		return rules.toString();
	}
	
	// -------------------------------------------------------------------------------
	// STRATIFICATION
	// -------------------------------------------------------------------------------

	/** The set of shape labels on a given stratum.
	 * 
	 * @param i
	 * @return
	 */
	public Set<ShapeLabel> getStratum (int i) {
		if (i < 0 && i >= rules.getStratification().size())
			throw new IllegalArgumentException("Stratum " + i + " does not exist");
		return Collections.unmodifiableSet(rules.getStratification().get(i));
	}

	/** The number of stratums of the schema.
	 * 
	 * @return
	 */
	public int getNbStratums () {
		return rules.getStratification().size();
	}

	/** The stratum of a given shape label.
	 * 
	 * @param label
	 * @return
	 */
	public int hasStratum (ShapeLabel label) {
		for (int i = 0; i < getNbStratums(); i++)
			if (getStratum(i).contains(label))
				return i;
		throw new IllegalArgumentException("Unknown shape label: " + label);
	}

	/** The rules of the schema.
	 * 
	 * @return
	 */
	public SchemaRules getRules() {
		return this.rules;
	}
}

