package fr.univLille.cristal.shex.schema.analysis;

import java.util.List;
import java.util.Map;

import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;

public class InstrumentationListsOfTripleConstraintsOnTripleExpressions extends Instrumentation<TripleExpr, List<TripleConstraint>> {



	private static InstrumentationListsOfTripleConstraintsOnTripleExpressions theInstance = new InstrumentationListsOfTripleConstraintsOnTripleExpressions();

	public static InstrumentationListsOfTripleConstraintsOnTripleExpressions getInstance() {
		return theInstance;
	}
	
	
	private InstrumentationListsOfTripleConstraintsOnTripleExpressions() {
		super("LIST_TRIPLE_CONSTRAINTS");
	}

	@Override
	public List<TripleConstraint> compute(TripleExpr on, Object... context) {
		Map<TripleExpr, List<TripleConstraint>> map = (Map<TripleExpr, List<TripleConstraint>>) context[0];
		return map.get(on);
	}

}
