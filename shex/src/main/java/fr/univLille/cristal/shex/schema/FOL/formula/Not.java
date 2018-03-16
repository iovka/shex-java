package fr.univLille.cristal.shex.schema.FOL.formula;


public class Not implements Sentence{
	protected Sentence subSentence;
	
	public Not(Sentence subSentence) {
		this.subSentence = subSentence;
	}

	@Override
	public boolean evaluate() {
		return !subSentence.evaluate();
	}
	
	@Override
	public String toString() {
		return "NOT{ "+subSentence+" }" ;
	}
}
