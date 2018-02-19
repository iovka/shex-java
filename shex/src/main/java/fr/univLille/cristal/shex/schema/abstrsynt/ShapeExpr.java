package fr.univLille.cristal.shex.schema.abstrsynt;

import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.analysis.ShapeExpressionVisitor;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public abstract class ShapeExpr{
	
	protected ShapeExprLabel id = null;
	
	public void setId(ShapeExprLabel id) {
		if (this.id != null)
			throw new IllegalStateException("ID can be set only once");
		this.id = id;
	}
	
	public ShapeExprLabel getId () {
		return this.id;
	}
	
	public abstract <ResultType> void accept (ShapeExpressionVisitor<ResultType> visitor, Object ... arguments);
}
