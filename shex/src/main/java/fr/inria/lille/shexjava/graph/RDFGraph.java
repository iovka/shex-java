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
package fr.inria.lille.shexjava.graph;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

 
/** Defines the operations on an RDF graph that are needed for validation.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public interface RDFGraph {


	/** List all the RDFTerms that contain the given node as subject or object.
	 * This is the union of {@link #itInNeighbours(RDFTerm)} and {@link #itOutNeighbours(RDFTerm)} 
	 * 
	 * @param focusNode
	 * @return an iterator over the neighbors of focusNode
	 */
	public Iterator<Triple> itAllNeighbours (RDFTerm focusNode);
	
	/** List all the RDFTerms that have the given node as focus node or object node and a predicate belonging to the set of allowed predicates.
	 * 
	 * @param focusNode
	 * @return an iterator over the neighbors of focusNode connected with one of the allowedPredicates
	 */
	public Iterator<Triple> itAllNeighboursWithPredicate (RDFTerm focusNode,Set<IRI> allowedPredicates);
	
	/** List all the RDFTerms that have the given node as object node.
	 * 
	 * @param focusNode
	 * @return an iterator over the incoming neighbors of focusNode
	 */
	public Iterator<Triple> itInNeighbours (RDFTerm focusNode);
	
	/** List all the RDFTerms that have the given node as object node and a predicate belonging to the set of allowed predicates.
	 * 
	 * @param focusNode
	 * @return an iterator over the incoming neighbors of focusNode connected with one of the allowedPredicates
	 */
	public Iterator<Triple> itInNeighboursWithPredicate (RDFTerm focusNode,Set<IRI> allowedPredicates);
	
	/** List all the RDFTerms that have the given node as focus node.
	 * 
	 * @param focusNode
	 * @return an iterator over the outgoing neighbors of focusNode
	 */
	public Iterator<Triple> itOutNeighbours (RDFTerm focusNode);
	
	/** List all the RDFTerms that have the given node as focus node and a predicate belonging to the set of allowed predicates.
	 * 
	 * @param focusNode
	 * @return an iterator over the outgoing neighbors of focusNode connected with one of the allowedPredicates
	 */
	public Iterator<Triple> itOutNeighboursWithPredicate (RDFTerm focusNode,Set<IRI> allowedPredicates);


	/** List all the object nodes in the graph.
	 * 
	 * @return an iterator over the object nodes of the graph
	 */
	public Iterator<RDFTerm> listAllObjectNodes ();
	
	/** List all the subjects node in the graph.
	 * 
	 * @return an iterator over the subject nodes of the graph
	 */
	public Iterator<RDFTerm> listAllSubjectNodes ();
	
	/** List all the object and subject in the graph.
	 * 
	 * @return an iterator over the nodes of the graph
	 */
	public Iterator<RDFTerm> listAllNodes ();
}
