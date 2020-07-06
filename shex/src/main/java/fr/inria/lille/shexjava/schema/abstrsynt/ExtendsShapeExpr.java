package fr.inria.lille.shexjava.schema.abstrsynt;

import java.util.Map;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;
import fr.inria.lille.shexjava.exception.UndefinedReferenceException;

public class ExtendsShapeExpr extends ShapeExpr {
	protected Label baseShapeExprLabel;
	protected ShapeExpr baseShapeExpr;
	protected ShapeExpr extension;
	
	
	public ExtendsShapeExpr(Label baseShapeExprLabel, ShapeExpr extension) {
		super();
		this.baseShapeExprLabel = baseShapeExprLabel;
		this.extension = extension;
	}	
	
	public ShapeExpr getExtension() {
		return extension;
	}
	

	public Label getBaseShapeExprLabel() {
		return baseShapeExprLabel;
	}

	public ShapeExpr getBaseShapeExpr() {
		return baseShapeExpr;
	}


	public void resolveReferences (Map<Label,ShapeExpr> shexprsMap) throws UndefinedReferenceException {
		if (baseShapeExpr != null)
			throw new IllegalStateException("References can be resolved at most once in ExtendsShapeExpr " + baseShapeExprLabel);
		if (shexprsMap.containsKey(baseShapeExprLabel))
			baseShapeExpr = shexprsMap.get(getBaseShapeExprLabel());
		 else
			throw new UndefinedReferenceException("Undefined shape label for base: " + baseShapeExprLabel);
	}


	@Override
	public <ResultType> void accept(ShapeExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitExtendsShapeExpr(this, arguments);		
	}

	@Override
	public String toPrettyString(Map<String, String> prefixes) {
		return " EXTENDS @"+baseShapeExprLabel.toPrettyString(prefixes)+ " "+extension.toPrettyString(prefixes);
	}

}
