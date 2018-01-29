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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;

/** Helper implementation of {@link RDF}.
 * Implementing classes need to implement only the methods {@link RDFGraph#getAllNodes()}, {@link #itInNeighbours(RDFNode)} and {@link #itOutNeighbours(Resource)}.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 * 
 */
abstract class AbstractRDFGraph implements RDFGraph {
 
	protected abstract Iterator<NeighborTriple> itOutNeighbours (Value focusNode);
	protected abstract Iterator<NeighborTriple> itInNeighbours (Value focusNode);
	
	private Iterator<NeighborTriple> itAllNeighbours (final Value focusNode) {
		return new Iterator<NeighborTriple>() {
			Iterator<NeighborTriple> itFw, itBw;
			{
				if (focusNode instanceof Resource)
					itFw = itOutNeighbours((Resource) focusNode);
				else 
					itFw = new EmptyIterator<>();
				
				itBw = itInNeighbours(focusNode);
			}
			
			@Override
			public boolean hasNext() {
				return itFw.hasNext() || itBw.hasNext();
			}

			@Override
			public NeighborTriple next() {
				if (itFw.hasNext()) 
					return itFw.next();
				else 
					return itBw.next();
			}
			
		};
	}
	
	private List<NeighborTriple> listNeighbours (Iterator<NeighborTriple> it) {
		ArrayList<NeighborTriple> result = new ArrayList<>();
		while (it.hasNext()) {
			result.add(it.next());
		}
		return result;
	}

	@Override
	public List<NeighborTriple> listAllNeighbours (Value focusNode) {
		return listNeighbours(itAllNeighbours(focusNode));
	}

	public List<NeighborTriple> listOutNeighbours(Value focusNode) {
		if (! (focusNode instanceof Resource))
			return Collections.emptyList();
		return listNeighbours(itOutNeighbours((Resource) focusNode));
	}
	
	@Override
	public List<NeighborTriple> listInNeighbours(Value focusNode) {
		return listNeighbours(itInNeighbours(focusNode));
	}
	
	private class EmptyIterator<T> implements Iterator<T> {

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
