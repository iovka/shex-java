package fr.univLille.cristal.shex.schema.concrsynt;

import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;

public class LiteralStemSetOfNodes implements SetOfNodes {
	private String litStem;
	
	public LiteralStemSetOfNodes(String litStem) {
		this.litStem = litStem;
	}

	@Override
	public boolean contains(Value node) {
		if (! (node instanceof Literal))
			return false;
		Literal lnode = (Literal) node;
		return lnode.stringValue().startsWith(litStem);
	}
	
	public String toString() {
		return "Literalstem="+litStem;
	}

}
