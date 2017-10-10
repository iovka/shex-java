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

import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.not;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.se;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.shapeAnd;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.shapeOr;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.tc;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import fr.univLille.cristal.shex.schema.abstrsynt.NeighbourhoodConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAndExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNotExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOrExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpression;
import fr.univLille.cristal.shex.schema.analysis.SchemaRulesStaticAnalysis;
import fr.univLille.cristal.shex.schema.analysis.SchemaRulesStaticAnalysis.FlattenAndOr;
import fr.univLille.cristal.shex.schema.analysis.SchemaRulesStaticAnalysis.PushNegationsDownVisitor;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TestFlattenAnd {

	@Test
	public void testNeighConstr() {
		ShapeExpression expr = se(tc("ex:a :: SL"));
		FlattenAndOr visitor = SchemaRulesStaticAnalysis.getInstance().new FlattenAndOr();
		
		expr.accept(visitor, new Object());
		ShapeExpression result = visitor.getResult();
		assertTrue(result instanceof NeighbourhoodConstraint);
	}

	@Test
	public void testNegatedNeighConstr() {
		ShapeExpression expr = not(se(tc("ex:a :: SL")));
		FlattenAndOr visitor = SchemaRulesStaticAnalysis.getInstance().new FlattenAndOr();
		
		expr.accept(visitor, new Object());
		ShapeExpression result = visitor.getResult();
		assertTrue(result instanceof ShapeNotExpression);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDoublyNegatedNeighConstr() {
		ShapeExpression expr = not(not(se(tc("ex:a :: SL"))));
		
		FlattenAndOr visitor = SchemaRulesStaticAnalysis.getInstance().new FlattenAndOr();
		expr.accept(visitor, new Object());
	}
	
	@Test
	public void testShapeOr() {
		ShapeExpression expr = shapeOr(tc("ex:a :: SL"), tc("ex:b :: SL"));
		
		FlattenAndOr visitor = SchemaRulesStaticAnalysis.getInstance().new FlattenAndOr();
		expr.accept(visitor, new Object());
		ShapeExpression result = visitor.getResult();
		
		assertTrue(result instanceof ShapeOrExpression);
		System.out.println(result);
	}
	
	@Test
	public void testShapeOrOr() {
		TripleExpression tc1 = tc("ex:a :: SL");
		TripleExpression tc2 = tc("ex:b :: SL");
		TripleExpression tc3 = tc("ex:c :: SL");
		
		ShapeExpression expr = shapeOr(shapeOr(tc1, tc2), tc3);
		
		FlattenAndOr visitor = SchemaRulesStaticAnalysis.getInstance().new FlattenAndOr();
		expr.accept(visitor, new Object());
		ShapeExpression result = visitor.getResult();
		
		assertTrue(result instanceof ShapeOrExpression);
	
		List<ShapeExpression> subExpr = ((ShapeOrExpression) result).getSubExpressions();
		assertEquals(3, subExpr.size());
		
		List<TripleExpression> subExprList = new ArrayList<>(); 
		for (ShapeExpression e : subExpr) {
			assertTrue(e instanceof NeighbourhoodConstraint);
			subExprList.add(((NeighbourhoodConstraint)e).getTripleExpression());
		}
		assertTrue(subExprList.contains(tc1));
		assertTrue(subExprList.contains(tc2));
		assertTrue(subExprList.contains(tc3));
		System.out.println(result);
	}
	
	@Test
	public void testShapeSingletonOrOr() {
		ShapeExpression expr = shapeOr(shapeOr(tc("ex:a :: SL"), tc("ex:b :: SL"), tc("ex:c :: SL")));
		System.out.println(expr);
		
		FlattenAndOr visitor = SchemaRulesStaticAnalysis.getInstance().new FlattenAndOr();
		expr.accept(visitor, new Object());
		ShapeExpression result = visitor.getResult();
		
		assertTrue(result instanceof ShapeOrExpression);
	
		List<ShapeExpression> subExpr = ((ShapeOrExpression) result).getSubExpressions();
		assertEquals(3, subExpr.size());
		for (ShapeExpression e : subExpr) {
			assertTrue(e instanceof NeighbourhoodConstraint);
		}
		
		System.out.println(result);
	}
	
	@Test
	public void testShapeAndAnd() {
		ShapeExpression expr = shapeAnd(shapeAnd(tc("ex:a :: SL"), tc("ex:b :: SL")), shapeAnd(tc("ex:c :: SL"), tc("ex:d :: SL")));
		System.out.println(expr);
		
		FlattenAndOr visitor = SchemaRulesStaticAnalysis.getInstance().new FlattenAndOr();
		expr.accept(visitor, new Object());
		ShapeExpression result = visitor.getResult();
		
		assertTrue(result instanceof ShapeAndExpression);
	
		List<ShapeExpression> subExpr = ((ShapeAndExpression) result).getSubExpressions();
		assertEquals(4, subExpr.size());
		for (ShapeExpression e : subExpr) {
			assertTrue(e instanceof NeighbourhoodConstraint);
		}
		
		System.out.println(result);
	}
	
	
	@Test
	public void testMixedAndOr() {
		TripleExpression tca = tc("ex:a :: SL"), 
				tcb = tc("ex:b :: SL"),
				tcc = tc("ex:c :: SL"),
				tcd = tc("ex:d :: SL"),
				tce = tc("ex:e :: SL"),
				tcf = tc("ex:f :: SL"),
				tcg = tc("ex:g :: SL"),
				tch = tc("ex:h :: SL"),
				tci = tc("ex:i :: SL"),
				tcj = tc("ex:j :: SL"),
				tck = tc("ex:k :: SL"),
				tcl = tc("ex:l :: SL"),
				tcm = tc("ex:m :: SL"),
				tcn =  tc("ex:n :: SL");
		ShapeExpression and1 = shapeAnd(tca, tcb);
		ShapeExpression and2 = shapeAnd(tcc, tcd);
		ShapeExpression and3 = shapeAnd(and1, and2);

		ShapeExpression or1 = shapeOr(tce, tcf);
		ShapeExpression or2 = shapeOr(tcg, tch);
		ShapeExpression or3 = shapeOr(and3, or1, or2);
		
		ShapeExpression and4 = shapeAnd(tci, tcj);
		ShapeExpression and5 = shapeAnd(tck, tcl);
		ShapeExpression or4 = shapeOr(tcm, tcn);
		ShapeExpression expr = shapeAnd(or3, and4, and5, or4);
		
		System.out.println(expr);
		
		FlattenAndOr visitor = SchemaRulesStaticAnalysis.getInstance().new FlattenAndOr();
		expr.accept(visitor, new Object());
		ShapeExpression result = visitor.getResult();
		
		assertTrue(result instanceof ShapeAndExpression);
		List<ShapeExpression> subExpr = ((ShapeAndExpression) result).getSubExpressions();
		assertEquals(6, subExpr.size());
	
		Set<ShapeOrExpression> subOrSet = new HashSet<>();
		Set<TripleExpression> subLeafSet = new HashSet<>();
		for (ShapeExpression e : subExpr) {
			if (e instanceof ShapeOrExpression)
				subOrSet.add((ShapeOrExpression) e);
			else {
				assertTrue(e instanceof NeighbourhoodConstraint);
				subLeafSet.add(((NeighbourhoodConstraint)e).getTripleExpression());
			}
		}
		assertTrue(subLeafSet.contains(tci));
		assertTrue(subLeafSet.contains(tcj));
		assertTrue(subLeafSet.contains(tck));
		assertTrue(subLeafSet.contains(tcl));
		
		assertEquals(2, subOrSet.size());
		
		
		ShapeOrExpression subOr = null;
		for (ShapeOrExpression e : subOrSet) {
			if (e.getSubExpressions().size() != 2) {
				subOr = e;
			}
		}
		assertEquals(5, subOr.getSubExpressions().size());
		
		Set<ShapeAndExpression> subAndSet = new HashSet<>();
		subLeafSet = new HashSet<>();
		for (ShapeExpression e: subOr.getSubExpressions()) {
			if (e instanceof ShapeAndExpression) {
				subAndSet.add((ShapeAndExpression) e);
			} else {
				subLeafSet.add(((NeighbourhoodConstraint)e).getTripleExpression());
			}
		}
		assertTrue(subLeafSet.contains(tce));
		assertTrue(subLeafSet.contains(tcf));
		assertTrue(subLeafSet.contains(tcg));
		assertTrue(subLeafSet.contains(tch));
		
		assertEquals(1, subAndSet.size());
		
		ShapeAndExpression subAnd = subAndSet.iterator().next();
		assertEquals(4, subAnd.getSubExpressions().size());
		
		System.out.println(result);
	}
	
	@Test
	public void testNegatedShapeOrWithInnerNegatedShapeAnd() {
		ShapeExpression expr = not(shapeOr(tc("ex:a :: SL"), not(shapeAnd(tc("ex:b :: SL"), tc("ex:c :: SL")))));
		
		PushNegationsDownVisitor visitor = SchemaRulesStaticAnalysis.getInstance().new PushNegationsDownVisitor();
		expr.accept(visitor, false);
		ShapeExpression result = visitor.getResult();
		
		assertTrue(result instanceof ShapeAndExpression);
		List<ShapeExpression> subExpr = ((ShapeAndExpression) result).getSubExpressions();
		assertEquals(2, subExpr.size());
		ShapeExpression subExpr0 = subExpr.get(0);
		ShapeExpression subExpr1 = subExpr.get(1);
		assertTrue(subExpr0 instanceof ShapeNotExpression || subExpr1 instanceof ShapeNotExpression);
		assertTrue(subExpr0 instanceof ShapeAndExpression || subExpr1 instanceof ShapeAndExpression);
		System.out.println(result);
	}

	
}
