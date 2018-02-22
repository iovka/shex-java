/*******************************************************************************
 * Copyright (C) 2018 Université de Lille - Inria
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package fr.univLille.cristal.shex.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univLille.cristal.shex.schema.Label;
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
	private Map<Label,List<TripleConstraint>> dynamiqueRes;

	public DynamicCollectorOfTripleConstraint() {
		this.dynamiqueRes = new HashMap<Label,List<TripleConstraint>>();
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
