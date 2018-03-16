package fr.univLille.cristal.shex.schema.FOL.formula;

public abstract class Operator implements Sentence{
	protected Variable v1;
	protected Variable v2;
	
	public Operator(Variable v1,Variable v2) {
		this.v1 = v1;
		this.v2 = v2;
	}

}