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
