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
 * Implementing classes need to implement only the methods {@link RDFGraph#listAllNodes()}, {@link #itInNeighbours(RDFNode)} and {@link #itOutNeighbours(Resource)}.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 * 
 */
abstract class AbstractRDFGraph implements RDFGraph {
	public Iterator<NeighborTriple> listAllNeighbours (Value focusNode) {
		List<Iterator<NeighborTriple>> result = new ArrayList<Iterator<NeighborTriple>>();
		result.add(listInNeighbours(focusNode));
		result.add(listOutNeighbours(focusNode));
		return new ConcatIterator<NeighborTriple>(result);
	}
	
	public Iterator<NeighborTriple> listAllNeighboursWithPredicate (Value focusNode,Set<IRI> allowedPredicates){
		List<Iterator<NeighborTriple>> result = new ArrayList<Iterator<NeighborTriple>>();
		for (IRI predicate:allowedPredicates) {
			result.add(itOutNeighbours(focusNode,predicate));
			result.add(itInNeighbours(focusNode,predicate));
		}
		return new ConcatIterator<NeighborTriple>(result);
	}

	
	public Iterator<NeighborTriple> listOutNeighbours(Value focusNode) {
		if (! (focusNode instanceof Resource))
			return new EmptyIterator<NeighborTriple>();
		return itOutNeighbours((Resource) focusNode,null);
	}
	

	public Iterator<NeighborTriple> listOutNeighboursWithPredicate (Value focusNode,Set<IRI> allowedPredicates){
		List<Iterator<NeighborTriple>> result = new ArrayList<Iterator<NeighborTriple>>();
		for (IRI predicate:allowedPredicates) {
			result.add(itOutNeighbours(focusNode,predicate));
		}
		return new ConcatIterator<NeighborTriple>(result);
	}
	

	public Iterator<NeighborTriple> listInNeighbours(Value focusNode) {
		return itInNeighbours(focusNode,null);
	}
	
	
	public Iterator<NeighborTriple> listInNeighboursWithPredicate (Value focusNode,Set<IRI> allowedPredicates){
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
	
	
	//---------------------------------------------------------------------------
	// Iterators
	//---------------------------------------------------------------------------
	
	protected abstract Iterator<NeighborTriple> itOutNeighbours (Value focusNode, IRI predicate);
	protected abstract Iterator<NeighborTriple> itInNeighbours (Value focusNode, IRI predicate);
	
	protected class ConcatIterator<T> implements Iterator<T> {
		private List<Iterator<T>> iterators;
				
		public ConcatIterator(List<Iterator<T>> iterators) {
			this.iterators = iterators;
		}

		@Override
		public boolean hasNext() {
			for (Iterator<T> it:iterators)
				if (it.hasNext())
					return true;							
			return false;
		}

		@Override
		public T next() {
			for (Iterator<T> it:iterators)
				if (it.hasNext())
					return it.next();
			throw new NoSuchElementException();
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
