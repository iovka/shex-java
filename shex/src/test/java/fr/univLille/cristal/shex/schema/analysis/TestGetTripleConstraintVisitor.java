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
import java.util.Map;

import org.junit.Test;

import fr.univLille.cristal.shex.schema.abstrsynt.EachOf;
import fr.univLille.cristal.shex.schema.abstrsynt.EmptyTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor;
import fr.univLille.cristal.shex.schema.abstrsynt.OneOf;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.NonRefTripleExpr;
import fr.univLille.cristal.shex.schema.analysis.ComputeListsOfTripleConstraintsVisitor;
import fr.univLille.cristal.shex.util.Interval;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TestGetTripleConstraintVisitor {
	
	@Test
	public void testEmptyExpression() {
		
		EmptyTripleExpression empty = new EmptyTripleExpression();
		
		ComputeListsOfTripleConstraintsVisitor visitor = new ComputeListsOfTripleConstraintsVisitor();
		empty.accept(visitor);
		Map<TripleExpr, List<TripleConstraint>> map = visitor.getResult();
			
		assertTrue(map.get(empty).isEmpty());
		
		//TODO remove assertTrue(empty.getTripleConstraints().isEmpty());
	}
	
	@Test
	public void testTripleConstraintExpression(){
		NonRefTripleExpr tripleConstraint = SimpleSchemaConstructor.tc("a :: @SL");
		ComputeListsOfTripleConstraintsVisitor visitor = new ComputeListsOfTripleConstraintsVisitor();
		tripleConstraint.accept(visitor);
		Map<TripleExpr, List<TripleConstraint>> map = visitor.getResult();
		
		assertEquals(1, map.get(tripleConstraint).size());
	}
	
	@Test
	public void testEachOfExpression(){
		NonRefTripleExpr tc1 = SimpleSchemaConstructor.tc("a :: @SL1");
		NonRefTripleExpr tc2 = SimpleSchemaConstructor.tc("a :: @SL2");
		List<NonRefTripleExpr> list = new ArrayList<NonRefTripleExpr>();
		list.add(tc1);
		list.add(tc2);
		EachOf eachOf = new EachOf(list);
		
		ComputeListsOfTripleConstraintsVisitor visitor = new ComputeListsOfTripleConstraintsVisitor();
		eachOf.accept(visitor);
		Map<TripleExpr, List<TripleConstraint>> map = visitor.getResult();
		List<TripleConstraint> l = map.get(eachOf);

		assertEquals(2, l.size());
		assertEquals(tc1, l.get(0));
		assertEquals(tc2, l.get(1));
		
	}
	
	
	@Test
	public void testSomeOfExpression() {
		NonRefTripleExpr tc1 = SimpleSchemaConstructor.tc("a :: @SL1");
		NonRefTripleExpr tc2 = SimpleSchemaConstructor.tc("a :: @SL2");
		List<NonRefTripleExpr> list = new ArrayList<NonRefTripleExpr>();
		list.add(tc1);
		list.add(tc2);
		OneOf eachOf = new OneOf(list);
		
		ComputeListsOfTripleConstraintsVisitor visitor = new ComputeListsOfTripleConstraintsVisitor();
		eachOf.accept(visitor);
		Map<TripleExpr, List<TripleConstraint>> map = visitor.getResult();
		List<TripleConstraint> l = map.get(eachOf);
		
		assertEquals(2, l.size());
		assertEquals(tc1, l.get(0));
		assertEquals(tc2, l.get(1));
	}
	
	
	@Test
	public void testRepeatedExpression(){
		NonRefTripleExpr tc1 = SimpleSchemaConstructor.tc("a :: @SL1");
		NonRefTripleExpr tc2 = SimpleSchemaConstructor.tc("a :: @SL2");
		List<NonRefTripleExpr> list = new ArrayList<NonRefTripleExpr>();
		list.add(tc1);
		list.add(tc2);
		OneOf eachOf = new OneOf(list);
		RepeatedTripleExpression expr = new RepeatedTripleExpression(eachOf, Interval.PLUS);
		
		ComputeListsOfTripleConstraintsVisitor visitor = new ComputeListsOfTripleConstraintsVisitor();
		expr.accept(visitor);
		Map<TripleExpr, List<TripleConstraint>> map = visitor.getResult();
		List<TripleConstraint> l = map.get(eachOf);

		assertEquals(2, l.size());
		assertEquals(tc1, l.get(0));
		assertEquals(tc2, l.get(1));
	}
	
	@Test
	public void testComplexExpression(){
		NonRefTripleExpr tc1 = SimpleSchemaConstructor.tc("a :: @SL1");
		NonRefTripleExpr tc2 = SimpleSchemaConstructor.tc("a :: @SL2");
		NonRefTripleExpr tc3 = SimpleSchemaConstructor.tc("b :: @SL1");
		NonRefTripleExpr tc4 = SimpleSchemaConstructor.tc("c :: @SL2");
		
		List<NonRefTripleExpr> list1 = new ArrayList<NonRefTripleExpr>();
		list1.add(tc1);
		list1.add(tc2);
		OneOf someOf = new OneOf(list1);
		RepeatedTripleExpression repeatedTripleExpression = new RepeatedTripleExpression(someOf, new Interval(1,2));
		
		List<NonRefTripleExpr> list2 = new ArrayList<NonRefTripleExpr>();
		list2.add(repeatedTripleExpression);
		list2.add(tc3);
		list2.add(new EmptyTripleExpression());
		list2.add(tc4);
		
		EachOf eachOf = new EachOf(list2);

		ComputeListsOfTripleConstraintsVisitor visitor = new ComputeListsOfTripleConstraintsVisitor();
		eachOf.accept(visitor);
		Map<TripleExpr, List<TripleConstraint>> map = visitor.getResult();
		List<TripleConstraint> l = map.get(eachOf);		
		
		assertEquals(4, l.size());
		assertEquals(tc1, l.get(0));
		assertEquals(tc2, l.get(1));
		assertEquals(tc3, l.get(2));
		assertEquals(tc4, l.get(3));
	}

}
