package fr.univLille.cristal.shex.schema.FOL.formula;

public class Exists implements Quantifier{
	private Variable variable;
	
	public Exists(Variable variable) {
		this.variable = variable;
	}
	
	@Override
	public String toString() {
		return '\u2203'+""+variable;
	}

	@Override
	public Variable getVariable() {
		return variable;
	}
	
}
