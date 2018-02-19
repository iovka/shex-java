package fr.univLille.cristal.shex.schema.abstrsynt;

import fr.univLille.cristal.shex.schema.TripleExprLabel;
import fr.univLille.cristal.shex.schema.analysis.TripleExpressionVisitor;

/**
 * 
 * @author Iovka Boneva
 * 11 oct. 2017
 */
public abstract class TripleExpr {
	protected TripleExprLabel id;
	
	public void setId(TripleExprLabel id) {
		if (this.id != null)
			throw new IllegalStateException("ID can be set only once");
		this.id = id;
	}
	
	public TripleExprLabel getId () {
		return this.id;
	}
	
	public abstract <ResultType> void accept (TripleExpressionVisitor<ResultType> visitor, Object... arguments);	
}
