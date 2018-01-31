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

import java.util.List;

import fr.univLille.cristal.shex.schema.abstrsynt.EachOf;
import fr.univLille.cristal.shex.schema.abstrsynt.EmptyTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.OneOf;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExprRef;
import fr.univLille.cristal.shex.schema.analysis.TripleExpressionVisitor;
import fr.univLille.cristal.shex.util.Interval;

/** Allows to compute the interval for a triple expression and a bag.
 * 
 * The Interval Algorithm is defined only for shape expressions that satisfy the following constraints:
 * - Arbitrary cardinalities are allowed only on triple constraints. An "arbitrary" cardinality is one different from {@link Interval.OPT}, {@link Interval.PLUS}, {@link Interval.STAR}.
 * - A {@link Interval.PLUS} cardinality is allowed only if the sub expression does not contain the empty bag.
 * If the expression does not satisfy one of these, a {@link IllegalArgumentException} is thrown.
 * 
 * @author Iovka Boneva
 *
 */
public class IntervalComputation extends TripleExpressionVisitor<Interval>{
	
	//private Object listTripleConstraintsAttributeKey = InstrumentationListsOfTripleConstraintsOnTripleExpressions.getInstance().getKey();

	
	private Interval result;
	
	@Override
	public Interval getResult() {
		return result;
	}

	@Override
	public void visitTripleConstraint(TripleConstraint tc, Object... arguments) {
		Bag bag = (Bag) (arguments[0]);
		
		int nbOcc = bag.getMult(tc);
		this.result = new Interval(nbOcc, nbOcc);
	}

	@Override
	public void visitEmpty(EmptyTripleExpression emptyTripleExpression, Object[] arguments) {
		this.result = Interval.STAR;
	}
	
	@Override
	public void visitOneOf(OneOf expr, Object... arguments) {
		Interval res = Interval.ZERO; // the neutral element for addition
		
		for (TripleExpr subExpr : expr.getSubExpressions()) {
			subExpr.accept(this, arguments);
			res = add(res, this.result);
		}
		this.result = res;
	}
	
	@Override
	public void visitEachOf(EachOf expr, Object... arguments) {
		Interval res = Interval.STAR; // the neutral element for intersection

		for (TripleExpr subExpr : expr.getSubExpressions()) {
			subExpr.accept(this, arguments);
			res = inter(res, this.result);
		}
		this.result = res;
	}

	@Override
	public void visitRepeated(RepeatedTripleExpression expression, Object[] arguments) {
		Bag bag = (Bag) arguments[0];
		
		Interval card = expression.getCardinality();
		TripleExpr subExpr = expression.getSubExpression();

		if (card.equals(Interval.STAR)) {
			
			if (isEmptySubbag(bag, expression)) {
				this.result = Interval.STAR;
			} else {
				subExpr.accept(this, arguments);
				if (! this.result.equals(Interval.EMPTY)) {
					this.result = Interval.PLUS;
				} else {
					this.result = Interval.EMPTY;
				}
			}
		}

		else if (card.equals(Interval.PLUS)) {			
			if (isEmptySubbag(bag, expression)) {
				this.result = Interval.ZERO;
			} else {
				subExpr.accept(this, arguments);
				if (! this.result.equals(Interval.EMPTY)) {
					this.result = new Interval(1, this.result.max);
				} else {
					this.result = Interval.EMPTY;
				}
			}
		}
		
		else if (card.equals(Interval.OPT)) {
			subExpr.accept(this, arguments);
			this.result = add(this.result, Interval.STAR);
		}

		else if (subExpr instanceof TripleConstraint) {
			TripleConstraint tc = (TripleConstraint)  subExpr;
			int nbOcc = bag.getMult(tc);
			this.result = div(nbOcc, card);
		}

		else if (card.equals(Interval.EMPTY)) {
			throw new UnsupportedOperationException("not yet implemented");
			// FIXME
		}
		
		else {
			//expression.getUnfoldedVersion().accept(this, arguments);
			throw new IllegalArgumentException("Arbitrary repetition " + card + "allowed on triple constraints only.");
		}

	}

	@Override
	public void visitTripleExprReference(TripleExprRef expr, Object... arguments) {
		expr.getTripleExp().accept(this, arguments);		
	}
	
	// TODO: Fix the next function
	private boolean isEmptySubbag(Bag bag, TripleExpr expression){
//
//		@SuppressWarnings("unchecked")
//		List<TripleConstraint> list = (List<TripleConstraint>) expression.getDynamicAttributes().get(listTripleConstraintsAttributeKey);
//		for(TripleConstraint tripleConstraint : list){
//			if(!(bag.getMult(tripleConstraint) == 0))
//				return false;
//		}
//		
		return true;
	}
		


	/** This function relies on the fact that the empty interval is represented by [2;1],
	 * thus card.max() cannot be equal to 0 except if the interval is [0;0]
	 * 
	 * @param nbOcc
	 * @param card
	 * @return
	 */
	private Interval div(int nbOcc, Interval card) {

		if (card.equals(Interval.ZERO)) {
			if (nbOcc == 0) return Interval.STAR;
			else return Interval.EMPTY;
		}

		int min, max;

		// min = nbOcc / card.max();   uppper bound
		// with upper bound of (0 / UNBOUND) = 0
		// and  upper bound of (n / UNBOUND) = 1 for n != 0
		if (card.max == Interval.UNBOUND) {
			if (nbOcc == 0) 
				min = 0;
			else 
				min = 1;

		} else {
			if (nbOcc % card.max == 0)
				min = nbOcc / card.max;
			else 
				min = (nbOcc / card.max) + 1;
		}	

		// max = nbOcc / card.min();  lower bound
		// with lower bound of (0 / 0) = 
		// and  lower bound of (n / 0) = UNBOUND for n != 0
		if (card.min == 0) 
			max = Interval.UNBOUND;
		else 
			max = nbOcc / card.min; 

		return new Interval(min,max);

	}

	private Interval add (Interval i1, Interval i2) {
		int imin, imax;

		imin = i1.min + i2.min;
		if (i1.max == Interval.UNBOUND || i2.max == Interval.UNBOUND) {
			imax = Interval.UNBOUND;
		} else {
			imax = i1.max + i2.max;
		}
		return new Interval(imin, imax);
	}

	private Interval inter (Interval i1, Interval i2) {
		int imin, imax;

		imin = Math.max(i1.min, i2.min);
		imax = Math.min(i1.max, i2.max);

		return new Interval(imin, imax);
	}





}
