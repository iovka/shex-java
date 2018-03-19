package fr.univLille.cristal.shex.schema.FOL.formula;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.util.CollectionToString;
import fr.univLille.cristal.shex.util.Pair;

public class Or implements Sentence{
	protected ArrayList<Sentence> subSentences;
	
	public Or(ArrayList<Sentence> subSentences) {
		this.subSentences = subSentences;
	}

	@Override
	public int evaluate(Map<Variable,Value> affectations,
							Set<Pair<Value, Label>> shapes,
							Set<Pair<Pair<Value,Value>, Label>> triples) {
		boolean partial=false;
		for (Sentence sub:subSentences) {
			int subScore = sub.evaluate(affectations,shapes,triples);
			if (subScore==3) return 3;
			if (subScore==2) partial=true;
			if (subScore==1) return 1;
		}
		if (partial)
			return 2;
		return 0;
	}
	
	@Override
	public String toString() {
		return CollectionToString.collectionToString(subSentences, ", ", "OR{ ", " }");
	}
}
