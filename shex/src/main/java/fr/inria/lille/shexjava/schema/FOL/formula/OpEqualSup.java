package fr.inria.lille.shexjava.schema.FOL.formula;

import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.Pair;

public class OpEqualSup extends OperatorRestricted{

	public OpEqualSup(Variable v1, Variable v2) {
		super(v1, v2);
	}

	@Override
	public int evaluate(Map<Variable,Value> affectations,
							Set<Pair<Value, Label>> shapes,
							Set<Pair<Pair<Value,Value>, Label>> triples) throws Exception {
		int res = super.evaluate(affectations, shapes, triples);
		if (res !=-1)
			return res;
		if (isStrictlyInferior(affectations.get(v2), affectations.get(v1)) ||
			isEqual(affectations.get(v2), affectations.get(v1)))
			return 1;
		return 0;
	}

	@Override
	public String toString() {
		return v1+""+'\u2265'+v2;
	}
}
