package fr.inria.lille.shexjava.schema.abstrsynt;

import java.util.Map;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;

public class ExtendsShapeExpr extends ShapeExpr {
	protected Label baseShapeExprLabel;
	protected ShapeExpr extension;
	
	
	
	public ExtendsShapeExpr(Label baseShapeExprLabel, ShapeExpr extension) {
		super();
		this.baseShapeExprLabel = baseShapeExprLabel;
		this.extension = extension;
	}

	@Override
	public <ResultType> void accept(ShapeExpressionVisitor<ResultType> visitor, Object... arguments) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toPrettyString(Map<String, String> prefixes) {
		// TODO Auto-generated method stub
		return null;
	}

}
