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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.univLille.cristal.shex.schema.analysis.ShapeExpressionVisitor;
import fr.univLille.cristal.shex.util.CollectionToString;

/**
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public class ShapeOr extends AbstractNaryShapeExpr{


	public ShapeOr(List<ShapeExpr> subExpressions) {
		super(subExpressions);
	}
	
	@Override
	public String toString() {
		return CollectionToString.collectionToString(getSubExpressions(), " OR ", "(", ")");
	}

	@Override
	public <ResultType> void accept(ShapeExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitShapeOr(this, arguments);
	}
	
	@Override
	public Object toJsonLD() {
		Map<String,Object> jsonObject = new LinkedHashMap<String,Object>();
		jsonObject.put("type", "ShapeOr");
		if (! this.id.isGenerated()) {
			jsonObject.put("id", this.id.toString());
		}
		List<Object> subexpressions = new LinkedList<Object>();
		for (ShapeExpr sh:this.getSubExpressions()) {
			subexpressions.add(sh.toJsonLD());
		}
		jsonObject.put("ShapeExprs", subexpressions);
		return jsonObject;
	}
	
}