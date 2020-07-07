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

import java.util.Collections;
import java.util.Map;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public abstract class ShapeExpr extends Expression {
	
	private Boolean isAbstract = null;
	
	public boolean isAbstract () {
		return this.isAbstract == true;
	}
	public void setAbstract (boolean isAbstract) {
		if (this.isAbstract != null)
			throw new IllegalStateException("Abstract can be set only once.");
		this.isAbstract = isAbstract;
	}

	public abstract <ResultType> void accept (ShapeExpressionVisitor<ResultType> visitor, Object ... arguments);
}
