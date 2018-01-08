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

package fr.univLille.cristal.shex.schema.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.NodeConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExternal;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class CollectNeighbourhoodConstraintsVisitor extends ShapeExpressionVisitor<List<Shape>> {

	List<Shape> ncs = new ArrayList<>();
	
	@Override
	public List<Shape> getResult() {
		return Collections.unmodifiableList(ncs);
	}

	@Override
	public void visitShape(Shape expr, Object... arguments) {
		ncs.add(expr);
	}

	@Override
	public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {}

	@Override
	public void visitShapeExprRef(ShapeExprRef expr, Object[] arguments) {}

	@Override
	public void visitShapeExternal(ShapeExternal shapeExt, Object[] arguments) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}

}
