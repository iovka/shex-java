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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor;
import fr.univLille.cristal.shex.schema.analysis.SchemaRulesStaticAnalysis;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TestAcyclicShapeRefs {

	@Test
	public void testNoCycles() {
		ShapeExprRef ref1 = new ShapeExprRef(newShapeLabel("SL1"));
		ShapeExprRef ref2 = new ShapeExprRef(newShapeLabel("SL2"));
		ShapeExprRef ref3 = new ShapeExprRef(newShapeLabel("SL3"));
		
		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		
		constr.addRule("SL0", ref1);
		constr.addRule("SL1", shapeAnd(ref2, ref3));
		
		@SuppressWarnings("rawtypes")
		List cycles = SchemaRulesStaticAnalysis.computeCyclicShapeRefDependencies(constr.getSchema());
		assertTrue(cycles.isEmpty());
	}
	
	@Test
	public void testCycles() {
		ShapeExprRef ref1 = new ShapeExprRef(newShapeLabel("SL1"));
		ShapeExprRef ref2 = new ShapeExprRef(newShapeLabel("SL2"));
		ShapeExprRef ref3 = new ShapeExprRef(newShapeLabel("SL3"));
		
		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		
		constr.addRule("SL0", ref1);
		constr.addRule("SL1", shapeAnd(ref2, ref3));
		constr.addRule("SL2", shapeOr(ref1, ref3));
		constr.addRule("SL3", ref1);
		
		List<List<ShapeExprLabel>> cycles = SchemaRulesStaticAnalysis.computeCyclicShapeRefDependencies(constr.getSchema());
		assertEquals(3, cycles.size());
		
		Set<Set<ShapeExprLabel>> cycleSets = new HashSet<>();
		for (List<ShapeExprLabel> c : cycles)
			cycleSets.add(new HashSet<>(c));
		
		Set<Set<ShapeExprLabel>> expectedCycleSets = new HashSet<>();
		Set<ShapeExprLabel> s1 = new HashSet<>();
		s1.add(newShapeLabel("SL1"));
		s1.add(newShapeLabel("SL2"));
		expectedCycleSets.add(s1);
		
		Set<ShapeExprLabel> s2 = new HashSet<>();
		s2.add(newShapeLabel("SL1"));
		s2.add(newShapeLabel("SL3"));
		expectedCycleSets.add(s2);
		
		Set<ShapeExprLabel> s3 = new HashSet<>();
		s3.add(newShapeLabel("SL1"));
		s3.add(newShapeLabel("SL2"));
		s3.add(newShapeLabel("SL3"));
		expectedCycleSets.add(s3);
		
		assertEquals(expectedCycleSets, cycleSets);
	}
	
}
