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
	protected ArrayList<Variable> operatorVariables;

	public Formula(ArrayList<Quantifier> quantifiers,Sentence sentence) {
		this.quantifiers = quantifiers;
		this.sentence = sentence;
		this.operatorVariables = new ArrayList<>();
		recFindOperatorVariables(sentence);
	}
	
	public boolean evaluate(Set<Pair<Value, Label>> shapes,
							Set<Pair<Pair<Value,Value>, Label>> triples) {
		return false;
	}
	
	protected int recEvaluation(ArrayList<Quantifier> left,
									Map<Variable,Value> affectations,
									List<Value> possibleValues,
									Set<Pair<Value, Label>> shapes,
									Set<Pair<Pair<Value,Value>, Label>> triples) throws Exception
	{
		int currentScore = sentence.evaluate(affectations, shapes, triples);
		if (currentScore != 2)
			return currentScore;
		
		if (left.size()==0 && currentScore==2) {
				System.err.println("Evaluate returned partial evaluation, but all variables are supposed to be affected.");
			return currentScore;
		}
		Quantifier first = left.get(0);
		left.remove(0);
		for (Value e:possibleValues) {
			if (this.operatorVariables.contains(first.getVariable())){
				if (Operator.isCorrectlyDefined(e))
					affectations.put(first.getVariable(), e);
				else
					continue;
			}else {
				affectations.put(first.getVariable(), e);
			}
			int score = recEvaluation(left, affectations, possibleValues, shapes, triples);
			if ((first instanceof Forall) && (score!=1))
				return score;
			if ((first instanceof Exists) && (score==1))
				return score;
		}
		if (first instanceof Forall)
			return 1;
		if (first instanceof Exists)
			return 0;
		return -1;
	}
	
	@Override
	public String toString() {
		return CollectionToString.collectionToString(quantifiers, " ", "", "")+"  "+sentence;
	}	

	protected void recFindOperatorVariables(Sentence s) {
		if (s instanceof Operator) 
			this.operatorVariables.addAll(((Operator) s).getVariables());
		if (s instanceof CompositeSentence) {
			for (Sentence st:((CompositeSentence) s).getSubSentences())
				recFindOperatorVariables(st);
		}
	}
}
