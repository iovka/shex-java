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
	// FIXME: here it should become a ShapeEXpr and not a reference
	private ShapeExpr shapeExpr;
	private boolean isSingleton;
		
	
	public static TripleConstraint newSingleton (TCProperty property, ShapeExpr shapeExpr) {
		return new TripleConstraint(property, shapeExpr, true);
	}
	
	/*
	
	public static TripleConstraint newForwardOpen (Set<TCProperty> closedProperties, ShapeExprRef shapeRef) {
		return new TripleConstraint(new ForwardComplementPropertySet(closedProperties), shapeRef, false);
	}
	
	public static TripleConstraint newInverseOpen (Set<TCProperty> closedProperties, ShapeExprRef shapeRef) {
		return new TripleConstraint(new InverseComplementPropertySet(closedProperties), shapeRef, false);
	}
	
	*/
	private TripleConstraint (TCProperty property, ShapeExpr shapeExpr, boolean isSingleton) {
		this.property = property;
		this.shapeExpr = shapeExpr;
		this.isSingleton = isSingleton;
	}	

	public TCProperty getProperty(){
		return property;
	}

	/*
	public TCProperty getProperty () {
		if (! isSingleton)
			throw new IllegalStateException("Trying to retrieve the property of a non-singleton property set");
		return ((SingletonPropertySet) property).getProperty();
	}
	*/
	
	public ShapeExpr getShapeExpr(){
		return shapeExpr;
	}
	
	
	@Override
	public String toString() {
		return String.format("%s::%s",
				property.toString(),
				shapeExpr.toString());
	}

	@Override
	public <ResultType> void accept(TripleExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitTripleConstraint(this, arguments);
	}
	
	@Override
	public TripleConstraint clone() {
		return new TripleConstraint(this.property, (ShapeExprRef) this.shapeExpr, this.isSingleton);
	}
	
}
