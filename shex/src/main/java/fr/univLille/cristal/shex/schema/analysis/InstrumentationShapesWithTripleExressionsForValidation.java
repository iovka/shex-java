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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.EachOf;
import fr.univLille.cristal.shex.schema.abstrsynt.NonRefTripleExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAnd;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNot;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;
import fr.univLille.cristal.shex.schema.parsing.JsonldParser;

/** With a {@link Shape} associates the {@link TripleExpr} to be used for validation.
 * The new {@link TripleExpr} is obtained by:
 * - {@link RepeatedTripleExpression}s are unfolded whenever their cardinality is not +, * or [1;1]
 * - additional {@link TripleConstraint}s are added for the extra properties, this adds additional rules
 * 
 * @author Iovka Boneva
 * 12 oct. 2017
 */
public class InstrumentationShapesWithTripleExressionsForValidation 
					extends Instrumentation<Shape, TripleExpr>{

	private static InstrumentationShapesWithTripleExressionsForValidation theInstance = new InstrumentationShapesWithTripleExressionsForValidation();
	
	public static InstrumentationShapesWithTripleExressionsForValidation getInstance () {
		return theInstance;
	}
	
	private InstrumentationShapesWithTripleExressionsForValidation() {
		super("TRIPLE_EXPR_FOR_VALIDATION"); 
	}

	@Override
	public TripleExpr compute(Shape shape, Object... context) {
		
		Map<ShapeExprLabel, ShapeExpr> rules = (Map<ShapeExprLabel, ShapeExpr>) context[0];
		
		TripleExpr texpr;
		
		ComputeUnfoldedArbitraryRepetitionsVisitor visitor = new ComputeUnfoldedArbitraryRepetitionsVisitor();
		shape.getTripleExpression().accept(visitor);
		texpr = visitor.getResult();

		Set<TripleConstraint> extraTripleConstraints = extraTripleConstraints(shape.getTripleExpression(), shape.getExtraProperties(), shape.isClosed(), rules);
		
		if (extraTripleConstraints.isEmpty())
			return texpr;
		else {
			List<NonRefTripleExpr> subExpressions = new ArrayList<>();
			subExpressions.addAll(extraTripleConstraints);
			subExpressions.add((NonRefTripleExpr) texpr);
			return new EachOf(subExpressions);
		} 
	}

	private Set<TripleConstraint> extraTripleConstraints (NonRefTripleExpr expr, Set<TCProperty> extraProps, boolean isClosed, Map<ShapeExprLabel, ShapeExpr> rules) {
		
		List<TripleConstraint> tripleConstraints;
		CollectTripleConstraintsVisitor visitor = new CollectTripleConstraintsVisitor();
		expr.accept(visitor);
		tripleConstraints = visitor.getResult();
		
		Set<TripleConstraint> additionalTripleConstraints = new HashSet<>();
				
		Iterator<TCProperty> extraPropsIterator = extraProps.iterator();
		while (extraPropsIterator.hasNext()) {
			TCProperty extraProp = extraPropsIterator.next();
			List<ShapeExpr> refsWithThisPropNegated = 
					tripleConstraints.stream()
					.filter(tc -> tc.getProperty().equals(extraProp))
					.map(tc -> new ShapeNot(tc.getShapeExpr()))
					.collect(Collectors.toList());

			if (refsWithThisPropNegated.isEmpty() && !isClosed) {
				extraPropsIterator.remove();
			}
			else if (refsWithThisPropNegated.isEmpty() && isClosed) {
				additionalTripleConstraints.add(TripleConstraint.newSingleton(extraProp, new ShapeExprRef(JsonldParser.SL_ALL))); 
			}
			else {
				ShapeExpr shapeExpression;
				if (refsWithThisPropNegated.size() == 1)
					shapeExpression = refsWithThisPropNegated.get(0);
				else
					shapeExpression = new ShapeAnd(refsWithThisPropNegated);
				
				ShapeExprLabel label = createFreshExtraLabel(extraProp, rules);
				ShapeExprRef ref = new ShapeExprRef(label);
				rules.put(label, shapeExpression);
				additionalTripleConstraints.add(TripleConstraint.newSingleton(extraProp, ref));
			}
		}
		return additionalTripleConstraints;
	}
	
	private ShapeExprLabel createFreshExtraLabel(TCProperty extraProp, Map<ShapeExprLabel, ShapeExpr> rules) {
		String refLabelBase = "EXTRA#" + extraProp.getIri().toString();
		ShapeExprLabel freshLabel = new ShapeExprLabel(SimpleValueFactory.getInstance().createBNode(refLabelBase));
		if (! rules.containsKey(freshLabel))
			return freshLabel;
		
		Random rand = new Random();
		do {
			int i = rand.nextInt(100);
			freshLabel = new ShapeExprLabel(SimpleValueFactory.getInstance().createBNode(refLabelBase + "" + i));
			
		} while (rules.containsKey(freshLabel));
		return freshLabel;
	}
	
}
