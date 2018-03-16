package fr.univLille.cristal.shex.schema.FOL.formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.util.CollectionToString;
import fr.univLille.cristal.shex.util.Pair;

public class Formula {
	protected ArrayList<Quantifier> quantifiers;
	protected Sentence sentence;

	public Formula(ArrayList<Quantifier> quantifiers,Sentence sentence) {
		this.quantifiers = quantifiers;
		this.sentence = sentence;
	}
	
	public boolean evaluate(Set<Pair<Value, Label>> shapes,
							Set<Pair<Pair<Value,Value>, Label>> triples) {
		return false;
	}
	
	protected boolean recEvaluation(ArrayList<Quantifier> left,
									Map<Variable,Value> affectations,
									List<Value> possibleValues,
									Set<Pair<Value, Label>> shapes,
									Set<Pair<Pair<Value,Value>, Label>> triples)
	{
		Quantifier first = left.get(0);
		left.remove(0);
		for (Value e:possibleValues) {
			affectations.put(first.getVariable(), e);
		}
		return true;
	}
	
	@Override
	public String toString() {
		return CollectionToString.collectionToString(quantifiers, " ", "", "")+"  "+sentence;
	}	

}
