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

	public NeighborTriple(Value focus, TCProperty prop, Value opposite) {
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
