/*******************************************************************************
 * Copyright (C) 2018 Université de Lille - Inria
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package fr.univLille.cristal.shex.validation;

import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.eachof;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.someof;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.tc;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import fr.univLille.cristal.shex.schema.abstrsynt.EmptyTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.NonRefTripleExpr;
import fr.univLille.cristal.shex.schema.analysis.InstrumentationListsOfTripleConstraintsOnTripleExpressions;
import fr.univLille.cristal.shex.schema.analysis.SchemaRulesStaticAnalysis;
import fr.univLille.cristal.shex.util.Interval;
import fr.univLille.cristal.shex.validation.Bag;
import fr.univLille.cristal.shex.validation.IntervalComputation;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TestIntervalComputation {

	@Test
	public void testEmptyExpression() {
		IntervalComputation ic = new IntervalComputation();
		NonRefTripleExpr expr = new EmptyTripleExpression();
		expr.accept(ic);
		assertEquals(Interval.STAR, ic.getResult());
	}
	
	@Test
	public void testTripleConstraintSimpleBag() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		Bag word = createBag(tca, 2); 

		IntervalComputation ic = new IntervalComputation();
		tca.accept(ic, word);
		Interval interval = ic.getResult();
		assertEquals(2, interval.min);
		assertEquals(2, interval.max);
	}
	
	@Test
	public void testTripleConstraintLessSimpleBag() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		TripleConstraint tcb = (TripleConstraint) tc("b:: .");
		Bag word = createBag(tca, 2, tcb, 1); 

		IntervalComputation ic = new IntervalComputation();
		tca.accept(ic, word);
		Interval interval = ic.getResult();
		assertEquals(2, interval.min);
		assertEquals(2, interval.max);
	}	
	

	@Test
	public void testRepeatedTripleConstraintOne() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		RepeatedTripleExpression expr = new RepeatedTripleExpression(tca, new Interval(2,4)); 
		Bag word = createBag(tca, 8); 

		IntervalComputation ic = new IntervalComputation();
		expr.accept(ic, word);
		Interval interval = ic.getResult();
		assertEquals(2, interval.min);
		assertEquals(4, interval.max);
	}
	
	@Test
	public void testRepeatedTripleConstraintTwo() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		RepeatedTripleExpression expr = new RepeatedTripleExpression(tca, new Interval(2,4)); 
		Bag word = createBag(tca, 2); 

		IntervalComputation ic = new IntervalComputation();
		expr.accept(ic, word);
		Interval interval = ic.getResult();
		assertEquals(Interval.ONE, interval);
	}

	
	@Test
	public void testRepeatedTripleConstraintThree() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		RepeatedTripleExpression expr = new RepeatedTripleExpression(tca, new Interval(3,4)); 
		Bag word = createBag(tca, 2); 

		IntervalComputation ic = new IntervalComputation();
		expr.accept(ic, word);
		Interval interval = ic.getResult();
		assertEquals(Interval.EMPTY, interval);
	}

	
	@Test
	public void testSomeOf_1() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		TripleConstraint tcb = (TripleConstraint) tc("b:: .");
		Bag word = createBag(tca, 2, tcb, 0); 

		NonRefTripleExpr expr = someof(tca, tcb);
		
		IntervalComputation ic = new IntervalComputation();
		expr.accept(ic, word);
		Interval interval = ic.getResult();
		assertEquals(2, interval.min);
		assertEquals(2, interval.max);
	}	
	
	@Test
	public void testSomeOf_2() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		TripleConstraint tcb = (TripleConstraint) tc("b:: .");
		Bag word = createBag(tca, 2, tcb, 1); 

		NonRefTripleExpr expr = someof(tca, tcb);
		
		IntervalComputation ic = new IntervalComputation();
		expr.accept(ic, word);
		Interval interval = ic.getResult();
		assertEquals(3, interval.min);
		assertEquals(3, interval.max);
	}	
	
	@Test
	public void testEachOf_1() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		TripleConstraint tcb = (TripleConstraint) tc("b:: .");
		Bag word = createBag(tca, 2, tcb, 0); 

		NonRefTripleExpr expr = eachof(tca, tcb);
		
		IntervalComputation ic = new IntervalComputation();
		expr.accept(ic, word);
		Interval interval = ic.getResult();
		assertEquals(Interval.EMPTY, interval);
	}	
	
	@Test
	public void testEachOf_2() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		TripleConstraint tcb = (TripleConstraint) tc("b:: .");
		Bag word = createBag(tca, 2, tcb, 2); 

		NonRefTripleExpr expr = eachof(tca, tcb);
		
		IntervalComputation ic = new IntervalComputation();
		expr.accept(ic, word);
		Interval interval = ic.getResult();
		assertEquals(2, interval.min);
		assertEquals(2, interval.max);
	}	
	
	@Test
	public void testStar_empty_subword() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		TripleConstraint tcb = (TripleConstraint) tc("b:: .");
		TripleConstraint tcc = (TripleConstraint) tc("c:: .");
		
		Bag word = createBag(tca, 2, tcb, 2, tcc, 0); 
		NonRefTripleExpr expr = new RepeatedTripleExpression(tcc, Interval.STAR);
		InstrumentationListsOfTripleConstraintsOnTripleExpressions.getInstance().apply(expr);
		
		IntervalComputation ic = new IntervalComputation();
		expr.accept(ic, word);
		Interval interval = ic.getResult();
		assertEquals(Interval.STAR, interval);
	}
	
	@Test
	public void testStar_nonempty_subword_nonempty_subinterval() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		TripleConstraint tcb = (TripleConstraint) tc("b:: .");
		TripleConstraint tcc = (TripleConstraint) tc("c:: .");
	 
		// (a | b) *
		NonRefTripleExpr subexpr = someof(tca, tcb);
		NonRefTripleExpr expr = new RepeatedTripleExpression(subexpr, Interval.STAR);
		InstrumentationListsOfTripleConstraintsOnTripleExpressions.getInstance().apply(expr);
		
		// {a,a,b,b}
		Bag word = createBag(tca, 2, tcb, 2, tcc, 0);
		
		IntervalComputation ic = new IntervalComputation();
		expr.accept(ic, word);
		Interval interval = ic.getResult();
		assertEquals(Interval.PLUS, interval);
	}
	
	
	@Test
	public void testStar_nonempty_subword_empty_subinterval() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		TripleConstraint tcb = (TripleConstraint) tc("b:: .");
		
		// (a;b)*
		NonRefTripleExpr subexpr = eachof(tca, tcb);
		NonRefTripleExpr expr = new RepeatedTripleExpression(subexpr, Interval.STAR);
		InstrumentationListsOfTripleConstraintsOnTripleExpressions.getInstance().apply(expr);
		
		// {a,a,b}
		Bag word = createBag(tca, 2, tcb, 1); 
		
		IntervalComputation ic = new IntervalComputation();
		expr.accept(ic, word);
		Interval interval = ic.getResult();
		assertEquals(Interval.EMPTY, interval);
	}
	
	@Test
	public void testPlus_empty_subword() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		TripleConstraint tcb = (TripleConstraint) tc("b:: .");
		TripleConstraint tcc = (TripleConstraint) tc("c:: .");
		
		Bag word = createBag(tca, 2, tcb, 2, tcc, 0); 
		NonRefTripleExpr expr = new RepeatedTripleExpression(tcc, Interval.PLUS);
		InstrumentationListsOfTripleConstraintsOnTripleExpressions.getInstance().apply(expr);
		
		IntervalComputation ic = new IntervalComputation();
		expr.accept(ic, word);
		Interval interval = ic.getResult();
		assertEquals(Interval.ZERO, interval);
	}
	
	@Test
	public void testPlus_nonempty_subword_nonempty_subinterval() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		TripleConstraint tcb = (TripleConstraint) tc("b:: .");
		TripleConstraint tcc = (TripleConstraint) tc("c:: .");
	 
		// (a | b) *
		NonRefTripleExpr subexpr = someof(tca, tcb);
		NonRefTripleExpr expr = new RepeatedTripleExpression(subexpr, Interval.PLUS);
		InstrumentationListsOfTripleConstraintsOnTripleExpressions.getInstance().apply(expr);
		
		// {a,a,b}
		Bag word = createBag(tca, 2, tcb, 1, tcc, 0);
		
		IntervalComputation ic = new IntervalComputation();
		expr.accept(ic, word);
		Interval interval = ic.getResult();
		assertEquals(1, interval.min);
		assertEquals(3, interval.max);
	}
	
	public void testIllegalRepetition() {

		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		TripleConstraint tcb = (TripleConstraint) tc("b:: .");
		TripleConstraint tcc = (TripleConstraint) tc("c:: .");

		// (a | b) [2;4]
		NonRefTripleExpr subexpr = someof(tca, tcb);
		NonRefTripleExpr expr = new RepeatedTripleExpression(subexpr, new Interval(2,4));
		InstrumentationListsOfTripleConstraintsOnTripleExpressions.getInstance().apply(expr);
		NonRefTripleExpr unfoldedTripleExpression = SchemaRulesStaticAnalysis.computeUnfoldedRepetitions(expr);

		// {a,a,b}
		Bag word = createBag(tca, 2, tcb, 1, tcc, 0);

		IntervalComputation ic = new IntervalComputation();
		assertTrue(true);
		unfoldedTripleExpression.accept(ic, word);
		// FIXME: add assertions
		// [0;0] is the correct interval, because we have changed the triple expressions while unfolding
		System.out.println(ic.getResult());
	}
	
	@Test
	public void testOpt() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		TripleConstraint tcb = (TripleConstraint) tc("b:: .");
 

		NonRefTripleExpr subexpr = eachof(tca, tcb);
		NonRefTripleExpr expr = new RepeatedTripleExpression(subexpr, Interval.OPT);

		Bag word = createBag(tca, 2, tcb, 2);

		IntervalComputation ic = new IntervalComputation();
		expr.accept(ic, word);
		Interval interval = ic.getResult();
		
		assertEquals(2, interval.min);
		assertEquals(Interval.UNBOUND, interval.max);

	}
	
	
	
	
	@Test
	public void testPlus_nonempty_subword_empty_subinterval() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		TripleConstraint tcb = (TripleConstraint) tc("b:: .");
		
		// (a;b)*
		NonRefTripleExpr subexpr = eachof(tca, tcb);
		NonRefTripleExpr expr = new RepeatedTripleExpression(subexpr, Interval.PLUS);
		InstrumentationListsOfTripleConstraintsOnTripleExpressions.getInstance().apply(expr);

		// {a,a,b}
		Bag word = createBag(tca, 2, tcb, 1); 
		
		IntervalComputation ic = new IntervalComputation();
		expr.accept(ic, word);
		Interval interval = ic.getResult();
		assertEquals(Interval.EMPTY, interval);
	}
	
	
	
	
	private Bag createBag (Object ... letterMult) {
		Map<TripleConstraint, Integer> map = new HashMap<>();
		for (int i = 0; i < letterMult.length / 2; i++) {
			TripleConstraint letter = (TripleConstraint) letterMult[2*i];
			Integer mult = (Integer) letterMult[2*i+1];
			map.put(letter, mult);
		}
		Bag bag = new Bag();
		for (Map.Entry<TripleConstraint, Integer> e: map.entrySet()) {
			for (int i = 0; i < e.getValue(); i++)
				bag.increment(e.getKey());
		}
		return bag;
	}

}
