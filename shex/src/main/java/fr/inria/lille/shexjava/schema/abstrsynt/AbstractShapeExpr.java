package fr.inria.lille.shexjava.schema.abstrsynt;

import java.util.Map;

import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;

public class AbstractShapeExpr extends ShapeExpr {
	protected ShapeExpr expr;

	public AbstractShapeExpr(ShapeExpr expr) {
		super();
		this.expr = expr;
	}

	@Override
	public <ResultType> void accept(ShapeExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitAbstractShape(this, arguments);
	}

	@Override
	public String toPrettyString(Map<String, String> prefixes) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
