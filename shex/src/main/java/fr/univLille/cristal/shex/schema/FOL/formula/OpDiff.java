package fr.univLille.cristal.shex.schema.FOL.formula;

public class OpDiff extends Operator{

	public OpDiff(Variable v1, Variable v2) {
		super(v1, v2);
	}

	@Override
	public boolean evaluate() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		return v1+""+'\u2260'+v2;
	}

}
