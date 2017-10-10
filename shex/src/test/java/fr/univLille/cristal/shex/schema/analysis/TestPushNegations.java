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

import fr.univLille.cristal.shex.schema.abstrsynt.NeighbourhoodConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAndExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNotExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOrExpression;
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
		ShapeExpression expr = se(tc("a :: SL"));
		PushNegationsDownVisitor visitor = SchemaRulesStaticAnalysis.getInstance().new PushNegationsDownVisitor();
		
		expr.accept(visitor, false);
		ShapeExpression result = visitor.getResult();
		assertTrue(result instanceof NeighbourhoodConstraint);
	}

	@Test
	public void testNegatedNeighConstr() {
		ShapeExpression expr = not(se(tc("a :: SL")));
		PushNegationsDownVisitor visitor = SchemaRulesStaticAnalysis.getInstance().new PushNegationsDownVisitor();
		
		expr.accept(visitor, false);
		ShapeExpression result = visitor.getResult();
		assertTrue(result instanceof ShapeNotExpression);
	}
	
	@Test
	public void testDoublyNegatedNeighConstr() {
		ShapeExpression expr = not(not(se(tc("a :: SL"))));
		
		PushNegationsDownVisitor visitor = SchemaRulesStaticAnalysis.getInstance().new PushNegationsDownVisitor();
		expr.accept(visitor, false);
		ShapeExpression result = visitor.getResult();
		
		assertTrue(result instanceof NeighbourhoodConstraint);
		System.out.println(result);
	}
	
	@Test
	public void testShapeOr() {
		ShapeExpression expr = shapeOr(tc("a :: SL"), tc("b :: SL"));
		
		PushNegationsDownVisitor visitor = SchemaRulesStaticAnalysis.getInstance().new PushNegationsDownVisitor();
		expr.accept(visitor, false);
		ShapeExpression result = visitor.getResult();
		
		assertTrue(result instanceof ShapeOrExpression);
		System.out.println(result);
	}
	
	@Test
	public void testNegatedShapeOr() {
		ShapeExpression expr = not(shapeOr(tc("a :: SL"), tc("b :: SL")));
		
		PushNegationsDownVisitor visitor = SchemaRulesStaticAnalysis.getInstance().new PushNegationsDownVisitor();
		expr.accept(visitor, false);
		ShapeExpression result = visitor.getResult();
		
		assertTrue(result instanceof ShapeAndExpression);
		System.out.println(result);
	}
	
	@Test
	public void testNegatedShapeOrWithInnerShapeAnd() {
		ShapeExpression expr = not(shapeOr(tc("a :: SL"), shapeAnd(tc("b :: SL"), tc("c :: SL"))));
		
		PushNegationsDownVisitor visitor = SchemaRulesStaticAnalysis.getInstance().new PushNegationsDownVisitor();
		expr.accept(visitor, false);
		ShapeExpression result = visitor.getResult();
		
		assertTrue(result instanceof ShapeAndExpression);
		List<ShapeExpression> subExpr = ((ShapeAndExpression) result).getSubExpressions();
		assertEquals(2, subExpr.size());
		ShapeExpression subExpr0 = subExpr.get(0);
		ShapeExpression subExpr1 = subExpr.get(1);
		assertTrue(subExpr0 instanceof ShapeNotExpression || subExpr1 instanceof ShapeNotExpression);
		assertTrue(subExpr0 instanceof ShapeOrExpression || subExpr1 instanceof ShapeOrExpression);
		System.out.println(result);
	}
	
	@Test
	public void testNegatedShapeOrWithInnerNegatedShapeAnd() {
		ShapeExpression expr = not(shapeOr(tc("a :: SL"), not(shapeAnd(tc("b :: SL"), tc("c :: SL")))));
		
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
