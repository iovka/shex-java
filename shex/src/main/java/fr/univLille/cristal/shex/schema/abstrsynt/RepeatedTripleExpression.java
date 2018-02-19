package fr.univLille.cristal.shex.schema.abstrsynt;

import fr.univLille.cristal.shex.schema.analysis.TripleExpressionVisitor;
import fr.univLille.cristal.shex.util.Interval;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class RepeatedTripleExpression extends TripleExpr {
	
	private TripleExpr subExpr;
	private Interval card;
	
	public RepeatedTripleExpression (TripleExpr subExpr, Interval card) {
		this.subExpr = subExpr;
		this.card = card;
	}
	
	public TripleExpr getSubExpression() {
		return this.subExpr;
	}

	public Interval getCardinality () {
		return this.card;
	}
	
	@Override
	public String toString() {
		String format;
		if (subExpr instanceof AbstractNaryTripleExpr) 
			format = "(%s)%s";
		else
			format = "%s%s";
		return String.format(format, subExpr.toString(), card.toString());
	}

	@Override
	public <ResultType> void accept(TripleExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitRepeated (this, arguments);
	}

	private TripleExpr unfoldedVersion;
	
	public TripleExpr getUnfoldedVersion() {
		return this.unfoldedVersion;
	}
	
	public void setUnfoldedVersion (TripleExpr expr) {
		if (this.unfoldedVersion != null)
			throw new IllegalStateException("Unfolded version can be set at most once.");
		this.unfoldedVersion = expr;
	}
	
}
