package fr.univLille.cristal.shex.schema.concrsynt;

import java.util.Set;

import org.eclipse.rdf4j.model.Value;

public class StemRangeSetOfNodes implements SetOfNodes {
	private SetOfNodes stem;
	private Set<SetOfNodes> exclusions;
	
	public StemRangeSetOfNodes(SetOfNodes stem,Set<SetOfNodes> exclusions) {
		this.stem = stem;
		this.exclusions = exclusions;
	}

	@Override
	public boolean contains(Value node) {
		if (stem!=null)
			if (!stem.contains(node))
			 return false;

		for (SetOfNodes sn:exclusions) {
			if (sn.contains(node))
				return false;
		}
		return true;
	}
	
	public String toString() {
		return "StemRange=("+stem+" exclusions="+exclusions+")";
	}

}
