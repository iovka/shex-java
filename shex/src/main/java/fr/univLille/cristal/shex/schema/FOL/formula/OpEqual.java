package fr.univLille.cristal.shex.schema.FOL.formula;

import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.util.Pair;

public class OpEqual extends Operator{

	public OpEqual(Variable v1, Variable v2) {
		super(v1, v2);
	}

	@Override
	public int evaluate(Map<Variable,Value> affectations,
							Set<Pair<Value, Label>> shapes,
							Set<Pair<Pair<Value,Value>, Label>> triples) throws Exception {
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
