package fr.univLille.cristal.shex.schema.abstrsynt;

import fr.univLille.cristal.shex.schema.TripleExprLabel;
import fr.univLille.cristal.shex.schema.analysis.TripleExpressionVisitor;

/**
 * 
 * @author Iovka Boneva
 * 11 oct. 2017
 */
public class TripleExprRef extends TripleExpr {
	private TripleExprLabel label;
	private TripleExpr tripleExp;
	
	
	public TripleExprRef(TripleExprLabel label) {
		this.label = label;
	}
	
	
	public TripleExpr getTripleExp() {
		return tripleExp;
	}


	public void setTripleDefinition(TripleExpr def) {
		if (this.tripleExp != null)
			throw new IllegalStateException("Triple Expression definition can be set at most once");
		this.tripleExp = def;
	}


	public TripleExprLabel getLabel() {
		return label;
	}


	@Override
	public <ResultType> void accept(TripleExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitTripleExprReference(this, arguments);
	}
	
	
	@Override
	public String toString() {
		return "@"+label;
	}
}
