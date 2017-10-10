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

package fr.univLille.cristal.shex.graph;

/** Encapsulates a value.
 * Used for wrapping RDF graphs.
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 * @param <InternalType>
 */
abstract class Encapsulator<InternalType> {

	protected final InternalType internalValue;
	
	protected Encapsulator (InternalType internalValue) {
		if (internalValue == null)
			throw new IllegalArgumentException("The encapsulated value cannot be null.");
		this.internalValue = internalValue;
	}

	@Override
	public String toString() {
		return internalValue.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + internalValue.hashCode();
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
		Encapsulator other = (Encapsulator) obj;
		return internalValue.equals(other.internalValue);
	}
	
	
	
}
