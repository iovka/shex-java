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

import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAnd;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNot;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOr;
import fr.univLille.cristal.shex.schema.abstrsynt.NonRefTripleExpr;
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
		ShapeExpr expr = se(tc("ex:a :: SL"));
		FlattenAndOr visitor = SchemaRulesStaticAnalysis.getInstance().new FlattenAndOr();
		
		expr.accept(visitor, new Object());
		ShapeExpr result = visitor.getResult();
		assertTrue(result instanceof Shape);
	}

	@Test
	public void testNegatedNeighConstr() {
		ShapeExpr expr = not(se(tc("ex:a :: SL")));
		FlattenAndOr visitor = SchemaRulesStaticAnalysis.getInstance().new FlattenAndOr();
		
		expr.accept(visitor, new Object());
		ShapeExpr result = visitor.getResult();
		assertTrue(result instanceof ShapeNot);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDoublyNegatedNeighConstr() {
		ShapeExpr expr = not(not(se(tc("ex:a :: SL"))));
		
		FlattenAndOr visitor = SchemaRulesStaticAnalysis.getInstance().new FlattenAndOr();
		expr.accept(visitor, new Object());
	}
	
	@Test
	public void testShapeOr() {
		ShapeExpr expr = shapeOr(tc("ex:a :: SL"), tc("ex:b :: SL"));
		
		FlattenAndOr visitor = SchemaRulesStaticAnalysis.getInstance().new FlattenAndOr();
		expr.accept(visitor, new Object());
		ShapeExpr result = visitor.getResult();
		
		assertTrue(result instanceof ShapeOr);
		System.out.println(result);
	}
	
	@Test
	public void testShapeOrOr() {
		NonRefTripleExpr tc1 = tc("ex:a :: SL");
		NonRefTripleExpr tc2 = tc("ex:b :: SL");
		NonRefTripleExpr tc3 = tc("ex:c :: SL");
		
		ShapeExpr expr = shapeOr(shapeOr(tc1, tc2), tc3);
		
		FlattenAndOr visitor = SchemaRulesStaticAnalysis.getInstance().new FlattenAndOr();
		expr.accept(visitor, new Object());
		ShapeExpr result = visitor.getResult();
		
		assertTrue(result instanceof ShapeOr);
	
		List<ShapeExpr> subExpr = ((ShapeOr) result).getSubExpressions();
		assertEquals(3, subExpr.size());
		
		List<NonRefTripleExpr> subExprList = new ArrayList<>(); 
		for (ShapeExpr e : subExpr) {
			assertTrue(e instanceof Shape);
			subExprList.add(((Shape)e).getTripleExpression());
		}
		assertTrue(subExprList.contains(tc1));
		assertTrue(subExprList.contains(tc2));
		assertTrue(subExprList.contains(tc3));
		System.out.println(result);
	}
	
	@Test
	public void testShapeSingletonOrOr() {
		ShapeExpr expr = shapeOr(shapeOr(tc("ex:a :: SL"), tc("ex:b :: SL"), tc("ex:c :: SL")));
		System.out.println(expr);
		
		FlattenAndOr visitor = SchemaRulesStaticAnalysis.getInstance().new FlattenAndOr();
		expr.accept(visitor, new Object());
		ShapeExpr result = visitor.getResult();
		
		assertTrue(result instanceof ShapeOr);
	
		List<ShapeExpr> subExpr = ((ShapeOr) result).getSubExpressions();
		assertEquals(3, subExpr.size());
		for (ShapeExpr e : subExpr) {
			assertTrue(e instanceof Shape);
		}
		
		System.out.println(result);
	}
	
	@Test
	public void testShapeAndAnd() {
		ShapeExpr expr = shapeAnd(shapeAnd(tc("ex:a :: SL"), tc("ex:b :: SL")), shapeAnd(tc("ex:c :: SL"), tc("ex:d :: SL")));
		System.out.println(expr);
		
		FlattenAndOr visitor = SchemaRulesStaticAnalysis.getInstance().new FlattenAndOr();
		expr.accept(visitor, new Object());
		ShapeExpr result = visitor.getResult();
		
		assertTrue(result instanceof ShapeAnd);
	
		List<ShapeExpr> subExpr = ((ShapeAnd) result).getSubExpressions();
		assertEquals(4, subExpr.size());
		for (ShapeExpr e : subExpr) {
			assertTrue(e instanceof Shape);
		}
		
		System.out.println(result);
	}
	
	
	@Test
	public void testMixedAndOr() {
		NonRefTripleExpr tca = tc("ex:a :: SL"), 
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
		ShapeExpr and1 = shapeAnd(tca, tcb);
		ShapeExpr and2 = shapeAnd(tcc, tcd);
		ShapeExpr and3 = shapeAnd(and1, and2);

		ShapeExpr or1 = shapeOr(tce, tcf);
		ShapeExpr or2 = shapeOr(tcg, tch);
		ShapeExpr or3 = shapeOr(and3, or1, or2);
		
		ShapeExpr and4 = shapeAnd(tci, tcj);
		ShapeExpr and5 = shapeAnd(tck, tcl);
		ShapeExpr or4 = shapeOr(tcm, tcn);
		ShapeExpr expr = shapeAnd(or3, and4, and5, or4);
		
		System.out.println(expr);
		
		FlattenAndOr visitor = SchemaRulesStaticAnalysis.getInstance().new FlattenAndOr();
		expr.accept(visitor, new Object());
		ShapeExpr result = visitor.getResult();
		
		assertTrue(result instanceof ShapeAnd);
		List<ShapeExpr> subExpr = ((ShapeAnd) result).getSubExpressions();
		assertEquals(6, subExpr.size());
	
		Set<ShapeOr> subOrSet = new HashSet<>();
		Set<NonRefTripleExpr> subLeafSet = new HashSet<>();
		for (ShapeExpr e : subExpr) {
			if (e instanceof ShapeOr)
				subOrSet.add((ShapeOr) e);
			else {
				assertTrue(e instanceof Shape);
				subLeafSet.add(((Shape)e).getTripleExpression());
			}
		}
		assertTrue(subLeafSet.contains(tci));
		assertTrue(subLeafSet.contains(tcj));
		assertTrue(subLeafSet.contains(tck));
		assertTrue(subLeafSet.contains(tcl));
		
		assertEquals(2, subOrSet.size());
		
		
		ShapeOr subOr = null;
		for (ShapeOr e : subOrSet) {
			if (e.getSubExpressions().size() != 2) {
				subOr = e;
			}
		}
		assertEquals(5, subOr.getSubExpressions().size());
		
		Set<ShapeAnd> subAndSet = new HashSet<>();
		subLeafSet = new HashSet<>();
		for (ShapeExpr e: subOr.getSubExpressions()) {
			if (e instanceof ShapeAnd) {
				subAndSet.add((ShapeAnd) e);
			} else {
				subLeafSet.add(((Shape)e).getTripleExpression());
			}
		}
		assertTrue(subLeafSet.contains(tce));
		assertTrue(subLeafSet.contains(tcf));
		assertTrue(subLeafSet.contains(tcg));
		assertTrue(subLeafSet.contains(tch));
		
		assertEquals(1, subAndSet.size());
		
		ShapeAnd subAnd = subAndSet.iterator().next();
		assertEquals(4, subAnd.getSubExpressions().size());
		
		System.out.println(result);
	}
	
	@Test
	public void testNegatedShapeOrWithInnerNegatedShapeAnd() {
		ShapeExpr expr = not(shapeOr(tc("ex:a :: SL"), not(shapeAnd(tc("ex:b :: SL"), tc("ex:c :: SL")))));
		
		PushNegationsDownVisitor visitor = SchemaRulesStaticAnalysis.getInstance().new PushNegationsDownVisitor();
		expr.accept(visitor, false);
		ShapeExpr result = visitor.getResult();
		
		assertTrue(result instanceof ShapeAnd);
		List<ShapeExpr> subExpr = ((ShapeAnd) result).getSubExpressions();
		assertEquals(2, subExpr.size());
		ShapeExpr subExpr0 = subExpr.get(0);
		ShapeExpr subExpr1 = subExpr.get(1);
		assertTrue(subExpr0 instanceof ShapeNot || subExpr1 instanceof ShapeNot);
		assertTrue(subExpr0 instanceof ShapeAnd || subExpr1 instanceof ShapeAnd);
		System.out.println(result);
	}

	
}
