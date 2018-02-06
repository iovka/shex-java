package fr.univLille.cristal.shex.schema.concrsynt;

import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;

public class LiteralStemSetOfNodes implements SetOfNodes {
	private String langStem;
	
	public LiteralStemSetOfNodes(String langTag) {
		this.langStem = langTag;
	}

	@Override
	public boolean contains(Value node) {
		if (! (node instanceof Literal))
			return false;
		
		Literal lnode = (Literal) node;		
		return lnode.stringValue().startsWith(langStem);
	}

}
