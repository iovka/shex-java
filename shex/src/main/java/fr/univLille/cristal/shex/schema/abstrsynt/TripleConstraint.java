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

import java.util.Set;

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.analysis.TripleExpressionVisitor;
import fr.univLille.cristal.shex.schema.concrsynt.ForwardComplementPropertySet;
import fr.univLille.cristal.shex.schema.concrsynt.InverseComplementPropertySet;
import fr.univLille.cristal.shex.schema.concrsynt.SingletonPropertySet;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TripleConstraint extends AbstractTripleExpression {
	
	private PropertySet propertySet;	
	private ShapeRef shapeRef;
	private boolean isSingleton;
		
	
	public static TripleConstraint newSingleton (TCProperty property, ShapeRef shapeRef) {
		return new TripleConstraint(new SingletonPropertySet(property), shapeRef, true);
	}
	
	public static TripleConstraint newForwardOpen (Set<TCProperty> closedProperties, ShapeRef shapeRef) {
		return new TripleConstraint(new ForwardComplementPropertySet(closedProperties), shapeRef, false);
	}
	
	public static TripleConstraint newInverseOpen (Set<TCProperty> closedProperties, ShapeRef shapeRef) {
		return new TripleConstraint(new InverseComplementPropertySet(closedProperties), shapeRef, false);
	}
	
	
	private TripleConstraint (PropertySet propertySet, ShapeRef shapeRef, boolean isSingleton) {
		this.propertySet = propertySet;
		this.shapeRef = shapeRef;
		this.isSingleton = isSingleton;
	}	

	public PropertySet getPropertySet(){
		return propertySet;
	}

	public TCProperty getProperty () {
		if (! isSingleton)
			throw new IllegalStateException("Trying to retrieve the property of a non-singleton property set");
		return ((SingletonPropertySet) propertySet).getProperty();
	}
	
	public ShapeRef getShapeRef(){
		return shapeRef;
	}
		
	
	@Override
	public String toString() {
		return String.format("%s::%s",
				propertySet.toString(),
				shapeRef.toString());
	}

	@Override
	public <ResultType> void accept(TripleExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitTripleConstraint(this, arguments);
	}
	
	@Override
	public TripleConstraint clone() {
		return new TripleConstraint(this.propertySet, this.shapeRef, this.isSingleton);
	}
	
}
