package fr.univLille.cristal.shex.schema.concrsynt;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Value;

public class IRIStemSetOfNodes implements SetOfNodes {
	private String iriStem;
	
	public IRIStemSetOfNodes(String iriStem) {
		this.iriStem = iriStem;
	}

	@Override
	public boolean contains(Value node) {
		if (! (node instanceof IRI))
			return false;
		
		IRI inode = (IRI) node;		
		return inode.stringValue().startsWith(iriStem);
	}

}
