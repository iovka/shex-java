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
import java.util.function.Function;
import java.util.function.Predicate;

import fr.univLille.cristal.shex.schema.abstrsynt.ASTElement;
import fr.univLille.cristal.shex.schema.abstrsynt.AbstractASTElement;
import fr.univLille.cristal.shex.schema.abstrsynt.EachOf;
import fr.univLille.cristal.shex.schema.abstrsynt.EmptyTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.OneOf;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExprRef;

/** Recursively collects all {@link AbstractASTElement}s of given type from a {@link TripleExpr}. 
 * 
 * @author Iovka Boneva
 * 12 oct. 2017
 * @param <C>
 */
public class CollectASTElementsT<C extends ASTElement> extends TripleExpressionVisitor<Set<C>>{

	private Set<C> set;
	private Predicate<ASTElement> filter;
	private boolean traverseShapeExpressions;
	
	public CollectASTElementsT(Predicate<ASTElement> filter, Set<C> collectionSet, boolean traverseShapeExpressions) {
		this.set = collectionSet;
		this.filter = filter;
		this.traverseShapeExpressions = traverseShapeExpressions;
	}
	
	@Override
	public Set<C> getResult() {
		return set;
	}

	@Override
	public void visitTripleConstraint(TripleConstraint tc, Object... arguments) {
		if (filter.test(tc))
			set.add((C)tc);
		if (traverseShapeExpressions) {
			CollectASTElementsS<C> c = new CollectASTElementsS<>(filter, set, traverseShapeExpressions);
			tc.getShapeExpr().accept(c, arguments);
		}
	}

	@Override
	public void visitEmpty(EmptyTripleExpression expr, Object[] arguments) {
		if (filter.test(expr))
			set.add((C)expr);
	}

	@Override
	public void visitEachOf(EachOf expr, Object... arguments) {
		if (filter.test(expr))
			set.add((C)expr);
		super.visitEachOf(expr, arguments);
	}
	
	@Override
	public void visitOneOf(OneOf expr, Object... arguments) {
		if (filter.test(expr))
			set.add((C)expr);
		super.visitOneOf(expr, arguments);
	}
	
	
	@Override
	public void visitRepeated(RepeatedTripleExpression expr, Object[] arguments) {
		if (filter.test(expr))
			set.add((C)expr);
		super.visitRepeated(expr, arguments);
	}
	
	@Override
	public void visitTripleExprReference(TripleExprRef expr, Object... arguments) {
		if (filter.test(expr))
			set.add((C)expr);
	}
	
}
