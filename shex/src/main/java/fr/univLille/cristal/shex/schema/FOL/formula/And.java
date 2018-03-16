package fr.univLille.cristal.shex.schema.FOL.formula;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.util.CollectionToString;
import fr.univLille.cristal.shex.util.Pair;

public class And implements Sentence{
	protected ArrayList<Sentence> subSentences;
	
	public And(ArrayList<Sentence> subSentences) {
		this.subSentences = subSentences;
	}

	@Override
	public boolean evaluate(Map<Variable,Value> affectations,
							Set<Pair<Value, Label>> shapes,
							Set<Pair<Pair<Value,Value>, Label>> triples) {
		for (Sentence sub:subSentences)
			if (!sub.evaluate(affectations,shapes,triples))
				return false;
		return true;
	}
	
	@Override
	public String toString() {
		return CollectionToString.collectionToString(subSentences, ", ", "AND{ ", " }");
	}

}
