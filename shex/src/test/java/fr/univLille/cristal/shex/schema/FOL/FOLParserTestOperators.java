package fr.univLille.cristal.shex.schema.FOL;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import fr.univLille.cristal.shex.schema.FOL.formula.Formula;
import fr.univLille.cristal.shex.schema.FOL.parsing.FOLVisitorImpl;

class FOLParserTestOperators {

	@Test
	void testEqual() throws IOException {
		System.out.println("Test Equal:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "forall x exists y x=y";
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas)
			System.out.println(text+" >>> "+f);
	}
	
	@Test
	void testDiff() throws IOException {
		System.out.println("Test Diff:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "forall x exists y x!=y";
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas)
			System.out.println(text+" >>> "+f);
	}
	
	@Test
	void testInf() throws IOException {
		System.out.println("Test Inf:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "forall x exists y x<y";
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas)
			System.out.println(text+" >>> "+f);
	}

	@Test
	void testEqualInf() throws IOException {
		System.out.println("Test EqualInf:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "forall x exists y x<=y";
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas)
			System.out.println(text+" >>> "+f);
	}
	
	@Test
	void testSup() throws IOException {
		System.out.println("Test Sup:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "forall x exists y x>y";
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas)
			System.out.println(text+" >>> "+f);
	}

	@Test
	void testEqualSup() throws IOException {
		System.out.println("Test EqualSup:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "forall x exists y x>=y";
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas)
			System.out.println(text+" >>> "+f);
	}
}
