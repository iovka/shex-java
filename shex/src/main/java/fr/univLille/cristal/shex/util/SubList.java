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
package fr.univLille.cristal.shex.util;

import java.util.AbstractList;
import java.util.List;
import java.util.Spliterator;

import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;

/** Implements a list as a sub-list of a bigger list.
 * 
 * The sub-list is defined by its begin and end index.
 * The list is unmodifiable through this class. If the underlying bigger list is modified, then the behaviour of this class is undefined.
 * In particular, no check is made on the validity of begin and end indexes.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public class SubList extends AbstractList<TripleConstraint>{
	
	private int idxBegin, idxEnd;
	private List<TripleConstraint> wholeList;
	
	public SubList (List<TripleConstraint> wholeList, int idxBegin, int idxEnd) {
		super();
		this.wholeList = wholeList;
		this.idxBegin = idxBegin;
		this.idxEnd = idxEnd;
	}
	
	@Override
	public TripleConstraint get(int index) {
		return wholeList.get(index+idxBegin);
	}

	@Override
	public int size() {
		return idxEnd - idxBegin;
	}
	
	public List<TripleConstraint> getList(){
		return wholeList.subList(idxBegin, idxEnd);
	}
	
	
	@Override
	public Spliterator<TripleConstraint> spliterator(){
		throw new UnsupportedOperationException("");
	} 
}
