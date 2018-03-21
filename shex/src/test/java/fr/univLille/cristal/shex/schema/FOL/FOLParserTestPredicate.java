package fr.univLille.cristal.shex.schema.FOL;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import fr.univLille.cristal.shex.schema.FOL.formula.Formula;
import fr.univLille.cristal.shex.schema.FOL.parsing.FOLVisitorImpl;

class FOLParserTestPredicate {

	@Test
	void testShapePredIRI() throws IOException {
		System.out.println("Test ShapePredIri:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String label = "<http://example.shex.io/Shape>";
		String text = "forall x exists y "+label+"(x)";
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas)
			System.out.println(text+" >>> "+f);
	}


	@Test
	void testShapePredBnode() throws IOException {
		System.out.println("Test ShapePredBnode:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String label = "_:Shape";
		String text = "forall x exists y "+label+"(x)";
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas)
			System.out.println(text+" >>> "+f);
	}
	
	@Test
	void testShapetripleIRI() throws IOException {
		System.out.println("Test Not:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String label = "<http://example.shex.io/Triple>";
		String text = "forall x exists y "+label+"(x,y)";
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas)
			System.out.println(text+" >>> "+f);
	}
	
	@Test
	void testTriplePredBnode() throws IOException {
		System.out.println("Test TriplePredBnode:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String label = "_:Triple";
		String text = "forall x exists y "+label+"(x,y)";
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas)
			System.out.println(text+" >>> "+f);
	}
}