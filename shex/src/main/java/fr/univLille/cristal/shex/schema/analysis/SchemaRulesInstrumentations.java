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

import java.util.List;
import java.util.Set;

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.abstrsynt.NeighbourhoodConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.SchemaRules;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpression;
import fr.univLille.cristal.shex.schema.ShapeLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpression;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class SchemaRulesInstrumentations {
	
	// FIXME : use a mechanism of applied instrumentations with identifiers fixed in advance, and documented
	
	public static void computeAndSetTripleConstraintsOnUnfoldedTripleExpressions (SchemaRules rules, Object attrKey, Object unfoldedTripleConstraintAttrKey) {
		for (ShapeLabel label : rules) {
			ShapeExpression expr = rules.getRule(label).expression;
			for (NeighbourhoodConstraint nc : SchemaRulesStaticAnalysis.collectNeighbourhoodConstraints(expr)) {
				TripleExpression unfoldedTripleExpression = (TripleExpression) nc.getAttributes().getAttribute(unfoldedTripleConstraintAttrKey);
				computeAndSetTripleConstraintsOn(unfoldedTripleExpression, attrKey);
			}
		}
	}
	
	public static void computeAndSetTripleConstraintsOn (TripleExpression expr, Object attrKey) {
		ComputeAndSetTripleConstraintsVisitor visitor = new ComputeAndSetTripleConstraintsVisitor(attrKey);
		expr.accept(visitor);
	}

	public static void setUsesInversePropertiesOn (NeighbourhoodConstraint nc, Object attrKey) {
		List<TripleConstraint> tripleConstraints = SchemaRulesStaticAnalysis.collectTripleConstraints(nc.getTripleExpression());
		for (TripleConstraint tc : tripleConstraints) {
			Set<TCProperty> mentionnedProperties = null;
			mentionnedProperties = tc.getPropertySet().getAsFiniteSet();
			if (mentionnedProperties == null)
				mentionnedProperties = tc.getPropertySet().getComplementAsFiniteSet();
			if (mentionnedProperties == null) {
				// neither finite nor co-finite, it possibly uses inverse properties
				nc.getAttributes().setAttribute(attrKey, true);
				return;
			}
			
			for (TCProperty prop: mentionnedProperties) {
				if (! prop.isForward()) {
					nc.getAttributes().setAttribute(attrKey, true);
					return;	
				}
			}
		}
		nc.getAttributes().setAttribute(attrKey, false);
	}
	
	public static void setUsesInversePropertiesOnNeighbourhoodConstraints (SchemaRules rules, Object attrKey) {
		for (ShapeLabel label : rules) {
			ShapeExpression expr = rules.getRule(label).expression;
			for (NeighbourhoodConstraint nc : SchemaRulesStaticAnalysis.collectNeighbourhoodConstraints(expr)) {
				setUsesInversePropertiesOn(nc, attrKey);
			}
		}
	}
	
	
	public static void setUnfoldedRepetitionsOn (NeighbourhoodConstraint nc, Object attrKey) {
		TripleExpression unfolded = SchemaRulesStaticAnalysis.computeUnfoldedRepetitions(nc.getTripleExpression());
		nc.getAttributes().setAttribute(attrKey, unfolded);
	}
	
	public static void setUnfoldedRepetitions (SchemaRules rules, Object attrKey) {
		for (ShapeLabel label : rules) {
			ShapeExpression expr = rules.getRule(label).expression;
			for (NeighbourhoodConstraint nc : SchemaRulesStaticAnalysis.collectNeighbourhoodConstraints(expr)) {
				setUnfoldedRepetitionsOn(nc, attrKey);
			}
		}
	}
	
	
}
