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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import fr.univLille.cristal.shex.schema.TripleExprLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.EachOf;
import fr.univLille.cristal.shex.schema.abstrsynt.EmptyTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.OneOf;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExprRef;
import fr.univLille.cristal.shex.schema.analysis.TripleExpressionVisitor;

/** Recursively collects all {@link AbstractASTElement}s of given type from a {@link TripleExpr}. 
 * 
 * @author Iovka Boneva
 * 12 oct. 2017
 * @param <TripleConstraint>
 */
public class DynamicCollectorOfTripleConstraint extends TripleExpressionVisitor<List<TripleConstraint>>{
	private Map<TripleExprLabel,List<TripleConstraint>> dynamiqueRes;

	public DynamicCollectorOfTripleConstraint() {
		this.dynamiqueRes = new HashMap<TripleExprLabel,List<TripleConstraint>>();
	}
	
	public List<TripleConstraint> getResult(TripleExpr triple) {
		if (!dynamiqueRes.containsKey(triple.getId())) {
			triple.accept(this);
		}
		return dynamiqueRes.get(triple.getId());
	} 
	
	@Override
	public List<TripleConstraint> getResult() {
		return null;
	}

	@Override
	public void visitTripleConstraint(TripleConstraint tc, Object... arguments) {
		List<TripleConstraint> tmp = new ArrayList<TripleConstraint>();
		tmp.add((TripleConstraint) tc);
		this.dynamiqueRes.put(tc.getId(),tmp);
		
	}

	@Override
	public void visitEmpty(EmptyTripleExpression expr, Object[] arguments) {
		List<TripleConstraint> tmp = new ArrayList<TripleConstraint>();
		this.dynamiqueRes.put(expr.getId(),tmp);
	}

	@Override
	public void visitEachOf(EachOf expr, Object... arguments) {
		super.visitEachOf(expr, arguments);
		List<TripleConstraint> result = new ArrayList<TripleConstraint>();
		for (TripleExpr subExpr:expr.getSubExpressions()) {
			result.addAll(dynamiqueRes.get(subExpr.getId()));
		}
		this.dynamiqueRes.put(expr.getId(), result);
	}
	
	@Override
	public void visitOneOf(OneOf expr, Object... arguments) {
		super.visitOneOf(expr, arguments);
		List<TripleConstraint> result = new ArrayList<TripleConstraint>();
		for (TripleExpr subExpr:expr.getSubExpressions()) {
			result.addAll(dynamiqueRes.get(subExpr.getId()));
		}
		this.dynamiqueRes.put(expr.getId(), result);
	}
	
	
	@Override
	public void visitRepeated(RepeatedTripleExpression expr, Object[] arguments) {
		super.visitRepeated(expr, arguments);
		List<TripleConstraint> result = new ArrayList<TripleConstraint>(dynamiqueRes.get(expr.getSubExpression().getId()));
		this.dynamiqueRes.put(expr.getId(),result);
	}
	
	@Override
	public void visitTripleExprReference(TripleExprRef expr, Object... arguments) {
		expr.getTripleExp().accept(this, arguments);
		List<TripleConstraint> tmp = new ArrayList<TripleConstraint>(dynamiqueRes.get(expr.getTripleExp().getId()));
		this.dynamiqueRes.put(expr.getId(),tmp);
		
	}
	
}
