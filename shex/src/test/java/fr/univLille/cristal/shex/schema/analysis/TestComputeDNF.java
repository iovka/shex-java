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

import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.junit.Test;

import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAnd;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNot;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.NonRefTripleExpr;
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
		NonRefTripleExpr tc = tc("ex:a :: SL");
		ShapeExpr expr = se(tc);
		
		ShapeOr dnf = SchemaRulesStaticAnalysis.computeDNF(expr);
		assertEquals(1, dnf.getSubExpressions().size());
		assertEquals(tc, ((Shape) dnf.getSubExpressions().get(0)).getTripleExpression());
		
	}

	@Test
	public void testNegatedNeighConstr() {
		ShapeExpr expr = not(se(tc("ex:a :: SL")));
		
		CreateDNFVisitor visitor = SchemaRulesStaticAnalysis.getInstance().new CreateDNFVisitor();
		
		expr.accept(visitor, new Object());
		ShapeExpr result = visitor.getResult();
		assertTrue(result instanceof ShapeNot);
	}
		

	
	@Test
	public void testShapeAnd(){
		NonRefTripleExpr tca = tc("ex:a :: SL"), 
				tcb = tc("ex:b :: SL"),
				tcc = tc("ex:c :: SL");
		
		ShapeExpr expr = shapeAnd(tca, tcb, tcc); 
		
		ShapeOr dnf = SchemaRulesStaticAnalysis.computeDNF(expr);
		assertEquals(1, dnf.getSubExpressions().size());
		ShapeAnd conjunct = (ShapeAnd) dnf.getSubExpressions().get(0);
				
		List<NonRefTripleExpr> expected = new ArrayList<>();	
		
		for (ShapeExpr e : conjunct.getSubExpressions()) {
			expected.add(((Shape)e).getTripleExpression());
		}
		assertEquals(3, expected.size());
		assertTrue(expected.contains(tca));
		assertTrue(expected.contains(tcb));
		assertTrue(expected.contains(tcc));
	}
	
	
	@Test
	public void testNegatedShapeOrWithInnerNegatedShapeAnd() {
		NonRefTripleExpr tca = tc("ex:a :: SL"), 
				tcb = tc("ex:b :: SL"),
				tcc = tc("ex:c :: SL");
		ShapeExpr expr = not(shapeOr(tca, not(shapeAnd(tcb, tcc))));
		
		ShapeOr dnf = SchemaRulesStaticAnalysis.computeDNF(expr);
		assertEquals(1, dnf.getSubExpressions().size());
		ShapeExpr conjunct = dnf.getSubExpressions().get(0);
		
		assertTrue(conjunct instanceof ShapeAnd);
		List<ShapeExpr> subExpr = ((ShapeAnd) conjunct).getSubExpressions();
		assertEquals(3, subExpr.size());
		for (ShapeExpr e : subExpr) {
			if (e instanceof ShapeNot) {
				ShapeNot enot = (ShapeNot)e;
				assertEquals(tca, ((Shape)(enot.getSubExpression())).getTripleExpression());
			} else {
				NonRefTripleExpr te =  ((Shape) e).getTripleExpression();
				assertTrue(tcb.equals(te) || tcc.equals(te));
			}
		}
		
		System.out.println(conjunct);
		
	}
	
	@Test
	public void testWithShapeRef() {
		NonRefTripleExpr tca = tc("ex:a :: SL"), 
				tcb = tc("ex:b :: SL"),
				tcc = tc("ex:c :: SL");
		ShapeExprRef ref = new ShapeExprRef(newShapeLabel("SL2"));
		
		ShapeExpr expr = shapeAnd(tca, tcb, tcc, ref); 
		
		ShapeOr dnf = SchemaRulesStaticAnalysis.computeDNF(expr);
		assertEquals(1, dnf.getSubExpressions().size());
		ShapeAnd conjunct = (ShapeAnd) dnf.getSubExpressions().get(0);
				
		List<Object> listAtoms= new ArrayList<>();	
		
		for (ShapeExpr e : conjunct.getSubExpressions()) {
			if (e instanceof Shape)
				listAtoms.add(((Shape)e).getTripleExpression());
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
		Map<String, NonRefTripleExpr> tc = new HashMap<>();
		for (int i = 'a'; i <= 'p'; i++) {
			String key = "" + (char) i;
			String prop = "ex:" + (char) i;
			NonRefTripleExpr t = tc(prop + " :: SL");
			tc.put(key, t);
		}
		
		ShapeExpr orab = shapeOr(tc.get("a"), tc.get("b"));
		ShapeExpr orcd = shapeOr(tc.get("c"), tc.get("d"));
		ShapeExpr oref = shapeOr(tc.get("e"), tc.get("f"));
		ShapeExpr orgh = shapeOr(tc.get("g"), tc.get("h"));
		ShapeExpr orij = shapeOr(tc.get("i"), tc.get("j"));
		ShapeExpr orkl = shapeOr(tc.get("k"), tc.get("l"));
		ShapeExpr ormn = shapeOr(tc.get("m"), tc.get("n"));
		ShapeExpr orop = shapeOr(tc.get("o"), tc.get("p"));
		
		ShapeExpr andabcd = shapeAnd(orab, orcd);
		ShapeExpr andefgh = shapeAnd(oref, orgh);
		ShapeExpr andijkl = shapeAnd(orij, orkl);
		ShapeExpr andmnop = shapeAnd(ormn, orop);
		
		ShapeExpr or1 = shapeOr(andabcd, andefgh);
		ShapeExpr or2 = shapeOr(andijkl, andmnop);
		
		ShapeExpr expr = shapeAnd(or1, or2);
		
		ShapeOr dnf = SchemaRulesStaticAnalysis.computeDNF(expr);
		
		assertEquals(64, dnf.getSubExpressions().size());
		
		Set<Set<NonRefTripleExpr>> theConjuncts = new HashSet<>();
		for (ShapeExpr e : dnf.getSubExpressions()) {
			ShapeAnd eand = (ShapeAnd) e;
			Set<NonRefTripleExpr> conjunct = eand.getSubExpressions()
					.stream()
					.map(subexpr -> ((Shape)subexpr).getTripleExpression()).collect(Collectors.toSet());
			theConjuncts.add(conjunct);
		}

		// Described by a cartesian product
		Set<Set<NonRefTripleExpr>> expectedConjuncts = new HashSet<>();
		for (String left : new String[]{"ac", "ad", "bc", "bd", "eg", "eh", "fg", "fh"}) {
			for (String right : new String[]{"ik", "il", "jk", "jl", "mo", "mp", "no", "np"}) {
				String s = left+right;
				Set<NonRefTripleExpr> conjunct = new HashSet<>();
				for (int i = 0; i < s.length(); i++) {
					conjunct.add(tc.get(""+s.charAt(i)));
				}
				expectedConjuncts.add(conjunct);
			}
		}
		
		assertEquals(expectedConjuncts, theConjuncts);
		
	}
	

	public final static String PREFIX = "http://a.ex#";
	public static ShapeExprLabel newShapeLabel (String label){
		return new ShapeExprLabel(SimpleValueFactory.getInstance().createIRI(PREFIX + label));
	}
}
