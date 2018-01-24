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

import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.eachof;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.not;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.se;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.shapeAnd;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.shapeOr;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.someof;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.tc;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Test;

import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.abstrsynt.EmptyTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor;
import fr.univLille.cristal.shex.schema.abstrsynt.NonRefTripleExpr;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TestCheckStratifiedNegation {
	
	@Test
	public void testSchemaWithOneShapeExpression() {
		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.addSlallRule();
		
		List<Set<ShapeExprLabel>> stratification = SchemaRulesStaticAnalysis.computeStratification(constr.getSchema());
		
		// Check that the stratification is correct
		assertEquals(1, stratification.size());
		assertEquals(1, stratification.get(0).size());
	}
	
	@Test
	public void testPositiveSelfLoopDependency() {
		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.addRule("SL", se(tc("p :: SL")));
	
		List<Set<ShapeExprLabel>> stratification = SchemaRulesStaticAnalysis.computeStratification(constr.getSchema());
		
		// Check that the stratification is correct
		assertEquals(1, stratification.size());
		assertEquals(1, stratification.get(0).size());
	}
	
	@Test
	public void testPositiveLoopLength2Dependency() {
		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.addRule("SL1", se(tc("p :: SL2")));
		constr.addRule("SL2", se(tc("q :: SL1")));

		List<Set<ShapeExprLabel>> stratification = SchemaRulesStaticAnalysis.computeStratification(constr.getSchema());
		
		// Check that the stratification is correct
		assertEquals(1, stratification.size());
		assertEquals(2, stratification.get(0).size());
	}

	@Test
	public void testPositiveLoopLength5Dependency() {
		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		for (int i = 1; i <= 4; i++) {
			constr.addRule("SL"+i, se(tc(""+((char)('o'+i))+ ":: SL"+(i+1))));
		}
		constr.addRule("SL5", se(tc("t :: SL1")));
		
		List<Set<ShapeExprLabel>> stratification = SchemaRulesStaticAnalysis.computeStratification(constr.getSchema());

		
		// Check that the stratification is correct
		assertEquals(1, stratification.size());
		assertEquals(5, stratification.get(0).size());
	}
	
	@Test
	public void testTreeStructuredSchema10rules() {
		// SL1 -> SL2, !SL3, SL4
		// SL3 -> SL5
		// SL4 -> SL6, !SL7
		// SL6 -> SL8, !SL9, SL10
		
		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		
		NonRefTripleExpr 
			te1 = tc("a :: SL1"),
			te2 = tc("a :: SL2"),
			te3 = tc("a :: SL3"),
			te4 = tc("a :: SL4"),
			te5 = tc("a :: SL5"),
			te6 = tc("a :: SL6"),
			te7 = tc("a :: SL7"),
			te8 = tc("a :: SL8"),
			te9 = tc("a :: SL9"),
			te10= tc("a :: SL10"),
			tempty = new EmptyTripleExpression();
		
		constr.addRule("SL1", shapeAnd(someof(te2, te4), not(te3)));
		constr.addRule("SL3", se(te5));
		constr.addRule("SL4", shapeOr(te6, not(te7)));
		constr.addRule("SL6", shapeAnd(eachof(te8, te10), not(te9)));
		
		constr.addRule("SL2", se(tempty));
		constr.addRule("SL5", se(tempty));
		constr.addRule("SL7", se(tempty));
		constr.addRule("SL8", se(tempty));
		constr.addRule("SL9", se(tempty));
		constr.addRule("SL10", se(tempty));

		List<Set<ShapeExprLabel>> stratification = SchemaRulesStaticAnalysis.computeStratification(constr.getSchema());

		// Check that the stratification is correct
		assertEquals(10, stratification.size());
		for (int i = 0; i < 10; i++) {
			assertEquals(1, stratification.get(i).size());
		}
		
		Set<ShapeExprLabel> allLabelsInStratification = new HashSet<>();
		for (int i = 0; i < 10; i++) {
			allLabelsInStratification.addAll(stratification.get(i));
		}
		assertEquals(10, allLabelsInStratification.size());
	}

	@Test
	public void testPositiveDependecyOnlyWithComplexValueExpressions() {
		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();

		NonRefTripleExpr 
			te1_1 = tc("a :: SL1"), te1_2 = tc("a :: SL1"), te1_3 = tc("a :: SL1"),
			te2_1 = tc("a :: SL2"),	te2_2 = tc("a :: SL2"), te2_3 = tc("a :: SL2"), te2_4 = tc("a :: SL2"),
			te3_1 = tc("a :: SL3"),	te3_2 = tc("a :: SL3"),	te3_3 = tc("a :: SL3"),	te3_4 = tc("a :: SL3"),
			te4_1 = tc("a :: SL4"), te4_2 = tc("a :: SL4"),	te4_3 = tc("a :: SL4");
		
		
		// TE1 -> TE2, TE3, TE4
		// TE2 -> TE1, TE3, TE2
		// TE3 -> TE1, TE2, TE3, TE4
		// TE4 -> TE1, TE2, TE3, TE4
		
		constr.addRule("SL1", shapeAnd(te2_1, te3_1, te4_1));
		constr.addRule("SL2", shapeOr(eachof(te1_1, te3_2), te2_2));
		constr.addRule("SL3", shapeAnd(eachof(te1_2, te2_3), someof(te3_3, te4_2)));
		constr.addRule("SL4", shapeAnd(eachof(te1_3, te2_4, te3_4), te4_3));
		
		List<Set<ShapeExprLabel>> stratification = SchemaRulesStaticAnalysis.computeStratification(constr.getSchema());
		
		// Check that the stratification is correct
		assertEquals(1, stratification.size());
		assertEquals(4, stratification.get(0).size());
	}

	@Test
	public void testPositiveAndNegativeDependeciesWithComplexShapeExpressions() {
		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		
		NonRefTripleExpr 
		te1_1 = tc("a :: SL1"), te1_2 = tc("a :: SL1"), te1_3 = tc("a :: SL1"),
		te2_1 = tc("a :: SL2"),	te2_2 = tc("a :: SL2"), te2_3 = tc("a :: SL2"), te2_4 = tc("a :: SL2"),
		te3_1 = tc("a :: SL3"),	te3_2 = tc("a :: SL3"),	te3_3 = tc("a :: SL3"),	te3_4 = tc("a :: SL3"),
		te4_1 = tc("a :: SL4"), te4_2 = tc("a :: SL4"),	te4_3 = tc("a :: SL4"), te4_4 = tc("a :: SL4"),
		te5 = tc("a :: SL5");
		
		// SL1 -> SL2, SL3, SL4
		// SL2 -> SL3, SL2
		// SL3 -> !SL4, SL2, SL3, SL4
		// SL4 -> SL5
		// SL5 -> SL4
		
		constr.addRule("SL1", shapeAnd(te2_1, te3_1, te4_1));
		constr.addRule("SL2", se(eachof(te3_2, te2_2)));
		constr.addRule("SL3", shapeAnd(not(te4_3), someof(te2_3, te3_3, te4_2)));
		constr.addRule("SL4", se(te5));
		constr.addRule("SL5", se(te4_4));
		
		ShexSchema rules = constr.getSchema(); 
		DefaultDirectedWeightedGraph<ShapeExprLabel, DefaultWeightedEdge> depGraph = SchemaRulesStaticAnalysis.computeDependencyGraph(rules);
		System.out.println(SchemaRulesStaticAnalysis.depGraphToString(depGraph));
		List<Set<ShapeExprLabel>> stratification = SchemaRulesStaticAnalysis.computeStratification(constr.getSchema());
		
		// Check that the stratification is correct
		assertEquals(3, stratification.size());
	
		Set<ShapeExprLabel> str0 = new HashSet<>();
		str0.add(newShapeLabel("SL4"));
		str0.add(newShapeLabel("SL5"));
		
		Set<ShapeExprLabel> str1 = new HashSet<>();
		str1.add(newShapeLabel("SL2"));
		str1.add(newShapeLabel("SL3"));
		
		Set<ShapeExprLabel> str2 = new HashSet<>();
		str2.add(newShapeLabel("SL1"));

		assertEquals(str0, new HashSet<>(stratification.get(0)));
		assertEquals(str1, stratification.get(1));
		assertEquals(str2, stratification.get(2));
	}
	
	
	
	
	@Test
	public void testNegativeSelfLoopDependency() {
		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		
		// SL -> !SL
		constr.addRule("SL", not(se(tc("p :: SL"))));
		
		assertNull(SchemaRulesStaticAnalysis.computeStratification(constr.getSchema()));
	}

	@Test
	public void testLoopLength2WithOneNegationDependency() {
		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		
		// SL1 -> SL2
		// SL2 -> !SL1
		
		constr.addRule("SL1", se(tc("p :: SL2")));
		constr.addRule("SL2", not(se(tc("q :: SL1"))));
		
		assertNull(SchemaRulesStaticAnalysis.computeStratification(constr.getSchema()));

	}

	
	@Test
	public void testPositiveAndNegativeDependeciesWithComplexValueExpressionsAndNegativeDependencyLoop() {
		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		
		// SL1 -> SL2, SL3, SL4
		// SL2 -> SL3, SL2
		// SL3 -> !SL4, SL2, SL3, SL4
		// SL4 -> SL5
		// SL5 -> SL4, !SL2
		
		constr.addRule("SL1", shapeAnd(tc("p :: SL2"), se(someof("q :: SL3 | p :: SL4"))));
		constr.addRule("SL2", shapeOr(tc("q :: SL3"), tc("p :: SL2")));
		constr.addRule("SL3", shapeOr(not(se(tc("p :: SL4"))), tc("p :: SL2"), se(eachof("q :: SL3 ; p :: SL4"))));
		constr.addRule("SL4", se(tc("q :: SL5")));
		constr.addRule("SL5", shapeAnd(tc("p :: SL4"), not(se(tc("q :: SL2")))));

		assertNull(SchemaRulesStaticAnalysis.computeStratification(constr.getSchema()));
	}
	
	private static final String PREFIX = "http://a.ex#";
	
	public static ShapeExprLabel newShapeLabel (String label){
		return new ShapeExprLabel(SimpleValueFactory.getInstance().createIRI(PREFIX + label));
	}
	
	
	
}


