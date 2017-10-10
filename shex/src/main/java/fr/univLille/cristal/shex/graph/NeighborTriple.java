/**
Copyright 2017 University of Lille.

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

import org.eclipse.rdf4j.model.Value;

/** A triple with orientation that can be forward or backward.
 * Used to represent the neighborhood of a node.
 * 
 * A forward triple is a usual RDF triple.
 * A backward triple is an RDF triple considered in backwards direction. 
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public class NeighborTriple {

	private final Value focus;
	private final Value opposite;
	private final TCProperty prop;

	protected NeighborTriple(Value focus, TCProperty prop, Value opposite) {
		this.focus = focus;
		this.opposite = opposite;
		this.prop = prop;
	}

	
	/** The focus node is the subject of a forward triple, or the object of a backward triple.
	 * 
	 * @return
	 */
	public final Value getFocus() {
		return focus;
	}
	
	/** The extremity of a triple that is opposite to the focus node.
	 * This is the subject of a forward triple, and is the object of a backward triple.
	 * 
	 * @return
	 */
	public final Value getOpposite() {
		return opposite;
	}
	
	/** The predicate of a neighbor triple.
	 * 
	 * @return
	 */
	public final TCProperty getPredicate() {
		return this.prop;
	}
	
	@Override
	public String toString() {
		return "NeighbourTriple:(" +
				getFocus() + " " +
				getPredicate() + " " +
				getOpposite() + ")";
	
	}
	
}
