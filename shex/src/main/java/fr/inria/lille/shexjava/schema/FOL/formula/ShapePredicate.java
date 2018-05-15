package fr.inria.lille.shexjava.schema.FOL.formula;

import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.Pair;

public class ShapePredicate implements Sentence{
	protected Label label;
	protected Variable variable;
	
	public ShapePredicate(Label label,Variable variable) {
		this.label = label;
		this.variable = variable;
	}

	@Override
	public int evaluate(Map<Variable,Value> affectations,
							Set<Pair<Value, Label>> shapes,
							Set<Pair<Pair<Value,Value>, Label>> triples) throws Exception {
		if (!affectations.containsKey(variable))
			return 2;
		Pair<Value,Label> key = new Pair<Value,Label>(affectations.get(variable),label);
		if (shapes.contains(key))
			return 1;
		return 0;
	}

	@Override
	public String toString() {
		return label+"("+variable+")";
	}
}
