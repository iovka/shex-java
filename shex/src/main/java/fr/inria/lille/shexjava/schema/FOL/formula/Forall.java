package fr.inria.lille.shexjava.schema.FOL.formula;

public class Forall implements Quantifier{
	private Variable variable;
	
	public Forall(Variable variable) {
		this.variable = variable;
	}

	@Override
	public String toString() {
		return '\u2200'+""+variable;
	}

	@Override
	public Variable getVariable() {
		return variable;
	}
	
	
}
