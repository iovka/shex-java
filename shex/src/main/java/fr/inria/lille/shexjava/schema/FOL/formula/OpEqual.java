package fr.inria.lille.shexjava.schema.FOL.formula;

import java.util.Map;
import java.util.Set;

import org.apache.commons.rdf.api.RDFTerm;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.Pair;

public class OpEqual extends Operator{

	public OpEqual(Variable v1, Variable v2) {
		super(v1, v2);
	}

	@Override
	public int evaluate(Map<Variable, RDFTerm> affectations,
							Set<Pair<RDFTerm, Label>> shapes,
							Set<Pair<Pair<RDFTerm, RDFTerm>, Label>> triples) throws Exception {
		if (!affectations.containsKey(v1) || !affectations.containsKey(v2))
			return 2;
		if (v1.equals(v2))
			return 1;
		return 0;
	}

	@Override
	public String toString() {
		return v1+"="+v2;
	}
}
