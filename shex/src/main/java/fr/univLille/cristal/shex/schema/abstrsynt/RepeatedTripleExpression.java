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


package fr.univLille.cristal.shex.schema.abstrsynt;

import fr.univLille.cristal.shex.schema.analysis.TripleExpressionVisitor;
import fr.univLille.cristal.shex.util.Interval;

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
	public String toString() {
		String format;
		if (subExpr instanceof AbstractNaryTripleExpr) 
			format = "(%s)%s";
		else
			format = "%s%s";
		return String.format(format, subExpr.toString(), card.toString());
	}

	@Override
	public <ResultType> void accept(TripleExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitRepeated (this, arguments);
	}

	private TripleExpr unfoldedVersion;
	
	public TripleExpr getUnfoldedVersion() {
		return this.unfoldedVersion;
	}
	
	public void setUnfoldedVersion (TripleExpr expr) {
		if (this.unfoldedVersion != null)
			throw new IllegalStateException("Unfolded version can be set at most once.");
		this.unfoldedVersion = expr;
	}
	
	@Override
	public Object toJsonLD() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
