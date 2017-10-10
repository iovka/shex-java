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

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Test;

import fr.univLille.cristal.shex.schema.ShapeLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeRef;
import fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor;
import fr.univLille.cristal.shex.schema.analysis.SchemaRulesStaticAnalysis;

import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.*;
import static org.junit.Assert.*;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TestComputeDependencyGraph {

	@Test
	public void testWithShapeRefs() {
		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.clearRuleMaps();
		
		// SL1 -> SL2 AND SL3
		// SL2 -> a::SL4
		// SL3 -> b::SL5
		// SL4 -> c:: .
		// SL5 -> d:: .
	
		constr.addRule("SL1", shapeAnd(new ShapeRef(new ShapeLabel("SL2")), new ShapeRef(new ShapeLabel("SL3"))));
		constr.addRule("SL2", se(tc("a:: SL4")));
		constr.addRule("SL3", se(tc("b:: SL5")));
		constr.addRule("SL4", se(tc("c:: .")));
		constr.addRule("SL5", se(tc("d:: .")));
			
		DefaultDirectedWeightedGraph<ShapeLabel, DefaultWeightedEdge> depGraph = SchemaRulesStaticAnalysis.computeDependencyGraph(constr.getRulesMap());
		assertTrue(depGraph.containsEdge(new ShapeLabel("SL1"), new ShapeLabel("SL2")));
		assertTrue(depGraph.containsEdge(new ShapeLabel("SL1"), new ShapeLabel("SL3")));
	}
	
	
	@Test
	public void testWithHiearchyOfShapeRefs() {
		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.clearRuleMaps();
		
		// SL1 -> SL2 AND SL3
		// SL2 -> SL4 OR SL5
		// SL3 -> SL6 AND SL7
		// SL4 -> a:: .
		// SL5 -> b:: .
		// SL6 -> c:: .
		// SL7 -> d:: .
	
		constr.addRule("SL1", shapeAnd(new ShapeRef(new ShapeLabel("SL2")), new ShapeRef(new ShapeLabel("SL3"))));
		constr.addRule("SL2", shapeOr(new ShapeRef(new ShapeLabel("SL4")), new ShapeRef(new ShapeLabel("SL5"))));
		constr.addRule("SL3", shapeAnd(new ShapeRef(new ShapeLabel("SL6")), new ShapeRef(new ShapeLabel("SL7"))));
		constr.addRule("SL4", se(tc("a:: .")));
		constr.addRule("SL5", se(tc("b:: .")));
		constr.addRule("SL6", se(tc("c:: .")));
		constr.addRule("SL7", se(tc("d:: .")));
		
		DefaultDirectedWeightedGraph<ShapeLabel, DefaultWeightedEdge> depGraph = SchemaRulesStaticAnalysis.computeDependencyGraph(constr.getRulesMap());
		assertTrue(depGraph.containsEdge(new ShapeLabel("SL1"), new ShapeLabel("SL2")));
		assertTrue(depGraph.containsEdge(new ShapeLabel("SL1"), new ShapeLabel("SL3")));
		assertFalse(depGraph.containsEdge(new ShapeLabel("SL1"), new ShapeLabel("SL4")));
		
		
	}
}
