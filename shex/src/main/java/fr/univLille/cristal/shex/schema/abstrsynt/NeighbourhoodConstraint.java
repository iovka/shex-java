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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.analysis.ShapeExpressionVisitor;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class NeighbourhoodConstraint extends AbstractShapeExpression implements AtomicShapeExpression  {
	
	private TripleExpression tripleExpression;
	private Set<TCProperty> extraProps;
	private TripleConstraint openForwardTripleConstraint;	

	/** Creates a {@link NeighbourhoodConstraint} that is forward closed, inverse open, and has no extra properties.
	 * 
	 * @param tripleExpression
	 */
	protected NeighbourhoodConstraint (TripleExpression tripleExpression) {
		this(tripleExpression, tripleExpression, Collections.emptySet(), null, null);
	}
	
	public NeighbourhoodConstraint(TripleExpression tripleExpression, TripleExpression originalTripleExpression,
			Set<TCProperty> extraProps, TripleConstraint openForwardTripleConstraint, TripleConstraint openInverseTripleConstraint) {
		this.tripleExpression = tripleExpression;
		this.extraProps = Collections.unmodifiableSet(new HashSet<>(extraProps));
		this.openForwardTripleConstraint = openForwardTripleConstraint;
	}
	
	public TripleExpression getTripleExpression () {
		return tripleExpression;
	}
	public boolean isClosed () {
		return openForwardTripleConstraint == null;
	}
	
	@Override
	public <ResultType> void accept(ShapeExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitNeighbourhoodConstraint(this, arguments);
	}

	@Override
	public String toString() {
		String closed = isClosed() ? "CLOSED" : "";
		String extra = extraProps.isEmpty() ? "" : "EXTRA" + extraProps.toString();
		return String.format("(%s %s %s)", closed, extra, tripleExpression);
	}

	
}
