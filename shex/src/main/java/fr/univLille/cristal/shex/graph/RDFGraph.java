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

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;

 
/** Defines the operations on an RDF graph that are needed for validation.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public interface RDFGraph {


	/** A list of all the triples that contain the given node as subject or object.
	 * This is the union of {@link #listInNeighbours(RDFNode)} and {@link #listOutNeighbours(Resource)} 
	 * 
	 * @param focusNode
	 * @return
	 */
	public Iterator<NeighborTriple> listAllNeighbours (Value focusNode);
	
	/** A list of all the forward triples that have the given node as focus node.
	 * 
	 * @param focusNode
	 * @return
	 */
	public Iterator<NeighborTriple> listAllNeighboursWithPredicate (Value focusNode,Set<IRI> allowedPredicates);
	
	/** A list of all the forward triples that have the given node as focus node.
	 * 
	 * @param focusNode
	 * @return
	 */
	public Iterator<NeighborTriple> listInNeighbours (Value focusNode);
	
	/** A list of all the forward triples that have the given node as focus node.
	 * 
	 * @param focusNode
	 * @return
	 */
	public Iterator<NeighborTriple> listInNeighboursWithPredicate (Value focusNode,Set<IRI> allowedPredicates);
	
	/** A list of all the backward triples that have the given node as focus node.
	 * 
	 * @param focusNode
	 * @return
	 */
	public Iterator<NeighborTriple> listOutNeighbours (Value focusNode);
	
	/** A list of all the forward triples that have the given node as focus node.
	 * 
	 * @param focusNode
	 * @return
	 */
	public Iterator<NeighborTriple> listOutNeighboursWithPredicate (Value focusNode,Set<IRI> allowedPredicates);


	/** The set of all object nodes in the graph.
	 * 
	 * @return
	 */
	public Iterator<Value> listAllObjectNodes ();
	
	/** The set of all subjects node in the graph.
	 * 
	 * @return
	 */
	public Iterator<Value> listAllSubjectNodes ();
	
	/** The set of all object and subject in the graph.
	 * 
	 * @return
	 */
	public Iterator<Value> listAllNodes ();
}
