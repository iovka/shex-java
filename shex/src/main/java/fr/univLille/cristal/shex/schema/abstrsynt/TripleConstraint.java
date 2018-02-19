package fr.univLille.cristal.shex.schema.abstrsynt;

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.analysis.TripleExpressionVisitor;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TripleConstraint extends TripleExpr {
	private TCProperty property;	
	private ShapeExpr shapeExpr;
		
	
//	public static TripleConstraint newSingleton (TCProperty property, ShapeExpr shapeExpr) {
//		return new TripleConstraint(property, shapeExpr);
//	}
	
	public TripleConstraint (TCProperty property, ShapeExpr shapeExpr ) {
		this.property = property;
		this.shapeExpr = shapeExpr;
	}	

	public TCProperty getProperty(){
		return property;
	}
	
	public ShapeExpr getShapeExpr(){
		return shapeExpr;
	}


	@Override
	public <ResultType> void accept(TripleExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitTripleConstraint(this, arguments);
	}
	
	@Override
	public TripleConstraint clone() {
		return new TripleConstraint(this.property, this.shapeExpr);
	}
	

	@Override
	public String toString() {
		return String.format("%s::%s",
				property.toString(),
				shapeExpr.toString());
	}

}

