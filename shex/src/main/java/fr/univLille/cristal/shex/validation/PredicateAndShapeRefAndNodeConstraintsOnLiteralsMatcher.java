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


package fr.univLille.cristal.shex.validation;

import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Resource;

import fr.univLille.cristal.shex.graph.NeighborTriple;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class PredicateAndShapeRefAndNodeConstraintsOnLiteralsMatcher extends PredicateOnlyMatcher {

	private Typing typing;
	
	public PredicateAndShapeRefAndNodeConstraintsOnLiteralsMatcher(Typing typing) {
		this.typing = typing;
	}

	@Override
	public Boolean apply(NeighborTriple triple, TripleConstraint tc) {
		if (! super.apply(triple, tc))
			return false;
		if (triple.getOpposite() instanceof Literal) {
			Literal opLit = (Literal) triple.getOpposite();
			EvaluateShapeExpressionOnLiteralVisitor visitor = new EvaluateShapeExpressionOnLiteralVisitor(opLit);
			tc.getShapeRef().getShapeDefinition().expression.accept(visitor);
			return visitor.getResult();
		}
		else 
			return typing.contains((Resource) triple.getOpposite(), tc.getShapeRef().getLabel());
	}
	
}
