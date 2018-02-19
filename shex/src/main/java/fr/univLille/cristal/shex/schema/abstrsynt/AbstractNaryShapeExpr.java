package fr.univLille.cristal.shex.schema.abstrsynt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.univLille.cristal.shex.schema.ShapeExprLabel;

/**
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public abstract class AbstractNaryShapeExpr extends ShapeExpr {
	
	private List<ShapeExpr> subExpressions;
		
	public AbstractNaryShapeExpr (List<ShapeExpr> subExpressions) {
		this(null, subExpressions);
	}
	
	public AbstractNaryShapeExpr (ShapeExprLabel id, List<ShapeExpr> subExpressions) {
		super();
		this.subExpressions = new ArrayList<>(subExpressions);
	}
	
	public List<ShapeExpr> getSubExpressions (){
		return Collections.unmodifiableList(this.subExpressions);
	}
	
}
