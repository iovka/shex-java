package fr.univLille.cristal.shex.schema.FOL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.junit.jupiter.api.Test;

import fr.univLille.cristal.shex.schema.FOL.formula.Formula;
import fr.univLille.cristal.shex.schema.FOL.parsing.FOLVisitorImpl;

class FOLEvaluationTest2 {
	private final static ValueFactory rdfFactory = SimpleValueFactory.getInstance();


	@Test
	void testEvaluation4() throws Exception {
		System.out.println("Test evaluation 4:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "forall x forall y ->(x!=y,or(x<y,x>y))";
		List<Value> values = new ArrayList<Value>();
		values.add(rdfFactory.createLiteral(5));
		values.add(rdfFactory.createLiteral(6));
		values.add(rdfFactory.createLiteral(10));
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas) {
			System.out.println(f+" : "+f.evaluate(values, new HashSet<>(), new HashSet<>()));
		}
	}


	
}