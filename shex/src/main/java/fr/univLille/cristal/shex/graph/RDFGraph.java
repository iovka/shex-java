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
	public List<NeighborTriple> listAllNeighbours (Value focusNode);
	
	/** A list of all the forward triples that have the given node as focus node.
	 * 
	 * @param focusNode
	 * @return
	 */
	public List<NeighborTriple> listAllNeighboursWithPredicate (Value focusNode,Set<IRI> allowedPredicates);
	
	/** A list of all the forward triples that have the given node as focus node.
	 * 
	 * @param focusNode
	 * @return
	 */
	public List<NeighborTriple> listInNeighbours (Value focusNode);
	
	/** A list of all the forward triples that have the given node as focus node.
	 * 
	 * @param focusNode
	 * @return
	 */
	public List<NeighborTriple> listInNeighboursWithPredicate (Value focusNode,Set<IRI> allowedPredicates);
	
	/** A list of all the backward triples that have the given node as focus node.
	 * 
	 * @param focusNode
	 * @return
	 */
	public List<NeighborTriple> listOutNeighbours (Value focusNode);
	
	/** A list of all the forward triples that have the given node as focus node.
	 * 
	 * @param focusNode
	 * @return
	 */
	public List<NeighborTriple> listOutNeighboursWithPredicate (Value focusNode,Set<IRI> allowedPredicates);


	/** The set of all object nodes in the graph.
	 * 
	 * @return
	 */
	public Set<Value> getAllObjectNodes ();
	
	/** The set of all subjects node in the graph.
	 * 
	 * @return
	 */
	public Set<Value> getAllSubjectNodes ();
	
	/** The set of all object and subject in the graph.
	 * 
	 * @return
	 */
	public Set<Value> getAllNodes ();
}
