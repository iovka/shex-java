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

class FOLEvaluationTest {
	private final static ValueFactory rdfFactory = SimpleValueFactory.getInstance();

	@Test
	void testEvaluation() throws Exception {
		System.out.println("Test evaluation 1:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "forall x forall y x!=y";
		List<Value> values = new ArrayList<Value>();
		values.add(rdfFactory.createLiteral(5));
		values.add(rdfFactory.createLiteral(6));
		values.add(rdfFactory.createLiteral(10));
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas) {
			System.out.println(f+" : "+f.evaluate(values, new HashSet<>(), new HashSet<>()));
		}
	}
	
	@Test
	void testEvaluation2() throws Exception {
		System.out.println("Test evaluation 2:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "forall x forall y ->(x!=y,x!=y)";
		List<Value> values = new ArrayList<Value>();
		values.add(rdfFactory.createLiteral(5));
		values.add(rdfFactory.createLiteral(6));
		values.add(rdfFactory.createLiteral(10));
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas) {
			System.out.println(f+" : "+f.evaluate(values, new HashSet<>(), new HashSet<>()));
		}
	}
	
	@Test
	void testEvaluation3() throws Exception {
		System.out.println("Test evaluation 3:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "forall x forall y ->(x!=y,or(x>y,x<y))";
		List<Value> values = new ArrayList<Value>();
		values.add(rdfFactory.createLiteral(5));
		values.add(rdfFactory.createLiteral(6));
		values.add(rdfFactory.createLiteral(10));
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas) {
			System.out.println(f+" : "+f.evaluate(values, new HashSet<>(), new HashSet<>()));
		}
	}

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

	@Test
	void testEvaluation5() throws Exception {
		System.out.println("Test evaluation 5:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "forall x forall y or(x<=y,x>=y)";
		List<Value> values = new ArrayList<Value>();
		values.add(rdfFactory.createLiteral(5));
		values.add(rdfFactory.createLiteral(6));
		values.add(rdfFactory.createLiteral(10));
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas) {
			System.out.println(f+" : "+f.evaluate(values, new HashSet<>(), new HashSet<>()));
		}
	}
	
	@Test
	void testEvaluation6() throws Exception {
		System.out.println("Test evaluation 6:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "forall x forall y not(and(x<y,x>=y))";
		List<Value> values = new ArrayList<Value>();
		values.add(rdfFactory.createLiteral(5));
		values.add(rdfFactory.createLiteral(6));
		values.add(rdfFactory.createLiteral(10));
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas) {
			System.out.println(f+" : "+f.evaluate(values, new HashSet<>(), new HashSet<>()));
		}
	}
	
	@Test
	void testEvaluation7() throws Exception {
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