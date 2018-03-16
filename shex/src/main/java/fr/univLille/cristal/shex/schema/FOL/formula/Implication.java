package fr.univLille.cristal.shex.schema.FOL.formula;

import fr.univLille.cristal.shex.util.CollectionToString;

public class Implication implements Sentence{
	protected Sentence s1;
	protected Sentence s2;

	public Implication(Sentence s1,Sentence s2) {
		this.s1 = s1;
		this.s2 = s2;
	}

	@Override
	public boolean evaluate() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		return "("+s1+") -> ("+s2+")";
	}

}
