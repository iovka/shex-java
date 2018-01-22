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

import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.analysis.ShapeExpressionVisitor;
import fr.univLille.cristal.shex.schema.parsing.ToJsonLD;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public abstract class ShapeExpr implements ToJsonLD{
	
	protected ShapeExprLabel id = null;
	
	public void setId(ShapeExprLabel id) {
		if (this.id != null)
			throw new IllegalStateException("ID can be set only once");
		this.id = id;
	}
	
	public ShapeExprLabel getId () {
		return this.id;
	}
	
	public abstract <ResultType> void accept (ShapeExpressionVisitor<ResultType> visitor, Object ... arguments);
}
