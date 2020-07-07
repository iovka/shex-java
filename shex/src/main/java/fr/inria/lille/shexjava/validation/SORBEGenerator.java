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
package fr.inria.lille.shexjava.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.inria.lille.shexjava.schema.LabelGenerated;

import fr.inria.lille.shexjava.schema.abstrsynt.EachOf;
import fr.inria.lille.shexjava.schema.abstrsynt.EmptyTripleExpression;
import fr.inria.lille.shexjava.schema.abstrsynt.OneOf;
import fr.inria.lille.shexjava.schema.abstrsynt.RepeatedTripleExpression;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExpr;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExprRef;
import fr.inria.lille.shexjava.schema.analysis.TripleExpressionVisitor;
import fr.inria.lille.shexjava.util.Interval;

/** Allows to compute a SORBE version of a triple expression. 
 * The computation results are memorized and won't be recomputed by further calls.
 * The SORBE version does not contain any triple expression reference, cardinality other than *, ? or +, nor an empty triple expression with the + cardinality.
 *
 * @author Iovka Boneva
 * @author Jérémie Dusart
 */
public class SORBEGenerator {
	/** The already computed top level sorbe expressions. */
	private Map<TripleExpr,TripleExpr> originalToSorbeMap;

	/** The original triple constraint that corresponds to a computed triple constraint. */
	private Map<TripleConstraint, TripleConstraint> sorbeToOriginalTripleConstraintMap;


	public SORBEGenerator() {
		this.originalToSorbeMap =new HashMap<>();
		this.sorbeToOriginalTripleConstraintMap = new HashMap<>();
	}

	
	/** Construct an equivalent triple expression that satisfies the SORBE requirement. 
	 * @param texpr
	 * @return
	 */
	public TripleExpr getSORBETripleExpr(TripleExpr texpr) {
		return originalToSorbeMap.computeIfAbsent(texpr, te -> { te.accept(generatorTE); return generatorTE.getResult(); });
	}

	/** Returns the original triple constraint expression for which the argument is the sorbe version. */
	public TripleConstraint getOriginalTripleConstraint(TripleConstraint tc) {
		return sorbeToOriginalTripleConstraintMap.get(tc);
	}
	
	private GeneratorOfTripleExpr generatorTE = new GeneratorOfTripleExpr();
	
	private class GeneratorOfTripleExpr extends TripleExpressionVisitor<TripleExpr> {

		@Override
		public void visitTripleConstraint(TripleConstraint tc, Object... arguments) {
			setResult(tc.clone());
			setTripleLabel(getResult(),tc);
		}

		@Override
		public void visitTripleExprReference(TripleExprRef expr, Object... arguments) {
			expr.getTripleExp().accept(this);			
		}
		
		@Override
		public void visitEachOf (EachOf expr, Object ... arguments) {
			List<TripleExpr> newSubExprs = new ArrayList<TripleExpr>();
			for (TripleExpr subExpr : expr.getSubExpressions()) {
				subExpr.accept(this, arguments);
				newSubExprs.add(getResult());
			}
			setResult(new EachOf(newSubExprs));
			setTripleLabel(getResult(),expr);
		}
		
		@Override
		public void visitOneOf (OneOf expr, Object ... arguments) {
			List<TripleExpr> newSubExprs = new ArrayList<TripleExpr>();
			for (TripleExpr subExpr : expr.getSubExpressions()) {
				subExpr.accept(this, arguments);
				newSubExprs.add(getResult());
			}
			setResult(new OneOf(newSubExprs));
			setTripleLabel(getResult(),expr);
		}
		

		@Override
		public void visitEmpty(EmptyTripleExpression expr, Object[] arguments) {
			setResult(new EmptyTripleExpression());
			setTripleLabel(getResult(),expr);
		}
		
		@Override
		public void visitRepeated(RepeatedTripleExpression expr, Object[] arguments) {
			CheckIfContainsEmpty visitor = new CheckIfContainsEmpty();
			expr.accept(visitor);
			expr.getSubExpression().accept(this);
			if (expr.getCardinality().equals(Interval.PLUS) & visitor.getResult()) {
				setResult(new RepeatedTripleExpression(getResult(),Interval.STAR));
				setTripleLabel(getResult(),expr);
			} else if(expr.getCardinality().equals(Interval.PLUS)
					  || expr.getCardinality().equals(Interval.STAR)
					  || expr.getCardinality().equals(Interval.OPT)
					  || expr.getCardinality().equals(Interval.ZERO)){
				setResult(new RepeatedTripleExpression(getResult(),expr.getCardinality()));
				setTripleLabel(getResult(),expr);
			} else {
				Interval card = expr.getCardinality();
				int nbClones = 0;
				int	nbOptClones = 0;
				List<TripleExpr> clones = new ArrayList<>();

				if (card.max == Interval.UNBOUND) {
					nbClones = card.min -1;
					TripleExpr tmp = new RepeatedTripleExpression(getResult(), Interval.PLUS);
					setTripleLabel(tmp,expr);
					clones.add(tmp);
				}else {
					nbClones = card.min;
					nbOptClones = card.max - card.min;
				}

				for (int i=0; i<nbClones;i++) {
					expr.getSubExpression().accept(this);
					clones.add(getResult());
				}
				for (int i=0; i<nbOptClones;i++) {
					expr.getSubExpression().accept(this);
					TripleExpr tmp = new RepeatedTripleExpression(getResult(), Interval.OPT);
					setTripleLabel(tmp,expr);
					clones.add(tmp);
				}
				if (clones.size()==1)
					setResult(clones.get(0));
				else {
					setResult(new EachOf(clones));
					setTripleLabel(getResult(),expr);
				}
			}
		}
		
	}
	
	
	private void setTripleLabel(TripleExpr newTriple, TripleExpr oldTriple) {
		newTriple.setId(LabelGenerated.getNew());
		if (newTriple instanceof TripleConstraint)
			sorbeToOriginalTripleConstraintMap.put((TripleConstraint) oldTriple, (TripleConstraint) newTriple);
	}

	static class CheckIfContainsEmpty extends TripleExpressionVisitor<Boolean>{

		@Override
		public void visitTripleConstraint(TripleConstraint tc, Object... arguments) {
			setResult(false);
		}

		@Override
		public void visitEmpty(EmptyTripleExpression expr, Object[] arguments) {
			setResult(false);
		}

		@Override
		public void visitEachOf(EachOf expr, Object... arguments) {
			for (TripleExpr subExpr : expr.getSubExpressions()) {
				subExpr.accept(this, arguments);
				if (!getResult())
					return;
			}
		}

		@Override
		public void visitOneOf(OneOf expr, Object... arguments) {
			for (TripleExpr subExpr : expr.getSubExpressions()) {
				subExpr.accept(this, arguments);
				if (getResult())
					return;
			}
		}

		@Override
		public void visitRepeated(RepeatedTripleExpression expr, Object[] arguments) {
			if (expr.getCardinality().min == 0) {
				setResult(true);
			} else {
				expr.getSubExpression().accept(this, arguments);
			}
		}

		@Override
		public void visitTripleExprReference(TripleExprRef expr, Object... arguments) {
			expr.getTripleExp().accept(this, arguments);
		}
	}
}
