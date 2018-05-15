package fr.inria.lille.shexjava.schema.FOL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.junit.jupiter.api.Test;

import fr.inria.lille.shexjava.schema.FOL.formula.Formula;
import fr.inria.lille.shexjava.schema.FOL.parsing.FOLVisitorImpl;

class FOLEvaluationTest {
	private final static RDF4J rdfFactory = new RDF4J();
	private final static ValueFactory rdf4JFactory = SimpleValueFactory.getInstance();

	@Test
	void testEvaluation() throws Exception {
		System.out.println("Test evaluation 1:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "forall x forall y x!=y";
		List<RDFTerm> values = new ArrayList<RDFTerm>();
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(5)));
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(6)));
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(10)));
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
		List<RDFTerm> values = new ArrayList<RDFTerm>();
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(5)));
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(6)));
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(10)));
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
		List<RDFTerm> values = new ArrayList<RDFTerm>();
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(5)));
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(6)));
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(10)));
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
		List<RDFTerm> values = new ArrayList<RDFTerm>();
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(5)));
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(6)));
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(10)));
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
		List<RDFTerm> values = new ArrayList<RDFTerm>();
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(5)));
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(6)));
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(10)));
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas) {
			System.out.println(f+" : "+f.evaluate(values, new HashSet<>(), new HashSet<>()));
		}
	}
	
	@Test
	void testEvaluation55() throws Exception {
		System.out.println("Test evaluation 55:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "forall x exists y x<=y";
		List<RDFTerm> values = new ArrayList<RDFTerm>();
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(5)));
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(6)));
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(10)));
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
		List<RDFTerm> values = new ArrayList<RDFTerm>();
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(5)));
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(6)));
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(10)));
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
		List<RDFTerm> values = new ArrayList<RDFTerm>();
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(5)));
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(6)));
		values.add(rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(10)));
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas) {
			System.out.println(f+" : "+f.evaluate(values, new HashSet<>(), new HashSet<>()));
		}
	}
	
}