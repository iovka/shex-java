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

import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;

/** Adds shape labels and corresponding rules for all anonymous shape expressions (i.e. shape expressions nested into triple expressions).
 * Adds a {@link ShapeExprRef} attribute to such triple constraints.
 * 
 * @author Iovka Boneva
 * 12 oct. 2017
 */
public class InstrumentationShapeRefsForAnonymousShapeExpressions 
				extends Instrumentation<TripleConstraint, ShapeExprRef> {

	private static InstrumentationShapeRefsForAnonymousShapeExpressions theInstance = 
			new InstrumentationShapeRefsForAnonymousShapeExpressions();
	
	public static InstrumentationShapeRefsForAnonymousShapeExpressions getInstance() {
		return theInstance;
	}
	
	private InstrumentationShapeRefsForAnonymousShapeExpressions() {
		super("SHAPE_REF_TO_ANONYMOUS_SHAPE_EXPR");
	}

	public static final ValueFactory RDF_FACTORY = SimpleValueFactory.getInstance();
	
	
	/** Expects {@link ShexSchema} as context.
	 * The new shape expressions are added to the schema rules.
	 */
	@Override
	public ShapeExprRef compute(TripleConstraint from, Object... context) {
		if (from.getShapeExpr() instanceof ShapeExprRef)
			return (ShapeExprRef) (from.getShapeExpr());
		else {
			Map<ShapeExprLabel, ShapeExpr> additionalRules = (Map<ShapeExprLabel, ShapeExpr>) context[0];
			ShapeExprLabel label = from.getShapeExpr().getId();
			if (label == null) {
				label = new ShapeExprLabel(RDF_FACTORY.createBNode());
				from.getShapeExpr().setId(label);
			}
			additionalRules.put(label, from.getShapeExpr());
			return new ShapeExprRef(label);
		}
	}

}
