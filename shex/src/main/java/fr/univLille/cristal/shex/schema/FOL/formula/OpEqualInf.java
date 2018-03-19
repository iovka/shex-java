package fr.univLille.cristal.shex.schema.FOL.formula;

import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.util.Pair;

public class OpEqualInf extends Operator{

	public OpEqualInf(Variable v1, Variable v2) {
		super(v1, v2);
	}

	@Override
	public int evaluate(Map<Variable,Value> affectations,
							Set<Pair<Value, Label>> shapes,
							Set<Pair<Pair<Value,Value>, Label>> triples) {
		int res = super.evaluate(affectations, shapes, triples);
		if (res !=-1)
			return res;
		if (isStrictlyInferior(affectations.get(v1.name), affectations.get(v2.name)) ||
			isEqual(affectations.get(v1.name), affectations.get(v2.name)))
			return 1;
		return 0;
	}

	@Override
	public String toString() {
		return v1+""+'\u2264'+v2;
	}
}
