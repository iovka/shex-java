package fr.univLille.cristal.shex.schema.abstrsynt;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.analysis.ShapeExpressionVisitor;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class Shape extends ShapeExpr {
	private boolean closed;
	private Set<TCProperty> extra;
	private TripleExpr tripleExpr;

	/** Creates a {@link Shape} that is forward closed, inverse open, and has no extra properties.
	 * 
	 * @param tripleExpression
	 */

	public Shape(TripleExpr tripleExpression, Set<TCProperty> extraProps, boolean closed) {
		this.tripleExpr = tripleExpression;
		this.extra = Collections.unmodifiableSet(new HashSet<>(extraProps));
		this.closed = closed;
	}
	
	public TripleExpr getTripleExpression () {
		return tripleExpr;
	}
	
	public boolean isClosed () {
		return this.closed;
	}
	
	public Set<TCProperty> getExtraProperties () {
		return extra;
	}
	
	@Override
	public <ResultType> void accept(ShapeExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitShape(this, arguments);
	}

	@Override
	public String toString() {
		String closed = isClosed() ? "CLOSED" : "";
		String extraP = extra.isEmpty() ? "" : "EXTRA" + extra.toString();
		return String.format("(%s %s %s)", closed, extraP, tripleExpr);
	}

	
}
