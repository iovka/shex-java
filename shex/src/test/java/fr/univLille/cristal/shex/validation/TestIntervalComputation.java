/**
Copyright 2017 University of Lille

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/
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
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpression;
import fr.univLille.cristal.shex.schema.analysis.SchemaRulesInstrumentations;
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
	
	private static final Object TRIPLE_CONSTRAINTS_LIST_PROP = new Object();
	
	
	@Test
	public void testEmptyExpression() {
		IntervalComputation ic = new IntervalComputation(TRIPLE_CONSTRAINTS_LIST_PROP);
		TripleExpression expr = new EmptyTripleExpression();
		expr.accept(ic);
		assertEquals(Interval.STAR, ic.getResult());
	}
	
	@Test
	public void testTripleConstraintSimpleBag() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		Bag word = createBag(tca, 2); 

		IntervalComputation ic = new IntervalComputation(TRIPLE_CONSTRAINTS_LIST_PROP);
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

		IntervalComputation ic = new IntervalComputation(TRIPLE_CONSTRAINTS_LIST_PROP);
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

		IntervalComputation ic = new IntervalComputation(TRIPLE_CONSTRAINTS_LIST_PROP);
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

		IntervalComputation ic = new IntervalComputation(TRIPLE_CONSTRAINTS_LIST_PROP);
		expr.accept(ic, word);
		Interval interval = ic.getResult();
		assertEquals(Interval.ONE, interval);
	}

	
	@Test
	public void testRepeatedTripleConstraintThree() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		RepeatedTripleExpression expr = new RepeatedTripleExpression(tca, new Interval(3,4)); 
		Bag word = createBag(tca, 2); 

		IntervalComputation ic = new IntervalComputation(TRIPLE_CONSTRAINTS_LIST_PROP);
		expr.accept(ic, word);
		Interval interval = ic.getResult();
		assertEquals(Interval.EMPTY, interval);
	}

	
	@Test
	public void testSomeOf_1() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		TripleConstraint tcb = (TripleConstraint) tc("b:: .");
		Bag word = createBag(tca, 2, tcb, 0); 

		TripleExpression expr = someof(tca, tcb);
		
		IntervalComputation ic = new IntervalComputation(TRIPLE_CONSTRAINTS_LIST_PROP);
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

		TripleExpression expr = someof(tca, tcb);
		
		IntervalComputation ic = new IntervalComputation(TRIPLE_CONSTRAINTS_LIST_PROP);
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

		TripleExpression expr = eachof(tca, tcb);
		
		IntervalComputation ic = new IntervalComputation(TRIPLE_CONSTRAINTS_LIST_PROP);
		expr.accept(ic, word);
		Interval interval = ic.getResult();
		assertEquals(Interval.EMPTY, interval);
	}	
	
	@Test
	public void testEachOf_2() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		TripleConstraint tcb = (TripleConstraint) tc("b:: .");
		Bag word = createBag(tca, 2, tcb, 2); 

		TripleExpression expr = eachof(tca, tcb);
		
		IntervalComputation ic = new IntervalComputation(TRIPLE_CONSTRAINTS_LIST_PROP);
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
		TripleExpression expr = new RepeatedTripleExpression(tcc, Interval.STAR);
		SchemaRulesInstrumentations.computeAndSetTripleConstraintsOn(expr, TRIPLE_CONSTRAINTS_LIST_PROP);
		
		IntervalComputation ic = new IntervalComputation(TRIPLE_CONSTRAINTS_LIST_PROP);
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
		TripleExpression subexpr = someof(tca, tcb);
		TripleExpression expr = new RepeatedTripleExpression(subexpr, Interval.STAR);
		SchemaRulesInstrumentations.computeAndSetTripleConstraintsOn(expr, TRIPLE_CONSTRAINTS_LIST_PROP);
		
		// {a,a,b,b}
		Bag word = createBag(tca, 2, tcb, 2, tcc, 0);
		
		IntervalComputation ic = new IntervalComputation(TRIPLE_CONSTRAINTS_LIST_PROP);
		expr.accept(ic, word);
		Interval interval = ic.getResult();
		assertEquals(Interval.PLUS, interval);
	}
	
	
	@Test
	public void testStar_nonempty_subword_empty_subinterval() {
		TripleConstraint tca = (TripleConstraint) tc("a:: .");
		TripleConstraint tcb = (TripleConstraint) tc("b:: .");
		
		// (a;b)*
		TripleExpression subexpr = eachof(tca, tcb);
		TripleExpression expr = new RepeatedTripleExpression(subexpr, Interval.STAR);
		SchemaRulesInstrumentations.computeAndSetTripleConstraintsOn(expr, TRIPLE_CONSTRAINTS_LIST_PROP);

		// {a,a,b}
		Bag word = createBag(tca, 2, tcb, 1); 
		
		IntervalComputation ic = new IntervalComputation(TRIPLE_CONSTRAINTS_LIST_PROP);
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
		TripleExpression expr = new RepeatedTripleExpression(tcc, Interval.PLUS);
		SchemaRulesInstrumentations.computeAndSetTripleConstraintsOn(expr, TRIPLE_CONSTRAINTS_LIST_PROP);
		
		IntervalComputation ic = new IntervalComputation(TRIPLE_CONSTRAINTS_LIST_PROP);
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
		TripleExpression subexpr = someof(tca, tcb);
		TripleExpression expr = new RepeatedTripleExpression(subexpr, Interval.PLUS);
		SchemaRulesInstrumentations.computeAndSetTripleConstraintsOn(expr, TRIPLE_CONSTRAINTS_LIST_PROP);
		
		// {a,a,b}
		Bag word = createBag(tca, 2, tcb, 1, tcc, 0);
		
		IntervalComputation ic = new IntervalComputation(TRIPLE_CONSTRAINTS_LIST_PROP);
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
		TripleExpression subexpr = someof(tca, tcb);
		TripleExpression expr = new RepeatedTripleExpression(subexpr, new Interval(2,4));
		SchemaRulesInstrumentations.computeAndSetTripleConstraintsOn(expr, TRIPLE_CONSTRAINTS_LIST_PROP);
		TripleExpression unfoldedTripleExpression = SchemaRulesStaticAnalysis.computeUnfoldedRepetitions(expr);

		// {a,a,b}
		Bag word = createBag(tca, 2, tcb, 1, tcc, 0);

		IntervalComputation ic = new IntervalComputation(TRIPLE_CONSTRAINTS_LIST_PROP);
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
 

		TripleExpression subexpr = eachof(tca, tcb);
		TripleExpression expr = new RepeatedTripleExpression(subexpr, Interval.OPT);

		Bag word = createBag(tca, 2, tcb, 2);

		IntervalComputation ic = new IntervalComputation(TRIPLE_CONSTRAINTS_LIST_PROP);
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
		TripleExpression subexpr = eachof(tca, tcb);
		TripleExpression expr = new RepeatedTripleExpression(subexpr, Interval.PLUS);
		SchemaRulesInstrumentations.computeAndSetTripleConstraintsOn(expr, TRIPLE_CONSTRAINTS_LIST_PROP);

		// {a,a,b}
		Bag word = createBag(tca, 2, tcb, 1); 
		
		IntervalComputation ic = new IntervalComputation(TRIPLE_CONSTRAINTS_LIST_PROP);
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

	private void computeTripleConstraints2 (TripleExpression expr) {
		SchemaRulesInstrumentations.computeAndSetTripleConstraintsOn(expr, TRIPLE_CONSTRAINTS_LIST_PROP);
	}

}
