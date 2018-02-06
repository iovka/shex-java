package fr.univLille.cristal.shex.schema.concrsynt;

import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;

public class LanguageSetOfNodes implements SetOfNodes {
	private String langTag;
	
	public LanguageSetOfNodes(String langTag) {
		this.langTag = langTag;
	}

	@Override
	public boolean contains(Value node) {
		if (! (node instanceof Literal))
			return false;
		
		Literal lnode = (Literal) node;
		if (!lnode.getLanguage().isPresent())
			return false;

		String lang = lnode.getLanguage().get();
		
		return lang.toLowerCase().equals(langTag);
	}

}
