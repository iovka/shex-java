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
	public boolean evaluate(Map<Variable,Value> affectations,
							Set<Pair<Value, Label>> shapes,
							Set<Pair<Pair<Value,Value>, Label>> triples) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		return v1+""+'\u2260'+v2;
	}

}
