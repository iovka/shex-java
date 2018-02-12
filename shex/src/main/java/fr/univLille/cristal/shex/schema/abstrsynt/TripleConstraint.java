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

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.analysis.TripleExpressionVisitor;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TripleConstraint extends TripleExpr {
	private TCProperty property;	
	private ShapeExpr shapeExpr;
		
	
//	public static TripleConstraint newSingleton (TCProperty property, ShapeExpr shapeExpr) {
//		return new TripleConstraint(property, shapeExpr);
//	}
	
	public TripleConstraint (TCProperty property, ShapeExpr shapeExpr ) {
		this.property = property;
		this.shapeExpr = shapeExpr;
	}	

	public TCProperty getProperty(){
		return property;
	}
	
	public ShapeExpr getShapeExpr(){
		return shapeExpr;
	}


	@Override
	public <ResultType> void accept(TripleExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitTripleConstraint(this, arguments);
	}
	
	@Override
	public TripleConstraint clone() {
		return new TripleConstraint(this.property, this.shapeExpr);
	}
	

	@Override
	public String toString() {
		return String.format("%s::%s",
				property.toString(),
				shapeExpr.toString());
	}

}

