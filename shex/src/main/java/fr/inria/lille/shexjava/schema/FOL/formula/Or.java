package fr.inria.lille.shexjava.schema.FOL.formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.rdf.api.RDFTerm;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.CollectionToString;
import fr.inria.lille.shexjava.util.Pair;

public class Or implements Sentence,CompositeSentence{
	protected ArrayList<Sentence> subSentences;
	
	public Or(ArrayList<Sentence> subSentences) {
		this.subSentences = subSentences;
	}

	@Override
	public int evaluate(Map<Variable, RDFTerm> affectations,
							Set<Pair<RDFTerm, Label>> shapes,
							Set<Pair<Pair<RDFTerm, RDFTerm>, Label>> triples) throws Exception {
		boolean partial=false;
		for (Sentence sub:subSentences) {
			int subScore = sub.evaluate(affectations,shapes,triples);
			if (subScore==2) partial=true;
			if (subScore==1) return 1;
		}
		if (partial)
			return 2;
		return 0;
	}
	
	@Override
	public String toString() {
		return CollectionToString.collectionToString(subSentences, " || ", "( ", " )");
	}
	
	@Override
	public List<Sentence> getSubSentences() {
		return subSentences;
	}
}
