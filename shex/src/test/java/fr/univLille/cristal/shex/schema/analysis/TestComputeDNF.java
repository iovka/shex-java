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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import fr.univLille.cristal.shex.schema.abstrsynt.NeighbourhoodConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAndExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpression;
import fr.univLille.cristal.shex.schema.ShapeLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNotExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOrExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeRef;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpression;
import fr.univLille.cristal.shex.schema.analysis.SchemaRulesStaticAnalysis;
import fr.univLille.cristal.shex.schema.analysis.SchemaRulesStaticAnalysis.CreateDNFVisitor;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TestComputeDNF {


	@Test
	public void testNeighConstr() {
		TripleExpression tc = tc("ex:a :: SL");
		ShapeExpression expr = se(tc);
		
		ShapeOrExpression dnf = SchemaRulesStaticAnalysis.computeDNF(expr);
		assertEquals(1, dnf.getSubExpressions().size());
		assertEquals(tc, ((NeighbourhoodConstraint) dnf.getSubExpressions().get(0)).getTripleExpression());
		
	}

	@Test
	public void testNegatedNeighConstr() {
		ShapeExpression expr = not(se(tc("ex:a :: SL")));
		
		CreateDNFVisitor visitor = SchemaRulesStaticAnalysis.getInstance().new CreateDNFVisitor();
		
		expr.accept(visitor, new Object());
		ShapeExpression result = visitor.getResult();
		assertTrue(result instanceof ShapeNotExpression);
	}
		

	
	@Test
	public void testShapeAnd(){
		TripleExpression tca = tc("ex:a :: SL"), 
				tcb = tc("ex:b :: SL"),
				tcc = tc("ex:c :: SL");
		
		ShapeExpression expr = shapeAnd(tca, tcb, tcc); 
		
		ShapeOrExpression dnf = SchemaRulesStaticAnalysis.computeDNF(expr);
		assertEquals(1, dnf.getSubExpressions().size());
		ShapeAndExpression conjunct = (ShapeAndExpression) dnf.getSubExpressions().get(0);
				
		List<TripleExpression> expected = new ArrayList<>();	
		
		for (ShapeExpression e : conjunct.getSubExpressions()) {
			expected.add(((NeighbourhoodConstraint)e).getTripleExpression());
		}
		assertEquals(3, expected.size());
		assertTrue(expected.contains(tca));
		assertTrue(expected.contains(tcb));
		assertTrue(expected.contains(tcc));
	}
	
	
	@Test
	public void testNegatedShapeOrWithInnerNegatedShapeAnd() {
		TripleExpression tca = tc("ex:a :: SL"), 
				tcb = tc("ex:b :: SL"),
				tcc = tc("ex:c :: SL");
		ShapeExpression expr = not(shapeOr(tca, not(shapeAnd(tcb, tcc))));
		
		ShapeOrExpression dnf = SchemaRulesStaticAnalysis.computeDNF(expr);
		assertEquals(1, dnf.getSubExpressions().size());
		ShapeExpression conjunct = dnf.getSubExpressions().get(0);
		
		assertTrue(conjunct instanceof ShapeAndExpression);
		List<ShapeExpression> subExpr = ((ShapeAndExpression) conjunct).getSubExpressions();
		assertEquals(3, subExpr.size());
		for (ShapeExpression e : subExpr) {
			if (e instanceof ShapeNotExpression) {
				ShapeNotExpression enot = (ShapeNotExpression)e;
				assertEquals(tca, ((NeighbourhoodConstraint)(enot.getSubExpression())).getTripleExpression());
			} else {
				TripleExpression te =  ((NeighbourhoodConstraint) e).getTripleExpression();
				assertTrue(tcb.equals(te) || tcc.equals(te));
			}
		}
		
		System.out.println(conjunct);
		
	}
	
	@Test
	public void testWithShapeRef() {
		TripleExpression tca = tc("ex:a :: SL"), 
				tcb = tc("ex:b :: SL"),
				tcc = tc("ex:c :: SL");
		ShapeRef ref = new ShapeRef(new ShapeLabel("SL2"));
		
		ShapeExpression expr = shapeAnd(tca, tcb, tcc, ref); 
		
		ShapeOrExpression dnf = SchemaRulesStaticAnalysis.computeDNF(expr);
		assertEquals(1, dnf.getSubExpressions().size());
		ShapeAndExpression conjunct = (ShapeAndExpression) dnf.getSubExpressions().get(0);
				
		List<Object> listAtoms= new ArrayList<>();	
		
		for (ShapeExpression e : conjunct.getSubExpressions()) {
			if (e instanceof NeighbourhoodConstraint)
				listAtoms.add(((NeighbourhoodConstraint)e).getTripleExpression());
			else 
				listAtoms.add(e);
		}
		assertEquals(4, listAtoms.size());
		assertTrue(listAtoms.contains(tca));
		assertTrue(listAtoms.contains(tcb));
		assertTrue(listAtoms.contains(tcc));
		assertTrue(listAtoms.contains(ref));
	}
	
	
	@Test
	public void testAlternateAndOr() {
		Map<String, TripleExpression> tc = new HashMap<>();
		for (int i = 'a'; i <= 'p'; i++) {
			String key = "" + (char) i;
			String prop = "ex:" + (char) i;
			TripleExpression t = tc(prop + " :: SL");
			tc.put(key, t);
		}
		
		ShapeExpression orab = shapeOr(tc.get("a"), tc.get("b"));
		ShapeExpression orcd = shapeOr(tc.get("c"), tc.get("d"));
		ShapeExpression oref = shapeOr(tc.get("e"), tc.get("f"));
		ShapeExpression orgh = shapeOr(tc.get("g"), tc.get("h"));
		ShapeExpression orij = shapeOr(tc.get("i"), tc.get("j"));
		ShapeExpression orkl = shapeOr(tc.get("k"), tc.get("l"));
		ShapeExpression ormn = shapeOr(tc.get("m"), tc.get("n"));
		ShapeExpression orop = shapeOr(tc.get("o"), tc.get("p"));
		
		ShapeExpression andabcd = shapeAnd(orab, orcd);
		ShapeExpression andefgh = shapeAnd(oref, orgh);
		ShapeExpression andijkl = shapeAnd(orij, orkl);
		ShapeExpression andmnop = shapeAnd(ormn, orop);
		
		ShapeExpression or1 = shapeOr(andabcd, andefgh);
		ShapeExpression or2 = shapeOr(andijkl, andmnop);
		
		ShapeExpression expr = shapeAnd(or1, or2);
		
		ShapeOrExpression dnf = SchemaRulesStaticAnalysis.computeDNF(expr);
		
		assertEquals(64, dnf.getSubExpressions().size());
		
		Set<Set<TripleExpression>> theConjuncts = new HashSet<>();
		for (ShapeExpression e : dnf.getSubExpressions()) {
			ShapeAndExpression eand = (ShapeAndExpression) e;
			Set<TripleExpression> conjunct = eand.getSubExpressions()
					.stream()
					.map(subexpr -> ((NeighbourhoodConstraint)subexpr).getTripleExpression()).collect(Collectors.toSet());
			theConjuncts.add(conjunct);
		}

		// Described by a cartesian product
		Set<Set<TripleExpression>> expectedConjuncts = new HashSet<>();
		for (String left : new String[]{"ac", "ad", "bc", "bd", "eg", "eh", "fg", "fh"}) {
			for (String right : new String[]{"ik", "il", "jk", "jl", "mo", "mp", "no", "np"}) {
				String s = left+right;
				Set<TripleExpression> conjunct = new HashSet<>();
				for (int i = 0; i < s.length(); i++) {
					conjunct.add(tc.get(""+s.charAt(i)));
				}
				expectedConjuncts.add(conjunct);
			}
		}
		
		assertEquals(expectedConjuncts, theConjuncts);
		
	}
	
	
}
