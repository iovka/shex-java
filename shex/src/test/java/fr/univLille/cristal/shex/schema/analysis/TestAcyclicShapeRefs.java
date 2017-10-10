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

import fr.univLille.cristal.shex.schema.ShapeLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeRef;
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
		ShapeRef ref1 = new ShapeRef(new ShapeLabel("SL1"));
		ShapeRef ref2 = new ShapeRef(new ShapeLabel("SL2"));
		ShapeRef ref3 = new ShapeRef(new ShapeLabel("SL3"));
		
		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.clearRuleMaps();
		
		constr.addRule("SL0", ref1);
		constr.addRule("SL1", shapeAnd(ref2, ref3));
		
		@SuppressWarnings("rawtypes")
		List cycles = SchemaRulesStaticAnalysis.computeCyclicShapeRefDependencies(constr.getRulesMap());
		assertTrue(cycles.isEmpty());
	}
	
	@Test
	public void testCycles() {
		ShapeRef ref1 = new ShapeRef(new ShapeLabel("SL1"));
		ShapeRef ref2 = new ShapeRef(new ShapeLabel("SL2"));
		ShapeRef ref3 = new ShapeRef(new ShapeLabel("SL3"));
		
		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.clearRuleMaps();
		
		constr.addRule("SL0", ref1);
		constr.addRule("SL1", shapeAnd(ref2, ref3));
		constr.addRule("SL2", shapeOr(ref1, ref3));
		constr.addRule("SL3", ref1);
		
		List<List<ShapeLabel>> cycles = SchemaRulesStaticAnalysis.computeCyclicShapeRefDependencies(constr.getRulesMap());
		assertEquals(3, cycles.size());
		
		Set<Set<ShapeLabel>> cycleSets = new HashSet<>();
		for (List<ShapeLabel> c : cycles)
			cycleSets.add(new HashSet<>(c));
		
		Set<Set<ShapeLabel>> expectedCycleSets = new HashSet<>();
		Set<ShapeLabel> s1 = new HashSet<>();
		s1.add(new ShapeLabel("SL1"));
		s1.add(new ShapeLabel("SL2"));
		expectedCycleSets.add(s1);
		
		Set<ShapeLabel> s2 = new HashSet<>();
		s2.add(new ShapeLabel("SL1"));
		s2.add(new ShapeLabel("SL3"));
		expectedCycleSets.add(s2);
		
		Set<ShapeLabel> s3 = new HashSet<>();
		s3.add(new ShapeLabel("SL1"));
		s3.add(new ShapeLabel("SL2"));
		s3.add(new ShapeLabel("SL3"));
		expectedCycleSets.add(s3);
		
		assertEquals(expectedCycleSets, cycleSets);
	}
	
}
