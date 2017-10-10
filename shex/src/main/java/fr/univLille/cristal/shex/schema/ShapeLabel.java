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

/** A label (name) for a shape in a {@link ShexSchema}.
 * Shape labels are used in {@link Typing} to indicate which are the shapes satisfied by a node.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public class ShapeLabel  {
	
	private final String id;
	
	/** Creates a shape label that encapsulates the given resource.
	 * 
	 * @param res
	 */
	public ShapeLabel (String id) {
		if (id == null)
			throw new IllegalArgumentException("Shape label cannot be defined by a null resource.");
		this.id = id;
	}
	
	@Override
	public String toString() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * id.hashCode();
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
		ShapeLabel other = (ShapeLabel) obj;
		return this.id.equals(other.id);
	}
}
