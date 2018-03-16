package fr.univLille.cristal.shex.schema.FOL.formula;

public class OpEqualInf extends Operator{

	public OpEqualInf(Variable v1, Variable v2) {
		super(v1, v2);
	}

	@Override
	public boolean evaluate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		return v1+""+'\u2264'+v2;
	}
}
