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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.univLille.cristal.shex.schema.abstrsynt.EachOfTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.EmptyTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor;
import fr.univLille.cristal.shex.schema.abstrsynt.SomeOfTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpression;
import fr.univLille.cristal.shex.schema.analysis.ComputeAndSetTripleConstraintsVisitor;
import fr.univLille.cristal.shex.util.Interval;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TestGetTripleConstraintVisitor {

	Object TRIPLE_CONSTR_LIST = new Object();
	
	@Test
	public void testEmptyExpression() {
		
		EmptyTripleExpression empty = new EmptyTripleExpression();
		
		ComputeAndSetTripleConstraintsVisitor visitor = new ComputeAndSetTripleConstraintsVisitor(TRIPLE_CONSTR_LIST);
		empty.accept(visitor);
			
		List<TripleConstraint> result = (List<TripleConstraint>) empty.getAttributes().getAttribute(TRIPLE_CONSTR_LIST);
		assertTrue(result.isEmpty());
		
		//TODO remove assertTrue(empty.getTripleConstraints().isEmpty());
	}
	
	@Test
	public void testTripleConstraintExpression(){
		TripleExpression tripleConstraint = SimpleSchemaConstructor.tc("a :: @SL");
		ComputeAndSetTripleConstraintsVisitor visitor = new ComputeAndSetTripleConstraintsVisitor(TRIPLE_CONSTR_LIST);
		tripleConstraint.accept(visitor);
		
		List<TripleConstraint> result = (List<TripleConstraint>) tripleConstraint.getAttributes().getAttribute(TRIPLE_CONSTR_LIST);
		assertEquals(1, result.size());
	}
	
	@Test
	public void testEachOfExpression(){
		TripleExpression tc1 = SimpleSchemaConstructor.tc("a :: @SL1");
		TripleExpression tc2 = SimpleSchemaConstructor.tc("a :: @SL2");
		List<TripleExpression> list = new ArrayList<TripleExpression>();
		list.add(tc1);
		list.add(tc2);
		EachOfTripleExpression eachOf = new EachOfTripleExpression(list);
		
		ComputeAndSetTripleConstraintsVisitor visitor = new ComputeAndSetTripleConstraintsVisitor(TRIPLE_CONSTR_LIST);
		eachOf.accept(visitor);
		
		List<TripleConstraint> result = (List<TripleConstraint>) eachOf.getAttributes().getAttribute(TRIPLE_CONSTR_LIST);


		assertEquals(2, result.size());
		assertEquals(tc1, result.get(0));
		assertEquals(tc2, result.get(1));
		
//		assertEquals(2, eachOf.getTripleConstraints().size());
//		assertEquals(tc1, eachOf.getTripleConstraints().get(0));
//		assertEquals(tc2, eachOf.getTripleConstraints().get(1));
	}
	
	
	@Test
	public void testSomeOfExpression() {
		TripleExpression tc1 = SimpleSchemaConstructor.tc("a :: @SL1");
		TripleExpression tc2 = SimpleSchemaConstructor.tc("a :: @SL2");
		List<TripleExpression> list = new ArrayList<TripleExpression>();
		list.add(tc1);
		list.add(tc2);
		SomeOfTripleExpression eachOf = new SomeOfTripleExpression(list);
		
		ComputeAndSetTripleConstraintsVisitor visitor = new ComputeAndSetTripleConstraintsVisitor(TRIPLE_CONSTR_LIST);
		eachOf.accept(visitor);
		
		List<TripleConstraint> result = (List<TripleConstraint>) eachOf.getAttributes().getAttribute(TRIPLE_CONSTR_LIST);
		
		assertEquals(2, result.size());
		assertEquals(tc1, result.get(0));
		assertEquals(tc2, result.get(1));
		
//		assertEquals(2, eachOf.getTripleConstraints().size());
//		assertEquals(tc1, eachOf.getTripleConstraints().get(0));
//		assertEquals(tc2, eachOf.getTripleConstraints().get(1));
	}
	
	
	@Test
	public void testRepeatedExpression(){
		TripleExpression tc1 = SimpleSchemaConstructor.tc("a :: @SL1");
		TripleExpression tc2 = SimpleSchemaConstructor.tc("a :: @SL2");
		List<TripleExpression> list = new ArrayList<TripleExpression>();
		list.add(tc1);
		list.add(tc2);
		SomeOfTripleExpression eachOf = new SomeOfTripleExpression(list);
		RepeatedTripleExpression expr = new RepeatedTripleExpression(eachOf, Interval.PLUS);
		
		ComputeAndSetTripleConstraintsVisitor visitor = new ComputeAndSetTripleConstraintsVisitor(TRIPLE_CONSTR_LIST);
		expr.accept(visitor);
		
		List<TripleConstraint> result = (List<TripleConstraint>) eachOf.getAttributes().getAttribute(TRIPLE_CONSTR_LIST);
		
		assertEquals(2, result.size());
		assertEquals(tc1, result.get(0));
		assertEquals(tc2, result.get(1));

	
//		assertEquals(2, eachOf.getTripleConstraints().size());
//		assertEquals(tc1, eachOf.getTripleConstraints().get(0));
//		assertEquals(tc2, eachOf.getTripleConstraints().get(1));
	}
	
	@Test
	public void testComplexExpression(){
		TripleExpression tc1 = SimpleSchemaConstructor.tc("a :: @SL1");
		TripleExpression tc2 = SimpleSchemaConstructor.tc("a :: @SL2");
		TripleExpression tc3 = SimpleSchemaConstructor.tc("b :: @SL1");
		TripleExpression tc4 = SimpleSchemaConstructor.tc("c :: @SL2");
		
		List<TripleExpression> list1 = new ArrayList<TripleExpression>();
		list1.add(tc1);
		list1.add(tc2);
		SomeOfTripleExpression someOf = new SomeOfTripleExpression(list1);
		RepeatedTripleExpression repeatedTripleExpression = new RepeatedTripleExpression(someOf, new Interval(1,2));
		
		List<TripleExpression> list2 = new ArrayList<TripleExpression>();
		list2.add(repeatedTripleExpression);
		list2.add(tc3);
		list2.add(new EmptyTripleExpression());
		list2.add(tc4);
		
		EachOfTripleExpression eachOf = new EachOfTripleExpression(list2);

		ComputeAndSetTripleConstraintsVisitor visitor = new ComputeAndSetTripleConstraintsVisitor(TRIPLE_CONSTR_LIST);
		eachOf.accept(visitor);
				
		List<TripleConstraint> result = (List<TripleConstraint>) eachOf.getAttributes().getAttribute(TRIPLE_CONSTR_LIST);
		
		assertEquals(4, result.size());
		assertEquals(tc1, result.get(0));
		assertEquals(tc2, result.get(1));
		assertEquals(tc3, result.get(2));
		assertEquals(tc4, result.get(3));
		
//		assertEquals(4, eachOf.getTripleConstraints().size());
//		assertEquals(tc1, eachOf.getTripleConstraints().get(0));
//		assertEquals(tc2, eachOf.getTripleConstraints().get(1));
//		assertEquals(tc3, eachOf.getTripleConstraints().get(2));
//		assertEquals(tc4, eachOf.getTripleConstraints().get(3));
	}

}
