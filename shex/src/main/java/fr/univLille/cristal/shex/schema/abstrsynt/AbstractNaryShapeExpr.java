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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.univLille.cristal.shex.schema.ShapeExprLabel;

/**
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public abstract class AbstractNaryShapeExpr extends ShapeExpr {
	
	private List<ShapeExpr> subExpressions;
		
	public AbstractNaryShapeExpr (List<ShapeExpr> subExpressions) {
		this(null, subExpressions);
	}
	
	public AbstractNaryShapeExpr (ShapeExprLabel id, List<ShapeExpr> subExpressions) {
		super();
		this.subExpressions = new ArrayList<>(subExpressions);
	}
	
	public List<ShapeExpr> getSubExpressions (){
		return Collections.unmodifiableList(this.subExpressions);
	}
	
}
