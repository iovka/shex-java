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

import java.util.Set;

import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;

/** With every shape associates a boolean value indicating whether the shape uses inverse properties in its triple expression.
 * 
 * @author Iovka Boneva
 * 12 oct. 2017
 */
public class InstrumentationUsesInversePropertiesOnShapes extends Instrumentation<Shape, Boolean> {
	
	private static InstrumentationUsesInversePropertiesOnShapes theInstance = new InstrumentationUsesInversePropertiesOnShapes();
	public static InstrumentationUsesInversePropertiesOnShapes getInstance () {
		return theInstance;
	}
	
	private InstrumentationUsesInversePropertiesOnShapes() {
		super("USES_INVERSE_PROPERTIES");
	}
	
	@Override
	public Boolean compute(Shape shape, Object... context) {
		Set<TripleConstraint> set = SchemaRulesStaticAnalysis.collectTripleConstraints(shape.getTripleExpression());
		return set.stream().map(tc -> tc.getProperty()).anyMatch(prop -> ! prop.isForward());
	}

}
