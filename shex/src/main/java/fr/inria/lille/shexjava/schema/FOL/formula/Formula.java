package fr.inria.lille.shexjava.schema.FOL.formula;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.api.RDFTerm;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.CollectionToString;
import fr.inria.lille.shexjava.util.Pair;

public class Formula {
	protected ArrayList<Quantifier> quantifiers;
	protected Sentence sentence;
	protected Map<Variable,Set<Variable>> sameTypeVariables;
	private Map<Variable,RDFTerm> lastAffectations;

	public Formula(ArrayList<Quantifier> quantifiers,Sentence sentence) {
		this.quantifiers = quantifiers;
		this.sentence = sentence;
		this.sameTypeVariables = new HashMap<Variable,Set<Variable>>();
		recFindOperatorVariables(sentence);
	}
	
	public boolean evaluate(List<RDFTerm> possibleValues,
							Set<Pair<RDFTerm, Label>> shapes,
							Set<Pair<Pair<RDFTerm,RDFTerm>, Label>> triples) throws Exception {
		ArrayList<Quantifier> copy = new ArrayList<Quantifier>();
		for (Quantifier e:quantifiers)
			copy.add(e);
		int result = recEvaluation(copy,new HashMap<Variable,RDFTerm>(),possibleValues,shapes,triples);
		if (result==2)
			System.err.println("Incomplete evaluation");
		return result==1;
	}
	
	protected int recEvaluation(ArrayList<Quantifier> left,
									Map<Variable,RDFTerm> affectations,
									List<RDFTerm> possibleValues,
									Set<Pair<RDFTerm, Label>> shapes,
									Set<Pair<Pair<RDFTerm,RDFTerm>, Label>> triples) throws Exception
	{
		lastAffectations = new HashMap<>(affectations);
		int currentScore = sentence.evaluate(affectations, shapes, triples);
		if (currentScore != 2)
			return currentScore;
		if (left.size()==0 && currentScore==2) {
				System.err.println("Evaluate returned partial evaluation, but all variables are supposed to be affected.");
			return currentScore;
		}
		Quantifier first = left.get(0);
		left.remove(0);
		for (RDFTerm e:possibleValues) {
			if (this.sameTypeVariables.containsKey(first.getVariable())){
				IRI selectedType=null;
				for (Variable x:affectations.keySet())
					if (sameTypeVariables.get(first.getVariable()).contains(x))
						selectedType=((Literal) affectations.get(x)).getDatatype();
				if (selectedType==null && OperatorRestricted.isCorrectlyDefined(e))
					affectations.put(first.getVariable(), e);
				else
					if ((e instanceof Literal) && ((Literal) e).getDatatype().equals(selectedType))
						affectations.put(first.getVariable(), e);
					else
						continue;
			}else {
				affectations.put(first.getVariable(), e);
			}
			int score = recEvaluation(left, affectations, possibleValues, shapes, triples);
			if ((first instanceof Forall) && (score!=1)) {
				affectations.remove(first.getVariable());
				left.add(0, first);
				return score;
			}
			if ((first instanceof Exists) && (score==1)) {
				affectations.remove(first.getVariable());
				left.add(0, first);
				return score;
			}
		}
		affectations.remove(first.getVariable());
		left.add(0, first);
		if (first instanceof Forall)
			return 1;
		if (first instanceof Exists)
			return 0;
		return -1;
	}
	
	
	/** Return the last affectations done by a call of evaluate. It the evaluation fail, it will give you the affectations on which the formula failed.
	 * 
	 * @return
	 */
	public Map<Variable, RDFTerm> getLastAffectations() {
		return lastAffectations;
	}

	@Override
	public String toString() {
		return CollectionToString.collectionToString(quantifiers, " ", "", "")+"  "+sentence;
	}	

	protected void recFindOperatorVariables(Sentence s) {
		if (s instanceof OperatorRestricted) {
			for (Variable v:((OperatorRestricted) s).getVariables()){
				if (!sameTypeVariables.containsKey(v))
					sameTypeVariables.put(v, new HashSet<Variable>());
				sameTypeVariables.get(v).addAll(((OperatorRestricted) s).getVariables());
				sameTypeVariables.get(v).remove(v);
			}
		}
		if (s instanceof CompositeSentence) {
			for (Sentence st:((CompositeSentence) s).getSubSentences())
				recFindOperatorVariables(st);
		}
	}
}
