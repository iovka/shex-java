package fr.univLille.cristal.shex.schema.FOL.formula;

import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.util.Pair;

public class Implication implements Sentence{
	protected Sentence s1;
	protected Sentence s2;

	public Implication(Sentence s1,Sentence s2) {
		this.s1 = s1;
		this.s2 = s2;
	}

	@Override
	public int evaluate(Map<Variable,Value> affectations,
							Set<Pair<Value, Label>> shapes,
							Set<Pair<Pair<Value,Value>, Label>> triples) {
		int s1Score = s1.evaluate(affectations,shapes,triples);
		int s2Score = s2.evaluate(affectations,shapes,triples);
		if (s1Score==3 || s2Score==3)
			return 3;
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

}
