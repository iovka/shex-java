package fr.univLille.cristal.shex.schema.FOL.formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.util.Pair;

public class Not implements Sentence,CompositeSentence{
	protected Sentence subSentence;
	
	public Not(Sentence subSentence) {
		this.subSentence = subSentence;
	}

	@Override
	public int evaluate(Map<Variable,Value> affectations,
			Set<Pair<Value, Label>> shapes,
			Set<Pair<Pair<Value,Value>, Label>> triples) throws Exception {
		int subScore = subSentence.evaluate(affectations,shapes,triples);
		if (subScore>=2)
			return subScore;
		if (subScore==0)
			return 1;
		if (subScore==1)
			return 0;
		return -1;
	}
	
	@Override
	public String toString() {
		return "!( "+subSentence+" )" ;
	}

	@Override
	public List<Sentence> getSubSentences() {
		List<Sentence> res = new ArrayList<Sentence>();
		res.add(subSentence);
		return res;
	}
	
}
