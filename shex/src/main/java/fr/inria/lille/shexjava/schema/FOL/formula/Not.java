package fr.inria.lille.shexjava.schema.FOL.formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.rdf.api.RDFTerm;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.Pair;

public class Not implements Sentence,CompositeSentence{
	protected Sentence subSentence;
	
	public Not(Sentence subSentence) {
		this.subSentence = subSentence;
	}

	@Override
	public int evaluate(Map<Variable, RDFTerm> affectations,
			Set<Pair<RDFTerm, Label>> shapes,
			Set<Pair<Pair<RDFTerm, RDFTerm>, Label>> triples) throws Exception {
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
