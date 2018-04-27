package fr.inria.lille.shexjava.schema.FOL.formula;

import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.Pair;

public class OpDiff extends Operator{

	public OpDiff(Variable v1, Variable v2) {
		super(v1, v2);
	}

	@Override
	public int evaluate(Map<Variable,Value> affectations,
							Set<Pair<Value, Label>> shapes,
							Set<Pair<Pair<Value,Value>, Label>> triples) throws Exception {
		if (!affectations.containsKey(v1) || !affectations.containsKey(v2))
			return 2;
		if (affectations.get(v2).equals(affectations.get(v1)))
			return 0;
		return 1;
	}
	
	@Override
	public String toString() {
		return v1+""+'\u2260'+v2;
	}

}
