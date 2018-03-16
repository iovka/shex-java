package fr.univLille.cristal.shex.schema.FOL.formula;

import fr.univLille.cristal.shex.schema.Label;

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
	public boolean evaluate() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		return label+"("+variable1+","+variable2+")";
	}

}