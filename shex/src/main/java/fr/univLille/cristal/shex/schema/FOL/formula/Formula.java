package fr.univLille.cristal.shex.schema.FOL.formula;

import java.util.ArrayList;

import fr.univLille.cristal.shex.util.CollectionToString;

public class Formula {
	protected ArrayList<Quantifier> quantifiers;
	protected Sentence sentence;

	public Formula(ArrayList<Quantifier> quantifiers,Sentence sentence) {
		this.quantifiers = quantifiers;
		this.sentence = sentence;
	}
	
	@Override
	public String toString() {
		return CollectionToString.collectionToString(quantifiers, " ", "", "")+"  "+sentence;
	}

}
