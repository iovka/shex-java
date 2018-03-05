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
import java.util.Set;

import org.apache.jena.rdf.model.RDFNode;
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


	/** List all the triples that contain the given node as subject or object.
	 * This is the union of {@link #itInNeighbours(RDFNode)} and {@link #itOutNeighbours(Resource)} 
	 * 
	 * @param focusNode
	 * @return
	 */
	public Iterator<NeighborTriple> itAllNeighbours (Value focusNode);
	
	/** List all the triples that have the given node as focus node or object node and a predicate belonging to the set of allowed predicates.
	 * 
	 * @param focusNode
	 * @return
	 */
	public Iterator<NeighborTriple> itAllNeighboursWithPredicate (Value focusNode,Set<IRI> allowedPredicates);
	
	/** List all the triples that have the given node as object node.
	 * 
	 * @param focusNode
	 * @return
	 */
	public Iterator<NeighborTriple> itInNeighbours (Value focusNode);
	
	/** List all the triples that have the given node as object node and a predicate belonging to the set of allowed predicates.
	 * 
	 * @param focusNode
	 * @return
	 */
	public Iterator<NeighborTriple> itInNeighboursWithPredicate (Value focusNode,Set<IRI> allowedPredicates);
	
	/** List all the triples that have the given node as focus node.
	 * 
	 * @param focusNode
	 * @return
	 */
	public Iterator<NeighborTriple> itOutNeighbours (Value focusNode);
	
	/** List all the triples that have the given node as focus node and a predicate belonging to the set of allowed predicates.
	 * 
	 * @param focusNode
	 * @return
	 */
	public Iterator<NeighborTriple> itOutNeighboursWithPredicate (Value focusNode,Set<IRI> allowedPredicates);


	/** List all the object nodes in the graph.
	 * 
	 * @return
	 */
	public Iterator<Value> listAllObjectNodes ();
	
	/** List all the subjects node in the graph.
	 * 
	 * @return
	 */
	public Iterator<Value> listAllSubjectNodes ();
	
	/** List all the object and subject in the graph.
	 * 
	 * @return
	 */
	public Iterator<Value> listAllNodes ();
}
