package fr.univLille.cristal.shex.schema.abstrsynt;

import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.analysis.ShapeExpressionVisitor;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class ShapeExprRef extends ShapeExpr {
	
	private final ShapeExprLabel label;
	private ShapeExpr def;
	
	public ShapeExprRef(ShapeExprLabel label) {
		this.label = label;
	}

	public ShapeExprLabel getLabel () {
		return this.label;
	}
	

	@Override
	public <ResultType> void accept(ShapeExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitShapeExprRef(this, arguments);
	}

	
	// FIXME : implement as an instrumentation
	public void setShapeDefinition(ShapeExpr def) {
		if (this.def != null)
			throw new IllegalStateException("Shape definition can be set at most once");
		this.def = def;
	}
	
	public ShapeExpr getShapeDefinition () {
		return this.def;
	}
		
	@Override
	public String toString() {
		return "@"+label.toString();
	}
}
