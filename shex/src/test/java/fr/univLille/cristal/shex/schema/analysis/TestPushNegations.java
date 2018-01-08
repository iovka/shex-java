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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAnd;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNot;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOr;
import fr.univLille.cristal.shex.schema.analysis.SchemaRulesStaticAnalysis;
import fr.univLille.cristal.shex.schema.analysis.SchemaRulesStaticAnalysis.PushNegationsDownVisitor;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TestPushNegations {
	
	@Test
	public void testNonNegatedNeighConstr() {
		ShapeExpr expr = se(tc("a :: SL"));
		PushNegationsDownVisitor visitor = SchemaRulesStaticAnalysis.getInstance().new PushNegationsDownVisitor();
		
		expr.accept(visitor, false);
		ShapeExpr result = visitor.getResult();
		assertTrue(result instanceof Shape);
	}

	@Test
	public void testNegatedNeighConstr() {
		ShapeExpr expr = not(se(tc("a :: SL")));
		PushNegationsDownVisitor visitor = SchemaRulesStaticAnalysis.getInstance().new PushNegationsDownVisitor();
		
		expr.accept(visitor, false);
		ShapeExpr result = visitor.getResult();
		assertTrue(result instanceof ShapeNot);
	}
	
	@Test
	public void testDoublyNegatedNeighConstr() {
		ShapeExpr expr = not(not(se(tc("a :: SL"))));
		
		PushNegationsDownVisitor visitor = SchemaRulesStaticAnalysis.getInstance().new PushNegationsDownVisitor();
		expr.accept(visitor, false);
		ShapeExpr result = visitor.getResult();
		
		assertTrue(result instanceof Shape);
		System.out.println(result);
	}
	
	@Test
	public void testShapeOr() {
		ShapeExpr expr = shapeOr(tc("a :: SL"), tc("b :: SL"));
		
		PushNegationsDownVisitor visitor = SchemaRulesStaticAnalysis.getInstance().new PushNegationsDownVisitor();
		expr.accept(visitor, false);
		ShapeExpr result = visitor.getResult();
		
		assertTrue(result instanceof ShapeOr);
		System.out.println(result);
	}
	
	@Test
	public void testNegatedShapeOr() {
		ShapeExpr expr = not(shapeOr(tc("a :: SL"), tc("b :: SL")));
		
		PushNegationsDownVisitor visitor = SchemaRulesStaticAnalysis.getInstance().new PushNegationsDownVisitor();
		expr.accept(visitor, false);
		ShapeExpr result = visitor.getResult();
		
		assertTrue(result instanceof ShapeAnd);
		System.out.println(result);
	}
	
	@Test
	public void testNegatedShapeOrWithInnerShapeAnd() {
		ShapeExpr expr = not(shapeOr(tc("a :: SL"), shapeAnd(tc("b :: SL"), tc("c :: SL"))));
		
		PushNegationsDownVisitor visitor = SchemaRulesStaticAnalysis.getInstance().new PushNegationsDownVisitor();
		expr.accept(visitor, false);
		ShapeExpr result = visitor.getResult();
		
		assertTrue(result instanceof ShapeAnd);
		List<ShapeExpr> subExpr = ((ShapeAnd) result).getSubExpressions();
		assertEquals(2, subExpr.size());
		ShapeExpr subExpr0 = subExpr.get(0);
		ShapeExpr subExpr1 = subExpr.get(1);
		assertTrue(subExpr0 instanceof ShapeNot || subExpr1 instanceof ShapeNot);
		assertTrue(subExpr0 instanceof ShapeOr || subExpr1 instanceof ShapeOr);
		System.out.println(result);
	}
	
	@Test
	public void testNegatedShapeOrWithInnerNegatedShapeAnd() {
		ShapeExpr expr = not(shapeOr(tc("a :: SL"), not(shapeAnd(tc("b :: SL"), tc("c :: SL")))));
		
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
