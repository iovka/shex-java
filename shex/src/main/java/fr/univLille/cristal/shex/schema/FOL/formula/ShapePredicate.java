package fr.univLille.cristal.shex.schema.FOL.formula;

import fr.univLille.cristal.shex.schema.Label;

public class ShapePredicate implements Sentence{
	protected Label label;
	protected Variable variable;
	
	public ShapePredicate(Label label,Variable variable) {
		this.label = label;
		this.variable = variable;
	}

	@Override
	public boolean evaluate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		return label+"("+variable+")";
	}
}
