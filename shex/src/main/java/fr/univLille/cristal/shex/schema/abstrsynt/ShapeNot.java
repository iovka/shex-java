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

import java.util.LinkedHashMap;
import java.util.Map;

import fr.univLille.cristal.shex.schema.analysis.ShapeExpressionVisitor;

/**
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public class ShapeNot extends ShapeExpr {

	private ShapeExpr subExpression;
	
	public ShapeNot(ShapeExpr subExpression) {
		this.subExpression = subExpression;
	}
	
	public ShapeExpr getSubExpression(){
		return subExpression;
	}

	@Override
	public <ResultType> void accept(ShapeExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitShapeNot(this, arguments);
	}
	

	@Override
	public String toString() {
		return String.format("(NOT %s)", subExpression.toString());
	}
	
	@Override
	public Object toJsonLD() {
		Map<String,Object> jsonObject = new LinkedHashMap<String,Object>();
		jsonObject.put("type", "ShapeNot");
		if (! this.id.isGenerated()) {
			jsonObject.put("id", this.id.toString());
		}
		jsonObject.put("SHapeExpr", subExpression.toJsonLD());
		return jsonObject;
	}
	
}
