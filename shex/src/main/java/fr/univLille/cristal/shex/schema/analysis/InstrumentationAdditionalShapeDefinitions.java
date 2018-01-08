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

import java.util.HashMap;
import java.util.Map;

import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;

/** Associates with a ShexSchema a map of additional named shape expressions.
 * Also applies {@link InstrumentationShapeRefsForAnonymousShapeExpressions} to all {@link TripleConstraint}s of the schema.
 * After the application of this instrumentation, every {@link TripleConstraint} of the schema contains a {@link ShapeExprRef} either to already existing {@link ShapeExpr}, or to a {@link ShapeExpr} added by the additional rules.
 * 
 * @author Iovka Boneva
 * 12 oct. 2017
 */
public class InstrumentationAdditionalShapeDefinitions 
				extends Instrumentation<ShexSchema, Map<ShapeExprLabel, ShapeExpr>>{ 
	
	private static final InstrumentationAdditionalShapeDefinitions instance = 
			new InstrumentationAdditionalShapeDefinitions();
	
	public static InstrumentationAdditionalShapeDefinitions getInstance() {
		return instance;
	}
	
	
	private InstrumentationAdditionalShapeDefinitions() {
		super("ALL_SHAPE_DEFINITIONS");
	}

	@Override
	public Map<ShapeExprLabel, ShapeExpr> compute(ShexSchema from, Object... arguments) {
		Map<ShapeExprLabel, ShapeExpr> additionalRules = new HashMap<>();
		
		Map<ShapeExprLabel, ShapeExpr> newRules = from;
		do {
			newRules = newRules(newRules);
			additionalRules.putAll(newRules);
		} while (! newRules.isEmpty());
		
		return additionalRules;
	}
	
	private Map<ShapeExprLabel, ShapeExpr> newRules (Map<ShapeExprLabel, ShapeExpr> fromRules) {
		Map<ShapeExprLabel, ShapeExpr> allRules = new HashMap<>();
		for (ShapeExpr shexpr : fromRules.values()) {
			for (Shape shape : SchemaRulesStaticAnalysis.collectShapes(shexpr)) {
				for (TripleConstraint tc: SchemaRulesStaticAnalysis.collectTripleConstraints(shape.getTripleExpression())) {					
					InstrumentationShapeRefsForAnonymousShapeExpressions.getInstance().apply(tc, allRules);
				}
			}
		}
	//	allRules.putAll(fromRules);
		return allRules;
	}
}
