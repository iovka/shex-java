package fr.univLille.cristal.shex.schema.FOL.formula;

import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.util.Pair;

public class TriplePredicate implements Sentence{
	protected Label label;
	protected Variable variable1;
	protected Variable variable2;
	
	public TriplePredicate(Label label,Variable variable1,Variable variable2) {
		this.label = label;
		this.variable1 = variable1;
		this.variable2 = variable2;
	}

	@Override
	public int evaluate(Map<Variable,Value> affectations,
							Set<Pair<Value, Label>> shapes,
							Set<Pair<Pair<Value,Value>, Label>> triples) {
		Pair<Value,Value> tmp = new Pair<Value,Value>(affectations.get(variable1.name),affectations.get(variable2.name));
		Pair<Pair<Value,Value>, Label> key = new Pair<Pair<Value,Value>, Label>(tmp,label);
		if (triples.contains(key))
			return 1;
		return 0;
	}
	
	@Override
	public String toString() {
		return label+"("+variable1+","+variable2+")";
	}

}