package fr.univLille.cristal.shex.schema.FOL.formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.util.Pair;

public class Implication implements Sentence,CompositeSentence{
	protected Sentence s1;
	protected Sentence s2;

	public Implication(Sentence s1,Sentence s2) {
		this.s1 = s1;
		this.s2 = s2;
	}

	@Override
	public int evaluate(Map<Variable,Value> affectations,
							Set<Pair<Value, Label>> shapes,
							Set<Pair<Pair<Value,Value>, Label>> triples) throws Exception {
		int s1Score = s1.evaluate(affectations,shapes,triples);
		int s2Score = s2.evaluate(affectations,shapes,triples);
		if (s1Score==0 || s2Score==1)
			return 1;
		if (s1Score==2 || s2Score==2)
			return 2;
		return 0;
	}
	
	@Override
	public String toString() {
		return "("+s1+") -> ("+s2+")";
	}

	@Override
	public List<Sentence> getSubSentences() {
		List<Sentence> res = new ArrayList<Sentence>();
		res.add(s1);
		res.add(s2);
		return res;
	}
}
