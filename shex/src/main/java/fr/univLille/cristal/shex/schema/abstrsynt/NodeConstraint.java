/*******************************************************************************
 * Copyright (C) 2018 Université de Lille - Inria
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package fr.univLille.cristal.shex.schema.abstrsynt;

import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.analysis.ShapeExpressionVisitor;
import fr.univLille.cristal.shex.schema.concrsynt.SetOfNodes;

/**
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public class NodeConstraint extends ShapeExpr {

	private SetOfNodes setOfNodes;
	
	public NodeConstraint (SetOfNodes setOfNodes) {
		this.setOfNodes = setOfNodes;
	}
	
	public boolean contains(Value node) {
		return setOfNodes.contains(node);
	}

	@Override
	public <ResultType> void accept(ShapeExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitNodeConstraint(this, arguments);
	}
	
	@Override
	public String toString() {
		return setOfNodes+"";
	}
}
