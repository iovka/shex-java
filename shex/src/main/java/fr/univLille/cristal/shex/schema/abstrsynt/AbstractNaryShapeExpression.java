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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public abstract class AbstractNaryShapeExpression extends AbstractShapeExpression implements ShapeExpression{
	
	private List<ShapeExpression> subExpressions;
		
	public AbstractNaryShapeExpression (List<ShapeExpression> subExpressions) {
		this.subExpressions = new ArrayList<>(subExpressions);
	}
	
	public List<ShapeExpression> getSubExpressions (){
		return Collections.unmodifiableList(this.subExpressions);
	}
	
	/*
	private ShapeOrExpression expressionDNF;
	// TOCHECK
	public void setDNF(ShapeOrExpression valueExpression){
		expressionDNF = valueExpression;
	}
	
	// TOCHECK
	public ShapeOrExpression getDNF(){
		return expressionDNF;
	}
	*/
	
}
