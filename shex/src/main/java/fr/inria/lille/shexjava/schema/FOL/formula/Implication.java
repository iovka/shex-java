package fr.inria.lille.shexjava.schema.FOL.formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.rdf.api.RDFTerm;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.Pair;

public class Implication implements Sentence,CompositeSentence{
	protected Sentence s1;
	protected Sentence s2;

	public Implication(Sentence s1,Sentence s2) {
		this.s1 = s1;
		this.s2 = s2;
	}

	@Override
	public int evaluate(Map<Variable, RDFTerm> affectations,
							Set<Pair<RDFTerm, Label>> shapes,
							Set<Pair<Pair<RDFTerm, RDFTerm>, Label>> triples) throws Exception {
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
