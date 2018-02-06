package fr.univLille.cristal.shex.schema.concrsynt;

import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;

public class LanguageStemSetOfNodes implements SetOfNodes {
	private String langStem;
	
	public LanguageStemSetOfNodes(String langTag) {
		this.langStem = langTag;
	}

	@Override
	public boolean contains(Value node) {
		if (! (node instanceof Literal))
			return false;
		
		Literal lnode = (Literal) node;
		if (!lnode.getLanguage().isPresent())
			return false;

		String lang = lnode.getLanguage().get();
		return lang.startsWith(langStem);
	}

}
