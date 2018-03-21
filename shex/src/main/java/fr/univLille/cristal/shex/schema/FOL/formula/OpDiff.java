package fr.univLille.cristal.shex.schema.FOL.formula;

import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.util.Pair;

public class OpDiff extends Operator{

	public OpDiff(Variable v1, Variable v2) {
		super(v1, v2);
	}

	@Override
	public int evaluate(Map<Variable,Value> affectations,
							Set<Pair<Value, Label>> shapes,
							Set<Pair<Pair<Value,Value>, Label>> triples) throws Exception {
		int res = super.evaluate(affectations, shapes, triples);
		if (res !=-1)
			return res;
		if (isEqual(affectations.get(v2), affectations.get(v1)))
			return 0;
		return 1;
	}
	
	@Override
	public String toString() {
		return v1+""+'\u2260'+v2;
	}

}
