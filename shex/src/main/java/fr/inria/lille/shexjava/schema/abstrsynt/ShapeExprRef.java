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
package fr.inria.lille.shexjava.schema.abstrsynt;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;

/**
 * 
 * @author Iovka Boneva
 * @author Jérémie Dusart
 */
public class ShapeExprRef extends ShapeExpr {
	
	private final Label label;
	private ShapeExpr def;
	
	public ShapeExprRef(Label label) {
		this.label = label;
	}

	public Label getLabel () {
		return this.label;
	}

	@Override
	public <ResultType> void accept(ShapeExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitShapeExprRef(this, arguments);
	}

	public void setShapeDefinition(ShapeExpr def) {
		if (this.def != null)
			throw new IllegalStateException("Shape definition can be set at most once");
		this.def = def;
	}
	
	public ShapeExpr getShapeDefinition () {
		return this.def;
	}
		
	@Override
	public String toString() {
		return "@"+label.toString();
	}
}
