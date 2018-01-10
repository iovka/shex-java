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

import fr.univLille.cristal.shex.schema.TripleExprLabel;
import fr.univLille.cristal.shex.schema.analysis.TripleExpressionVisitor;

/**
 * 
 * @author Iovka Boneva
 * 11 oct. 2017
 */
public class TripleExprRef extends TripleExpr {
	private TripleExprLabel label;
	private TripleExpr tripleExp;
	
	public TripleExprRef(TripleExprLabel ref) {
		this.label = ref;
	}
	
	public TripleExpr getTripleExp() {
		return tripleExp;
	}


	public void setTripleExp(TripleExpr tripleExp) {
		this.tripleExp = tripleExp;
	}


	public TripleExprLabel getLabel() {
		return label;
	}


	@Override
	public <ResultType> void accept(TripleExpressionVisitor<ResultType> visitor, Object... arguments) {
		// TODO Auto-generated method stub
	}
}
