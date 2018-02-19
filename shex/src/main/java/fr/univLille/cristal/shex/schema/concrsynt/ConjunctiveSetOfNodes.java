package fr.univLille.cristal.shex.schema.concrsynt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.rdf4j.model.Value;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class ConjunctiveSetOfNodes implements SetOfNodes {

	private List<SetOfNodes> conjuncts;
	
	public ConjunctiveSetOfNodes(Collection<SetOfNodes> conjuncts) {
		this.conjuncts = new ArrayList<>();
		this.conjuncts.addAll(conjuncts);
	}
	
	@Override
	public boolean contains(Value node) {
		for (SetOfNodes s : conjuncts)
			if (! s.contains(node))
				return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "CONJ" + conjuncts.toString();
	}

}
