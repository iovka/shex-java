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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.jena.rdf.model.RDFNode;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.vocabulary.RDF;

/** Helper implementation of {@link RDF}.
 * Implementing classes need to implement only the methods {@link RDFGraph#listAllSubjectNodes()}, {@link RDFGraph#listAllObjectNodes()}, {@link #itInNeighbours(RDFNode)} and {@link #itOutNeighbours(Resource)}.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 * @author Jérémie Dusart
 */
abstract class AbstractRDFGraph implements RDFGraph {
	@Override
	public Iterator<NeighborTriple> itAllNeighbours (Value focusNode) {
		List<Iterator<NeighborTriple>> result = new ArrayList<Iterator<NeighborTriple>>();
		result.add(itInNeighbours(focusNode));
		result.add(itOutNeighbours(focusNode));
		return new ConcatIterator<NeighborTriple>(result);
	}
	
	@Override
	public Iterator<NeighborTriple> itAllNeighboursWithPredicate (Value focusNode,Set<IRI> allowedPredicates){
		List<Iterator<NeighborTriple>> result = new ArrayList<Iterator<NeighborTriple>>();
		for (IRI predicate:allowedPredicates) {
			result.add(itOutNeighbours(focusNode,predicate));
			result.add(itInNeighbours(focusNode,predicate));
		}
		return new ConcatIterator<NeighborTriple>(result);
	}

	@Override
	public Iterator<NeighborTriple> itOutNeighbours(Value focusNode) {
		if (! (focusNode instanceof Resource))
			return new EmptyIterator<NeighborTriple>();
		return itOutNeighbours((Resource) focusNode,null);
	}
	
	@Override
	public Iterator<NeighborTriple> itOutNeighboursWithPredicate (Value focusNode,Set<IRI> allowedPredicates){
		List<Iterator<NeighborTriple>> result = new ArrayList<Iterator<NeighborTriple>>();
		for (IRI predicate:allowedPredicates) {
			result.add(itOutNeighbours(focusNode,predicate));
		}
		return new ConcatIterator<NeighborTriple>(result);
	}
	
	@Override
	public Iterator<NeighborTriple> itInNeighbours(Value focusNode) {
		return itInNeighbours(focusNode,null);
	}
	
	@Override
	public Iterator<NeighborTriple> itInNeighboursWithPredicate (Value focusNode,Set<IRI> allowedPredicates){
		List<Iterator<NeighborTriple>> result = new ArrayList<Iterator<NeighborTriple>>();
		for (IRI predicate:allowedPredicates) {
			result.add(itInNeighbours(focusNode,predicate));
		}
		return new ConcatIterator<NeighborTriple>(result);
	}
	
	@Override
	public Iterator<Value> listAllNodes() {
		List<Iterator<Value>> result = new ArrayList<Iterator<Value>>();
		result.add(listAllObjectNodes());
		result.add(listAllSubjectNodes());
		return new ConcatIterator<Value>(result);
	}
	
	
	/** List all the triples that have the given node as object node and the specified predicate.
	 * @param focusNode
	 * @param predicate
	 * @return an iterator over the neighbors of focusNode connected with the predicate
	 */
	protected abstract Iterator<NeighborTriple> itOutNeighbours (Value focusNode, IRI predicate);
	
	/** List all the triples that have the given node as subject node and the specified predicate.
	 * @param focusNode
	 * @param predicate
	 * @return an iterator over the neighbors of focusNode connected with the predicate
	 */
	protected abstract Iterator<NeighborTriple> itInNeighbours (Value focusNode, IRI predicate);
	
	//---------------------------------------------------------------------------
	// Iterators
	//---------------------------------------------------------------------------
	
	protected class ConcatIterator<T> implements Iterator<T> {
		private List<Iterator<T>> iterators;
				
		public ConcatIterator(List<Iterator<T>> iterators) {
			this.iterators = iterators;
		}

		@Override
		public boolean hasNext() {
			if (iterators.size()==0)
				return false;
			if (!iterators.get(0).hasNext()) {
				iterators.remove(0);
				return this.hasNext();
			} else {
				return true;
			}
		}

		@Override
		public T next() {
			if (iterators.size()==0)
				throw new NoSuchElementException();
			if (!iterators.get(0).hasNext()) {
				iterators.remove(0);
				return this.next();
			} else {
				return iterators.get(0).next();
			}
		}
	}	
	
	
	protected class EmptyIterator<T> implements Iterator<T> {
		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public T next() {
			throw new NoSuchElementException();
		}
	}
}
