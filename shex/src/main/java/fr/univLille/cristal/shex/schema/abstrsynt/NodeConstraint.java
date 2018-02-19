package fr.univLille.cristal.shex.schema.abstrsynt;

import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.analysis.ShapeExpressionVisitor;
import fr.univLille.cristal.shex.schema.concrsynt.SetOfNodes;

/**
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public class NodeConstraint extends ShapeExpr {

	private SetOfNodes setOfNodes;
	
	public NodeConstraint (SetOfNodes setOfNodes) {
		this.setOfNodes = setOfNodes;
	}
	
	public boolean contains(Value node) {
		return setOfNodes.contains(node);
	}

	@Override
	public <ResultType> void accept(ShapeExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitNodeConstraint(this, arguments);
	}
	
	@Override
	public String toString() {
		return setOfNodes+"";
	}
}
