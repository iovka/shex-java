package fr.univLille.cristal.shex.schema.FOL.formula;

public class Variable  implements Sentence{
	protected String name;

	public Variable(String name){
		this.name = name;
	}

	@Override
	public boolean evaluate() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
