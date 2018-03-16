package fr.univLille.cristal.shex.schema.FOL;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import fr.univLille.cristal.shex.schema.FOL.formula.Formula;
import fr.univLille.cristal.shex.schema.FOL.parsing.FOLVisitorImpl;

class FOLParserTestLogique {
	
	@Test
	void testUndefined() throws IOException {
		System.out.println("Test Undefined:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "Not(x=y)";
		try {
			ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
			for (Formula f:formulas)
				System.out.println(f);
			fail("");
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
	}

	@Test
	void testNot() throws IOException {
		System.out.println("Test Not:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "forall x exists y Not(x=y)";
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas)
			System.out.println(text+" >>> "+f);
	}

	
	@Test
	void testOr() throws IOException {
		System.out.println("Test Or:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "forall x exists y or(x=y,x=y)";
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas)
			System.out.println(text+" >>> "+f);
	}
	
	
	@Test
	void testAnd() throws IOException {
		System.out.println("Test And:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "forall x exists y and(x=y,x=y)";
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas)
			System.out.println(text+" >>> "+f);
	}
	
	@Test
	void testImpl() throws IOException {
		System.out.println("Test ->:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "forall x exists y ->(x=y,x=y)";
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas)
			System.out.println(text+" >>> "+f);
	}
	
	@Test
	void testOrAnd() throws IOException {
		System.out.println("Test OrAnd:");
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String text = "forall x exists y and(or(x>y,x=y),x=y)";
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		for (Formula f:formulas)
			System.out.println(text+" >>> "+f);
	}
}