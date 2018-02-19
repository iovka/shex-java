package fr.univLille.cristal.shex.schema.abstrsynt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractNaryTripleExpr extends TripleExpr {

	private final List<TripleExpr> subExpressions;
	
	public AbstractNaryTripleExpr (List<TripleExpr> subExpressions) {
		if (subExpressions.size() < 2)
			throw new IllegalArgumentException("At least two subexpressions required");
		this.subExpressions = new ArrayList<>(subExpressions);
	}
	
	public List<TripleExpr> getSubExpressions () {
		return Collections.unmodifiableList(this.subExpressions);
	}
	
}
