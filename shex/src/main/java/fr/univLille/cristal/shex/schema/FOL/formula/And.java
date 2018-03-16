package fr.univLille.cristal.shex.schema.FOL.formula;

import java.util.ArrayList;

import fr.univLille.cristal.shex.util.CollectionToString;

public class And implements Sentence{
	protected ArrayList<Sentence> subSentences;
	
	public And(ArrayList<Sentence> subSentences) {
		this.subSentences = subSentences;
	}

	@Override
	public boolean evaluate() {
		for (Sentence sub:subSentences)
			if (!sub.evaluate())
				return false;
		return true;
	}
	
	@Override
	public String toString() {
		return CollectionToString.collectionToString(subSentences, ", ", "AND{ ", " }");
	}

}
