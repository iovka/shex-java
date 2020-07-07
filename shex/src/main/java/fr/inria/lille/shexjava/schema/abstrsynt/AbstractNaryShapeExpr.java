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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public abstract class AbstractNaryShapeExpr extends ShapeExpr {
	
	private List<ShapeExpr> subExpressions;
		
	public AbstractNaryShapeExpr (List<ShapeExpr> subExpressions) {
		this(null, subExpressions);
	}
	
	public AbstractNaryShapeExpr (Label id, List<ShapeExpr> subExpressions) {
		super();
		this.subExpressions = new ArrayList<>(subExpressions);
	}
	
	public List<ShapeExpr> getSubExpressions (){
		return Collections.unmodifiableList(this.subExpressions);
	}
	
}
