package fr.univLille.cristal.shex.schema.FOL.formula;

import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.util.Pair;

public class Not implements Sentence{
	protected Sentence subSentence;
	
	public Not(Sentence subSentence) {
		this.subSentence = subSentence;
	}

	@Override
	public boolean evaluate(Map<Variable,Value> affectations,
			Set<Pair<Value, Label>> shapes,
			Set<Pair<Pair<Value,Value>, Label>> triples) {
		return !subSentence.evaluate(affectations,shapes,triples);
	}
	
	@Override
	public String toString() {
		return "NOT{ "+subSentence+" }" ;
	}
}
