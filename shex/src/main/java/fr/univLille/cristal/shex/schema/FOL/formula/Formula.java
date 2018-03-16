package fr.univLille.cristal.shex.schema.FOL.formula;

import java.util.ArrayList;
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
	
	@Override
	public String toString() {
		return CollectionToString.collectionToString(quantifiers, " ", "", "")+"  "+sentence;
	}	

}
