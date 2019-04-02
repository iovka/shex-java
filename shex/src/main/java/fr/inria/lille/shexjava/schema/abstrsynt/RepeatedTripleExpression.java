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
package fr.inria.lille.shexjava.schema.abstrsynt;

import java.util.Map;

import fr.inria.lille.shexjava.schema.analysis.TripleExpressionVisitor;
import fr.inria.lille.shexjava.util.Interval;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class RepeatedTripleExpression extends TripleExpr {
	
	private TripleExpr subExpr;
	private Interval card;
	
	public RepeatedTripleExpression (TripleExpr subExpr, Interval card) {
		this.subExpr = subExpr;
		this.card = card;
	}
	
	public TripleExpr getSubExpression() {
		return this.subExpr;
	}

	public Interval getCardinality () {
		return this.card;
	}
		
	@Override
	public String toPrettyString(Map<String,String> prefixes) {
		String format;
		if (subExpr instanceof AbstractNaryTripleExpr) 
			format = "(%s)%s";
		else
			format = "%s%s";
		return String.format(format, subExpr.toPrettyString(prefixes), card.toString());
	}

	@Override
	public <ResultType> void accept(TripleExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitRepeated (this, arguments);
	}	
}
