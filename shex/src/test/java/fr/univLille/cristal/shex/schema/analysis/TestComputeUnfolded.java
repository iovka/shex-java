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

package fr.univLille.cristal.shex.schema.analysis;

import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.*;
import static org.junit.Assert.*;

import org.junit.Test;

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.abstrsynt.EachOfTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.EmptyTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpression;
import fr.univLille.cristal.shex.schema.analysis.ComputeUnfoldedArbitraryRepetitionsVisitor;
import fr.univLille.cristal.shex.schema.analysis.SchemaRulesStaticAnalysis;
import fr.univLille.cristal.shex.util.Interval;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TestComputeUnfolded {

	@Test
	public void testEmptyExpr() {
		TripleExpression empty = new EmptyTripleExpression();
		
		ComputeUnfoldedArbitraryRepetitionsVisitor visitor = new ComputeUnfoldedArbitraryRepetitionsVisitor();
		empty.accept(visitor);
		assertEquals(empty, visitor.getResult());	
	}
	
	@Test
	public void testTripleConstraint() {
		TripleExpression tc = tc("a::SL");
		ComputeUnfoldedArbitraryRepetitionsVisitor visitor = new ComputeUnfoldedArbitraryRepetitionsVisitor();
		tc.accept(visitor);
		assertEquals(tc, visitor.getResult());
	}
	
	@Test
	public void testNoRepetitions() {
		TripleExpression expr = eachof(tc("a::SL"), tc("b::SL"));
		ComputeUnfoldedArbitraryRepetitionsVisitor visitor = new ComputeUnfoldedArbitraryRepetitionsVisitor();
		expr.accept(visitor);
		assertEquals(expr, visitor.getResult());
	}
	
	@Test
	public void testRepetedTripleConstraintNoUnfolding() {
		TripleExpression expr = eachof(tc("a::SL"), tc("b::SL[2;3]"));
		ComputeUnfoldedArbitraryRepetitionsVisitor visitor = new ComputeUnfoldedArbitraryRepetitionsVisitor();
		expr.accept(visitor);
		assertEquals(expr, visitor.getResult());
	}
	
	@Test
	public void testStarRepetition1() {
		TripleExpression expr = new RepeatedTripleExpression(eachof(tc("a::SL"), tc("b::SL")), Interval.STAR);
		ComputeUnfoldedArbitraryRepetitionsVisitor visitor = new ComputeUnfoldedArbitraryRepetitionsVisitor();
		expr.accept(visitor);
		assertEquals(expr, visitor.getResult());
	}
	
	@Test
	public void testStarRepetition2() {
		TripleExpression expr1 = new RepeatedTripleExpression(eachof(tc("a::SL"), tc("b::SL")), Interval.STAR);
		TripleExpression expr = someof(expr1, tc("a::SL[4;5]"));
		ComputeUnfoldedArbitraryRepetitionsVisitor visitor = new ComputeUnfoldedArbitraryRepetitionsVisitor();
		expr.accept(visitor);
		assertEquals(expr, visitor.getResult());
	}

	@Test
	public void testOneFiniteRepetitionToUnfold() {
		TripleConstraint tca = (TripleConstraint) tc("a::SL");
		TripleConstraint tcb = (TripleConstraint) tc("b::SL");
		TCProperty a_prop = tca.getPropertySet().getAsFiniteSet().iterator().next();
		TCProperty b_prop = tcb.getPropertySet().getAsFiniteSet().iterator().next();
		
		TripleExpression expr = new RepeatedTripleExpression(eachof(tca, tcb), new Interval(2,2));
		ComputeUnfoldedArbitraryRepetitionsVisitor visitor = new ComputeUnfoldedArbitraryRepetitionsVisitor();
		expr.accept(visitor);
		TripleExpression result = visitor.getResult();
		List<TripleConstraint> tripleConstraints = SchemaRulesStaticAnalysis.collectTripleConstraints(result);
		assertEquals(4, tripleConstraints.size());
 
		List<TripleConstraint> a_constraints = 
				tripleConstraints.stream()
				.filter(tc -> tc.getPropertySet().contains(a_prop))
				.collect(Collectors.toList());
		assertEquals(2, a_constraints.size());
		
		List<TripleConstraint> b_constraints = 
				tripleConstraints.stream()
				.filter(tc -> tc.getPropertySet().contains(b_prop))
				.collect(Collectors.toList());
		assertEquals(2, b_constraints.size());
	}
	
	@Test
	public void testOneUnboundedRepetitionToUnfold() {
		TripleConstraint tca = (TripleConstraint) tc("a::SL");
		TripleConstraint tcb = (TripleConstraint) tc("b::SL");
		
		TripleExpression expr = new RepeatedTripleExpression(eachof(tca, tcb), new Interval(3,Interval.UNBOUND));
		ComputeUnfoldedArbitraryRepetitionsVisitor visitor = new ComputeUnfoldedArbitraryRepetitionsVisitor();
		expr.accept(visitor);
		TripleExpression result = visitor.getResult();
				
		assertTrue(result instanceof EachOfTripleExpression);
		EachOfTripleExpression eachOf = (EachOfTripleExpression) result;
		assertEquals(3, eachOf.getSubExpressions().size());

		List<TripleExpression> notRepetitions =
				eachOf.getSubExpressions().stream()
				.filter(e -> ! (e instanceof RepeatedTripleExpression))
				.collect(Collectors.toList());
		assertEquals(2, notRepetitions.size());
		for (TripleExpression e : notRepetitions)
			assertTrue(e instanceof EachOfTripleExpression);
		
		List<TripleExpression> repetitions = 
				eachOf.getSubExpressions().stream()
				.filter(e -> e instanceof RepeatedTripleExpression)
				.collect(Collectors.toList());
		assertEquals(1, repetitions.size());
		
		RepeatedTripleExpression repetition = (RepeatedTripleExpression) repetitions.get(0);
		assertEquals(Interval.PLUS, repetition.getCardinality());
		assertTrue(repetition.getSubExpression() instanceof EachOfTripleExpression);
	}
	
	@Test
	public void testUnfoldOneRepetitionNotOnTop() {
		
		TripleConstraint tca = (TripleConstraint) tc("a::SL");
		TripleConstraint tcb = (TripleConstraint) tc("b::SL");
		TripleConstraint tcc = (TripleConstraint) tc("c::SL");
		
		TripleExpression rep = new RepeatedTripleExpression(eachof(tca, tcb), new Interval(1,2));
		TripleExpression expr = someof(rep, tcc);
		
		ComputeUnfoldedArbitraryRepetitionsVisitor visitor = new ComputeUnfoldedArbitraryRepetitionsVisitor();
		expr.accept(visitor);
		TripleExpression result = visitor.getResult();
		
		System.out.println(result);
		fail("correct, but add assertions");

	}
	
	
	
	@Test
	public void testNestedRepetitions() {
		
		TripleConstraint tca = (TripleConstraint) tc("a::SL");
		TripleConstraint tcb = (TripleConstraint) tc("b::SL");
		TripleConstraint tcc = (TripleConstraint) tc("c::SL");
		TripleConstraint tcd = (TripleConstraint) tc("d::SL");
		
		TripleExpression rep1 = new RepeatedTripleExpression(eachof(tca, tcb), new Interval(1,2));
		TripleExpression rep2 = new RepeatedTripleExpression(someof(rep1, tcc), new Interval(2,3));
		TripleExpression expr = someof(rep2, tcd);
		
		ComputeUnfoldedArbitraryRepetitionsVisitor visitor = new ComputeUnfoldedArbitraryRepetitionsVisitor();
		expr.accept(visitor);
		TripleExpression result = visitor.getResult();
		
		System.out.println(result);
		fail("check if correct and add assertions");
		
		
	}
	
	
}
